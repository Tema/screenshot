package com.screenshot;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import com.screenshot.gui.Messenger;
import com.screenshot.gui.ScreenshotApp;
import com.screenshot.upload.FtpClient;
import com.screenshot.upload.PicasaClient;
import com.screenshot.util.ScreenUtils;

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
        app = new ScreenshotApp(robot.createScreenCapture(ScreenUtils.getScreenBounds()), this);
    }

    @Override
    public void screenshotSelected(BufferedImage img) {
        try {
            app.close();

            String url;
            if (Settings.getInstance().isPicasawebMode()) {
                url = postToPicasaweb(img);
            } else {
                url = sendToFtp(img);
            }

            if (url != null) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(url), null);
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception e) {
                    e.printStackTrace();
                    messenger.showError("Can't open URL " + url + ". Error message: "  + e.getMessage());
                }
            }
        } finally {
            if (!Settings.getInstance().isSystemTrayMode()) {
                System.exit(0);
            }
        }
    }

    private String postToPicasaweb(BufferedImage img) {
        try {
            return new PicasaClient().upload(img);
        } catch (Exception e) {
            e.printStackTrace();
            messenger.showError("Can't send screenshot. Reason: '" + e.getMessage() + "'. Check google account settings.");
            return null;
        }
    }

    private String sendToFtp(BufferedImage img) {
        String fileName = UUID.randomUUID().toString() + ".png";
        String url = Settings.getInstance().getHttpBaseUrl() + "/" + fileName;
        try {
            new FtpClient().upload(fileName, img);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            messenger.showError("Can't send screenshot. Reason: '" + e.getMessage() + "'. Check FTP settings.");
            return null;
        }
    }

    @Override
    public void screenshotCancelled() {
        app.close();
    }

}
