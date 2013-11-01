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
package com.screenshot.gui;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;

import com.screenshot.ScreenshotTaker;
import com.screenshot.Settings;

import static java.awt.event.MouseEvent.BUTTON1;

public class SystemTrayDaemon {

    private Point mousePoint;
    private ScreenshotTaker screenshotTaker;

    public SystemTrayDaemon(final SettingsUI settingsUI, ScreenshotTaker screenshotTaker) throws AWTException {
        this.screenshotTaker = screenshotTaker;
        init(settingsUI);
    }

    private void init(final SettingsUI settingsUI) throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();

        PopupMenu popup = new PopupMenu();

        final CheckboxMenuItem ftpItem = new CheckboxMenuItem(" Ftp ");
        final CheckboxMenuItem picasawebItem = new CheckboxMenuItem(" Picasaweb ");

        ftpItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Settings.getInstance().setPicasawebMode(false);
                Settings.getInstance().save();
                start();
            }
        });
        popup.add(ftpItem);

        picasawebItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Settings.getInstance().setPicasawebMode(true);
                Settings.getInstance().save();
                start();
            }
        });
        popup.add(picasawebItem);

        checkMenuItem(picasawebItem, ftpItem, Settings.getInstance().isPicasawebMode());

        MenuItem settingsItem = new MenuItem(" Settings ");
        settingsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                settingsUI.open(mousePoint);
            }
        });
        popup.add(settingsItem);

        MenuItem exitItem = new MenuItem(" Exit ");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting...");
                System.exit(0);
            }
        });
        popup.add(exitItem);

        URL imgURL = getClass().getClassLoader().getResource("icon.png");
        final TrayIcon trayIcon = new TrayIcon(new ImageIcon(imgURL).getImage(), "Screenshot", popup);

        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                checkMenuItem(picasawebItem, ftpItem, Settings.getInstance().isPicasawebMode());
            }
            public void mouseClicked(MouseEvent e) {
                mousePoint = e.getPoint();
                if (e.getButton() == BUTTON1) {
                    start();
                }
            }
        });

        tray.add(trayIcon);
    }

    private void checkMenuItem(CheckboxMenuItem picasawebItem, CheckboxMenuItem ftpItem, boolean picasawebMode) {
        picasawebItem.setState(picasawebMode);
        ftpItem.setState(!picasawebMode);
    }

    private void start() {
        screenshotTaker.start();
    }

}
