package com.study.designpatterns.singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Advanced Singleton implementation for managing application configuration.
 * This class ensures that only one instance exists, supports lazy loading,
 * and provides thread-safe access to the configuration properties.
 */
public class ConfigurationManager {
    private static volatile ConfigurationManager instance;
    private final Properties config;
    private final String configFile;

    private ConfigurationManager(String configFile) {
        if (instance != null) {
            throw new IllegalStateException("ConfigurationManager instance already exists. Use getInstance() method.");
        }
        this.configFile = configFile;
        this.config = new Properties();
        loadConfiguration();
    }

    public static ConfigurationManager getInstance() {
        return getInstance("application.properties");
    }

    /**
     * Retrieves the ConfigurationManager singleton instance for the given
     * configuration file. Implements double-checked locking for thread safety and
     * lazy initialization.
     *
     * @param configFile path to the configuration file
     * @return singleton instance of ConfigurationManager
     */
    public static ConfigurationManager getInstance(String configFile) {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager(configFile);
                }
            }
        }
        return instance;
    }

    private void loadConfiguration() {
        try (InputStream input = new FileInputStream("src/main/resources/" + configFile)) {
            config.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load configuration file: " + configFile, ex);
        }
    }

    public String getProperty(String key) {
        return config.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }

    // Prevent cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning not allowed for Singleton");
    }

    // Handle serialization
    protected Object readResolve() {
        return getInstance();
    }

    public static void main(String[] args) {
        ConfigurationManager configManager = ConfigurationManager.getInstance("application.properties");
        System.out.println("App Name: " + configManager.getProperty("app.name", "DefaultApp"));
        System.out.println("App Version: " + configManager.getProperty("app.version", "1.0.0"));

        ConfigurationManager defaultConfigManager = ConfigurationManager.getInstance();
        if (configManager == defaultConfigManager) {
            System.out.println("Both instances are the same (Singleton confirmed)");
        } else {
            System.out.println("Instances are different (Singleton violated)");
        }
    }
}
