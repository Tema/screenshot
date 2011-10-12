package com.screenshot.gui;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.screenshot.ScreenshotListener;
import com.screenshot.Settings;
import com.screenshot.util.ScreenUtils;

public class ScreenshotApp {

    private final JFrame frame;

    public ScreenshotApp(BufferedImage screenCapture, ScreenshotListener listener) {
        frame = new JFrame("Screenshot");
        frame.getContentPane().add(new ScreenshotPanel(screenCapture, listener));
        Rectangle screen = ScreenUtils.getScreenBounds();
        frame.setLocation(screen.getLocation());
        frame.setSize(screen.width, screen.height);
        frame.setUndecorated(true);
        frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    frame.dispose();
                    if (!Settings.getInstance().isSystemTrayMode()){
                        System.exit(0);
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    public void close(){
        frame.dispose();
    }

}
