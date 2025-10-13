package dev.floraf;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Mashup {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String VERSION = "0.1.0-alpha";

    private boolean graphical = true;

    public static void main(String[] args) {
        LOGGER.info("Initializing Mashup, version {}...", VERSION);
    }
}