package com.screenshot;

import java.awt.SystemTray;

import com.screenshot.gui.Messenger;
import com.screenshot.gui.SettingsUI;
import com.screenshot.gui.SystemTrayDaemon;

public class Main {

    public static void main(String[] args) throws Exception {
        SettingsUI settingsUI = new SettingsUI();

        if (!Settings.getInstance().isPersisted()){
            settingsUI.open(null);
        }

        if (Settings.getInstance().isSystemTrayMode() && SystemTray.isSupported()){
            new SystemTrayDaemon(settingsUI);
        } else {
            new ScreenshotTaker(new Messenger()).start();
        }
    }
}
