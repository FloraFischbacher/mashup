package dev.floraf;

import org.apache.logging.log4j.Logger;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;

public class Mashup {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String VERSION = "0.1.0-alpha";

    private boolean graphical = true;

    public static void main(String[] args) {
        LOGGER.info("Initializing Mashup, version {}...", VERSION);
    }

    public enum Platform {
        WINDOWS("Windows"),
        MACOS("macOS"),
        LINUX("Linux");

        public String name;

        private Platform(String name) {
            this.name = name;
        }
    }

    public static Platform getPlatform() {
        if (SystemUtils.IS_OS_WINDOWS) {
            return Platform.WINDOWS;
        } else if (SystemUtils.IS_OS_MAC_OSX) {
            return Platform.MACOS;
        } else if (
            SystemUtils.IS_OS_LINUX
            || SystemUtils.IS_OS_FREE_BSD
            || SystemUtils.IS_OS_OPEN_BSD
            || SystemUtils.IS_OS_NET_BSD
        ) {
            return Platform.LINUX;
        } else {
            throw new UnsupportedOperationException("This OS is not supported!");
        }
    }

    public static String getConfigDir() {
        return switch (getPlatform()) {
            case Platform.WINDOWS -> System.getenv("APPDATA")
                + "/Mashup/config";
            case Platform.MACOS -> System.getProperty("user.home")
                + "/Library/Preferences/Mashup";
            case Platform.LINUX -> ObjectUtils.firstNonNull(
                System.getenv("XDG_CONFIG_HOME") + "/Mashup",
                System.getProperty("user.home") + "/.config"
            ) + "/Mashup";
        };
    }

    public static String getDataDir() {
        return switch (getPlatform()) {
            case Platform.WINDOWS -> System.getenv("APPDATA")
                + "/Mashup/data";
            case Platform.MACOS -> System.getProperty("user.home")
                + "/Library/Application Support/Mashup";
            case Platform.LINUX -> ObjectUtils.firstNonNull(
                System.getenv("XDG_DATA_HOME"),
                System.getProperty("user.home") + "/.local/share"
            ) + "/Mashup";
        };
    }
}