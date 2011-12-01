package com.screenshot;

import java.awt.SystemTray;

import javax.swing.SwingUtilities;

import com.screenshot.util.Messenger;
import com.screenshot.gui.SettingsUI;
import com.screenshot.gui.SystemTrayDaemon;

public class Main {

    public static void main(String[] args) throws Exception {
        final Messenger messenger = new Messenger();
        Thread.currentThread().setUncaughtExceptionHandler(messenger);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Thread.currentThread().setUncaughtExceptionHandler(messenger);
            }
        });
        
        SettingsUI settingsUI = new SettingsUI();

        boolean showSettings = !Settings.getInstance().isPersisted()  || 
                (args.length > 0 && (args[0].equals("help") || args[0].equals("--help") || args[0].equals("-h")));  
        
        if (showSettings){
            settingsUI.open(null);
        }

        ScreenshotTaker screenshotTaker = new ScreenshotTaker(messenger);
                
        if (Settings.getInstance().isSystemTrayMode() && SystemTray.isSupported()){
            new SystemTrayDaemon(settingsUI, screenshotTaker);
        } else {
            if (!showSettings) {// don't start capture behind settings window
                screenshotTaker.start();
            }
        }
    }
}
