package com.screenshot.gui;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.screenshot.ScreenshotListener;
import com.screenshot.Settings;
import com.screenshot.util.Messenger;
import com.screenshot.util.ScreenUtils;

public class ScreenshotApp {

    private final JFrame frame;
    ScreenshotPanel screenshotPanel;

    public ScreenshotApp(ScreenshotListener listener, Messenger messenger) {
        frame = new JFrame("Screenshot");
        screenshotPanel = new ScreenshotPanel(listener, messenger);
        frame.getContentPane().add(screenshotPanel);
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
    }

    public void open() {
        screenshotPanel.init();
        Rectangle screen = ScreenUtils.getScreenBounds();
        frame.setLocation(screen.getLocation());
        frame.setSize(screen.width, screen.height);
        frame.setVisible(true);
        frame.toFront();
    }

    public void close(){
        screenshotPanel.clear();
        frame.setVisible(false);
    }

}
