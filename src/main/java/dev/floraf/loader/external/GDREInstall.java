package dev.floraf.loader.external;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;

import dev.floraf.Mashup;

// TODO: Switch implementation of unzip to Apache Commons Compress to avoid
//       having two different archive/compression libraries installed at the
//       same time!

public class GDREInstall {
    String gdreDir = Mashup.getDataDir() + "/gdre";
    String gdreFile = this.getTargetFile();
    String version = "2.0.0";
    
    ProcessBuilder gdre = new ProcessBuilder();
    StringBuilder baseCommand;

    private static GDREInstall instance = null;

    public static GDREInstall get() {
        if (instance == null) instance = new GDREInstall();
        return instance;
    }

    private GDREInstall() {
        if (!this.exists()) {
            try {
                Files.createDirectories(Path.of(gdreDir));
                
                this.download();
                this.install();
            } catch (IOException e) {
                throw new RuntimeException (
                    "Unable to create Mashup directory; " +
                    "are your permissions correct?"
                );
            }
        }

        baseCommand = setupCommand();
        instance = this;
    }

    private String getTargetFile() {
        return switch (Mashup.getPlatform()) {
            case Mashup.Platform.WINDOWS -> "gdre_tools.exe";
            case Mashup.Platform.MACOS   ->
                "/Godot RE Tools.app/Contents/MacOS/Godot RE Tools";
            case Mashup.Platform.LINUX   -> "gdre_tools.x86_64";
        };
    }

    private boolean exists() {
        Path gdrePath;

        try {
            gdrePath = Path.of(gdreDir, gdreFile);
        } catch (InvalidPathException _) {
            return false;
        }

        return Files.exists(gdrePath);
    }

    private void download() {
        String os = Mashup.getPlatform().name.toLowerCase();

        List<String> gdreURL = Arrays.asList (
            "https://github.com/GDRETools/gdsdecomp/releases/download/v",
            version, "/GDRE_tools-v", version, "-", os, ".zip"
        );

        try {
            URL url = new URI(String.join("", gdreURL)).toURL();
            FileUtils.copyURLToFile(
                url, new File(gdreDir + "gdre.zip"), 10000, 5000);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(
                "Could not locate GDRE Tools; " +
                "please report this to the Mashup issue tracker!");
        } catch (IOException e) {
            throw new RuntimeException("Failed to download GDRE Tools!");
        }
    }

    private void install() {
        String file = gdreDir + "gdre.zip";
        try (var zip = new ZipFile(file)) {
            zip.extractAll(gdreDir);
            FileUtils.delete(new File(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StringBuilder setupCommand() {
        gdre.directory(new File(gdreDir));
        gdre.inheritIO();

        StringBuilder cmd = new StringBuilder();

        cmd.append(switch (Mashup.getPlatform()) {
            case WINDOWS      -> gdreFile;        
            case MACOS, LINUX -> "./" + gdreFile;
        });

        cmd.append(" --headless ");

        return cmd;
    }

    // TODO Generalize the command wrappers a bit more

    public void cache() {
        CBInstall cb = CBInstall.get();
        StringBuilder cmd = new StringBuilder(baseCommand);

        cmd.append(String.format("--list-files=%s ", cb.pck));

        gdre.command(cmd.toString());
        // From here, we want to eventually cache the files from the
        // output of the previously-set command.
        //
        // TODO Mashup.getTempDir()
        // TODO Run the subprocess and ensure it works properly
    }

    public void patch (
        String output,
        List<String> sources,
        List<String> targets
    ) {
        if (sources.size() != targets.size())
            throw new IllegalArgumentException();

        CBInstall cb = CBInstall.get();
        StringBuilder cmd = new StringBuilder(baseCommand);
        
        cmd.append(String.format("--pck-patch=%s ", cb.pck));
        cmd.append(String.format("--output=%s ", Mashup.getDataDir()));

        if (!cb.isCached()) throw new IllegalStateException(
            "Cassette Beasts files must be cached prior to patching!");

        for (int i = 0; i < targets.size(); i++) {
            String source = sources.get(i);
            String target = targets.get(i);
            
            // TODO Check if the sources and targets are valid 

            cmd.append(String.format("--patch-file=%s=%s ", source, target));
        }
    }
}
