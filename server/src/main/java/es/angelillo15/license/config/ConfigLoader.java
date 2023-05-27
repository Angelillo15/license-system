package es.angelillo15.license.config;

import es.angelillo15.configmanager.ConfigManager;
import es.angelillo15.license.LicenseServer;
import lombok.Getter;

import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class ConfigLoader {
    @Getter
    private static ConfigManager configManager;
    @Getter
    private static String path;

    public static void load(){
        CodeSource codeSource = LicenseServer.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        path = jarFile.getParentFile().getPath() + File.separator + "/config/";
    }

    public static void loadConfig() {
        load();
        configManager = new ConfigManager(new File(path).toPath(), "config.yml", "config.yml");
        configManager.registerConfig();
    }
}