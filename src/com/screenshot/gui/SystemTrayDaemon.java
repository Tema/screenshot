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

import static java.awt.event.MouseEvent.BUTTON1;

public class SystemTrayDaemon {

    public SystemTrayDaemon() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();

        ActionListener exitListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting...");
                System.exit(0);
            }
        };

        PopupMenu popup = new PopupMenu();
        MenuItem defaultItem = new MenuItem(" Exit ");
        defaultItem.addActionListener(exitListener);
        popup.add(defaultItem);

        URL imgURL = getClass().getClassLoader().getResource("icon.png");
        final TrayIcon trayIcon = new TrayIcon(new ImageIcon(imgURL).getImage(), "Screenshot", popup);

        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == BUTTON1) {
                    new ScreenshotTaker(new Messenger(trayIcon)).start();
                }
            }
        });

        tray.add(trayIcon);
    }

}
