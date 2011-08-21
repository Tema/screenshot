package com.screenshot.gui;

import java.awt.Cursor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.screenshot.ScreenshotListener;

public class ScreenshotApp {

    private final JFrame frame;

    public ScreenshotApp(BufferedImage screenCapture, ScreenshotListener listener) {
        frame = new JFrame("Screenshot");
        frame.getContentPane().add(new ScreenshotPanel(screenCapture, listener));
        frame.setSize(screenCapture.getWidth(), screenCapture.getHeight());
        frame.setUndecorated(true);
        frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    frame.dispose();
                }
            }
        });

        frame.setVisible(true);
    }

    public void close(){
        frame.dispose();
    }

}
