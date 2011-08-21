package com.screenshot.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private final static Settings instance = new Settings();

    private Properties properties;

    public static Settings getInstance() {
        return instance;
    }

    private Settings() {
        properties = new Properties(){{
            try {
                load(new FileInputStream("dev.properties"));
            } catch (IOException e) {
                // use system properties
            }}

            @Override
            public String getProperty(String key) {
                String value = super.getProperty(key);
                return value != null ? value : System.getProperty(key);
            }
        };
    }

    public boolean isSystemTrayMode(){
        return Boolean.valueOf(properties.getProperty("jnlp.systemtray.mode"));
    }

    public String getFtpUrl() {
        return properties.getProperty("jnlp.ftp.url");
    }

    public String getHttpBaseUrl() {
        return properties.getProperty("jnlp.http.base.url");
    }
}
