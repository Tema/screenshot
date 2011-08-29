package com.screenshot.gui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;

import com.screenshot.ScreenshotTaker;
import com.screenshot.util.Settings;

import static java.awt.event.MouseEvent.BUTTON1;

public class SystemTrayDaemon {

    public SystemTrayDaemon() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();

        PopupMenu popup = new PopupMenu();

        MenuItem ftpItem = new MenuItem(" Ftp ");
        ftpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Settings.getInstance().setPicasawebMode(false);
                start();
            }
        });
        popup.add(ftpItem);

        MenuItem picasawebItem = new MenuItem(" Picasaweb ");
        picasawebItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Settings.getInstance().setPicasawebMode(true);
                start();
            }
        });
        popup.add(picasawebItem);

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
                if (e.getButton() == BUTTON1) {
                    start();
                }
            }
        });

        tray.add(trayIcon);
    }

    private void start() {
        new ScreenshotTaker(new Messenger()).start();
    }

}
