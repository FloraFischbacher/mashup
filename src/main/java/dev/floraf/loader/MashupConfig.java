package dev.floraf.loader;

/**
 * A singleton object representing the user's configuration of Mashup.
 * Will create a configuration file if one is not found.
 */
public class MashupConfig {
    private static MashupConfig instance = null;

    private MashupConfig() {
        instance = this;
    }

    public static MashupConfig get() {
        if (instance == null) instance = new MashupConfig();
        return instance;
    }
}
