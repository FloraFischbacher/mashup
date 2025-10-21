package dev.floraf.loader.external;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FileUtils;

import dev.floraf.Mashup;

// TODO: Wrap over GDRE Tools instance once finally installed.
// TODO: Switch implementation of unzip to Apache Commons Compress to avoid
//       having two different archive/compression libraries installed at the
//       same time!

public class GDREToolsInstall {
    String gdreDir = Mashup.getDataDir() + "/gdre";
    String version = "2.0.0";
    ProcessBuilder gdre = new ProcessBuilder();

    public GDREToolsInstall() {
        if (!this.exists()) {
            this.download();
            this.install();
        }
    }

    public boolean exists() {
        Path gdrePath;
        String gdreFile = switch (Mashup.getPlatform()) {
            case Mashup.Platform.WINDOWS -> "gdre_tools.exe";
            case Mashup.Platform.MACOS   -> "/Godot RE Tools.app/Contents/MacOS/Godot RE Tools";
            case Mashup.Platform.LINUX   -> "gdre_tools.x86_64";
        };

        try {
            gdrePath = Path.of(gdreDir + gdreFile);
        } catch (InvalidPathException _) {
            return false;
        }

        return Files.exists(gdrePath);
    }

    public void download() {
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
            throw new RuntimeException("Could not locate GDRE Tools!");
        } catch (IOException e) {
            throw new RuntimeException("Failed to download GDRE Tools!");
        }
    }

    public void install() {
        String file = gdreDir + "gdre.zip";
        try (var zip = new ZipFile(file)) {
            zip.extractAll(gdreDir);
            FileUtils.delete(new File(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
