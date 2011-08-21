package com.screenshot;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

import com.screenshot.gui.Messenger;
import com.screenshot.gui.ScreenshotApp;
import com.screenshot.util.FtpClient;
import com.screenshot.util.Settings;

public class ScreenshotTaker implements ScreenshotListener {

    public Robot robot;
    private Messenger messenger;
    private ScreenshotApp app;

    public ScreenshotTaker(Messenger messenger) {
        this.messenger = messenger;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            messenger.showError("The platform configuration does not allow low-level input control. Can't take screenshot. Sorry.");
            e.printStackTrace();
        }

    }

    public void start(){
        app = new ScreenshotApp(robot.createScreenCapture(getScreenBounds()), this);
    }

    @Override
    public void screenshotSelected(BufferedImage img) {
        app.close();
        String fileName = UUID.randomUUID().toString() + ".png";
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(Settings.getInstance().getHttpBaseUrl() + "/" + fileName), null);
        messenger.sendingSnapshot();
        try {
            new FtpClient().upload(fileName, img);
            messenger.snapshotIsSent();
        } catch (IOException e) {
            messenger.showError("Can't send screenshot. Reason: '" + e.getMessage() + "'. Check FTP settings.");
            e.printStackTrace();
        }
    }

    @Override
    public void screenshotCancelled() {
        app.close();
    }

    private static Rectangle getScreenBounds() {
        Rectangle virtualBounds = new Rectangle();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (GraphicsDevice gd : ge.getScreenDevices()) {
            for (GraphicsConfiguration aGc : gd.getConfigurations()) {
                virtualBounds = virtualBounds.union(aGc.getBounds());
            }
        }
        return virtualBounds;
    }
}
