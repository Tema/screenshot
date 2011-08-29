package com.screenshot.gui;

import javax.swing.JOptionPane;

public class Messenger {

    private static final String CAPTION = "Screenshot";

    public Messenger() {}

    public void sendingSnapshot(){
    }

    public void snapshotIsSent(){
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Screenshot", JOptionPane.ERROR_MESSAGE);
    }
}
