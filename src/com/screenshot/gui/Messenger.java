package com.screenshot.gui;

import java.awt.TrayIcon;

import javax.swing.JOptionPane;

import static java.awt.TrayIcon.MessageType.INFO;

public class Messenger {

    private static final String CAPTION = "Screenshot";

    private TrayIcon trayIcon;

    public Messenger() {}

    public Messenger(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    public void sendingSnapshot(){
        if (trayIcon != null) {
            trayIcon.displayMessage(CAPTION, "URL on the snapshot is copied to the clipboard. Sending the snapshot...", INFO);
        }
    }

    public void snapshotIsSent(){
        if (trayIcon != null) {
            trayIcon.displayMessage(CAPTION, "URL on the snapshot is copied to the clipboard. Snapshot is sent.", INFO);
        }
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Screenshot", JOptionPane.ERROR_MESSAGE);
    }
}
