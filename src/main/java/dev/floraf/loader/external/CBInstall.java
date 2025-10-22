package dev.floraf.loader.external;

import java.util.HashSet;

public class CBInstall {
     enum DLC {
        COSPLAY("00_cosplay.pck",
          "8850655e1693e39384825ebec5b81ac4d20308b7f48de0947612ec4804a800a5"),
        PIER("01_pier.pck",
          "7d3c3c569425a41d5938c87cbb86b49d8dc6b96187d2d9f9068aecd35bccad20"),
        FASHION("02_fashion.pck",
          "3319a6933b1e97b0555407ca5331e53094b25757e4718d823009060f158e9637"),
        WINGS("03_wings.pck",
          "9ef47b19cb7a1158fa4185a8bc75595157e503f0997feb2f785cff36666419e0");

        @SuppressWarnings("unused")
        private String file;

        @SuppressWarnings("unused")
        private String sha256;

        private DLC(String file, String sha256) {
            this.file = file;
            this.sha256 = sha256;
        }
    }

    private static CBInstall instance = null;

    public String dir;
    public String pck;
    private HashSet<String> files = new HashSet<>();
    private boolean cached = false;

    private CBInstall() {

    }

    public static CBInstall get() {
        if (instance == null) instance = new CBInstall();
        return instance;
    }

    public boolean[] getInstalledDLC() {
        boolean[] installed = {false, false, false, false};

        for (DLC dlc : DLC.values()) {
            // Check whether DLC in install folder and whether checksum of that
            // file matches the other one as expected.
        }

        return installed;
    }

    public boolean isCached() {
        return cached;
    }
}
