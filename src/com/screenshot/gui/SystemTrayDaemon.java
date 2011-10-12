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

    public SystemTrayDaemon(final SettingsUI settingsUI) throws AWTException {
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
            public void mouseClicked(MouseEvent e) {
                checkMenuItem(picasawebItem, ftpItem, Settings.getInstance().isPicasawebMode());
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
        new ScreenshotTaker(new Messenger()).start();
    }

}
