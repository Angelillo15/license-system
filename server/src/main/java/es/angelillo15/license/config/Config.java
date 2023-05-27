package es.angelillo15.license.config;

import org.simpleyaml.configuration.file.YamlConfiguration;

public class Config {
    public static int port() {
        YamlConfiguration config = ConfigLoader.getConfigManager().getConfig();
        return config.getInt("Config.port");
    }
}
