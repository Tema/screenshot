package com.screenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private final String FILE_NAME = System.getProperty("user.home") + "/screenshot.properties";

    private final static Settings instance = new Settings();

    private Properties properties;
    private final String SYSTEM_TRAY_MODE = "jnlp.systemtray.mode";
    private final String PICASAWEB_MODE = "jnlp.picasaweb.mode";
    private final String FTP_URL = "jnlp.ftp.url";
    private final String FTP_USER = "jnlp.ftp.user";
    private final String FTP_PASSWORD = "jnlp.ftp.password";
    private final String HTTP_BASE_URL = "jnlp.http.base.url";
    private final String PICASAWEB_ACCOUNT = "jnlp.picasaweb.account";
    private final String PICASAWEB_PASSWORD = "jnlp.picasaweb.pwd";

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
                for (String key : System.getProperties().stringPropertyNames()) {
                    if (key.startsWith("jnlp.")){
                        put(key, System.getProperty(key));
                    }
                }
                // properties are stored in home dir (custom user preferences)
                try {load(new FileInputStream(FILE_NAME));} catch (IOException e) {/**/}
                // only for dev when app is run from IDE otherwise user.home properties would take affect
                try {load(new FileInputStream("dev.properties"));} catch (IOException e) {/**/}
            }
        };
    }

    public void save(){
        try {
            properties.store(new FileWriter(FILE_NAME), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSystemTrayMode(){
        return Boolean.valueOf(properties.getProperty(SYSTEM_TRAY_MODE));
    }

    public boolean isPicasawebMode(){
        return Boolean.valueOf(properties.getProperty(PICASAWEB_MODE));
    }

    public void setPicasawebMode(boolean mode){
        properties.setProperty(PICASAWEB_MODE, Boolean.toString(mode));
    }

    public String getFtpUrl() {
        return properties.getProperty(FTP_URL);
    }

    public String getFtpUser(){
        return properties.getProperty(FTP_USER);
    }

    public String getFtpPassword(){
        return properties.getProperty(FTP_PASSWORD);
    }

    public String getHttpBaseUrl() {
        return properties.getProperty(HTTP_BASE_URL);
    }

    public String getGoogleAccount() {
        return properties.getProperty(PICASAWEB_ACCOUNT);
    }

    public String getGooglePwd() {
        return properties.getProperty(PICASAWEB_PASSWORD);
    }

    public void setSystemTrayMode(boolean enabled){
        properties.setProperty(SYSTEM_TRAY_MODE, Boolean.toString(enabled));
    }

    public void setFtpUrl(String url){
        properties.setProperty(FTP_URL, url);
    }

    public void setFtpUser(String user){
        properties.setProperty(FTP_USER, user);
    }

    public void setFtpPassword(String password){
        properties.setProperty(FTP_PASSWORD, password);
    }

    public void setHttpBase(String url){
        properties.setProperty(HTTP_BASE_URL, url);
    }

    public void setPicasawebLogin(String login){
        properties.setProperty(PICASAWEB_ACCOUNT, login);
    }

    public void setPicasawebPassword(String password){
        properties.setProperty(PICASAWEB_PASSWORD, password);
    }

    public boolean isPersisted() {
        return new File(FILE_NAME).exists();
    }
}
