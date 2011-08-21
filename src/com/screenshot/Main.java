package com.screenshot;

import java.awt.SystemTray;

import com.screenshot.gui.Messenger;
import com.screenshot.gui.SystemTrayDaemon;
import com.screenshot.util.Settings;

public class Main {

    public static void main(String[] args) throws Exception {

        if (Settings.getInstance().isSystemTrayMode() && SystemTray.isSupported()){
            new SystemTrayDaemon();
        } else {
            new ScreenshotTaker(new Messenger()).start();
        }
    }
}
