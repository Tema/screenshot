/*
 * Copyright (C) Tema
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.screenshot;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import com.screenshot.gui.ScreenshotApp;
import com.screenshot.upload.FtpClient;
import com.screenshot.upload.PicasaClient;
import com.screenshot.util.Messenger;

public class ScreenshotTaker implements ScreenshotListener {

    private Messenger messenger;
    private ScreenshotApp app;

    public ScreenshotTaker(Messenger messenger) {
        this.messenger = messenger;
    }

    public void start(){
        if (app != null) {
            app.close("Start new", true, false);
        }
        app = new ScreenshotApp(this, messenger);    
    }

    @Override
    public void screenshotSelected(BufferedImage img) {
        try {
            app.close("snapshot is took", false, false);
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
                    messenger.error("Can't open URL " + url + ". Error message: " + e.getMessage(), e);
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
            messenger.error("Can't send screenshot.\nReason: '" + e.getMessage() + "'.\nCheck google account settings or internet access.", e);
            return null;
        } catch (NoClassDefFoundError error){
            messenger.error("Can't send screenshot.\nReason: '" + error.getMessage() + "'.\nCheck google account settings or internet access.", error);
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
            messenger.error("Can't send screenshot.\nReason: '" + e.getMessage() + "'.\nCheck FTP settings or internet connection.", e);
            return null;
        }
    }

    @Override
    public void screenshotCancelled() {
        app.close("screenshot is cancelled", false, true);
    }

}
