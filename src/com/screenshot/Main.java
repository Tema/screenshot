package com.screenshot;

import java.awt.SystemTray;

import com.screenshot.gui.Messenger;
import com.screenshot.gui.SettingsUI;
import com.screenshot.gui.SystemTrayDaemon;

public class Main {

    public static void main(String[] args) throws Exception {
        SettingsUI settingsUI = new SettingsUI();

        boolean showSettings = args.length > 0 && (args[0].equals("help") || args[0].equals("--help") || args[0].equals("-h"));  
        
        if (!Settings.getInstance().isPersisted() || showSettings){
            settingsUI.open(null);
        }

        ScreenshotTaker screenshotTaker = new ScreenshotTaker(new Messenger());
                
        if (Settings.getInstance().isSystemTrayMode() && SystemTray.isSupported()){
            new SystemTrayDaemon(settingsUI, screenshotTaker);
        } else {
            screenshotTaker.start();
        }
    }
}
