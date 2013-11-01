/*
 * Copyright (C) Tema
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
