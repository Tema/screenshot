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
        properties = new Properties(){
            {
                //the later property has higher priority

                // properties that are packed in jar
                try {load(getClass().getClassLoader().getResourceAsStream("screenshot.properties"));} catch (Exception e) {/**/}
                // properties are defined in jnlp
                putAll(System.getProperties());
                // properties are stored in home dir (custom user preferences)
                try {load(new FileInputStream(System.getProperty("user.home") + "/screenshot.properties"));} catch (IOException e) {/**/}
                // only for dev when app is run from IDE otherwise user.home properties would take affect
                try {load(new FileInputStream("dev.properties"));} catch (IOException e) {/**/}
            }
        };
    }

    public boolean isSystemTrayMode(){
        return Boolean.valueOf(properties.getProperty("jnlp.systemtray.mode"));
    }

    public boolean isPicasawebMode(){
        return Boolean.valueOf(properties.getProperty("jnlp.picasaweb.mode"));
    }

    public String getFtpUrl() {
        return properties.getProperty("jnlp.ftp.url");
    }

    public String getHttpBaseUrl() {
        return properties.getProperty("jnlp.http.base.url");
    }

    public String getGoogleAccount() {
        return properties.getProperty("jnlp.google.account");
    }

    public String getGooglePwd() {
        return properties.getProperty("jnlp.google.pwd");
    }
}
