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
package com.screenshot.upload;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import com.screenshot.Settings;

public class FtpClient {

    public void upload(String fileName, BufferedImage img) throws IOException {

        OutputStream os = null;
        try {
            URL url = new URL(construct() + "/" + fileName + ";type=i");
            URLConnection con = url.openConnection();
            os = con.getOutputStream();
            ImageIO.write(img, "PNG", os);

        } catch (MalformedURLException e) {
            throw new IOException(e.getMessage());
        } finally {
            if (os != null) {
                os.close();
            }
        }

}

    private String construct() {
        Settings settings = Settings.getInstance();
        String login = "";
        if (!"".equals(settings.getFtpUser())) {
            login = settings.getFtpUser() + ":" + settings.getFtpPassword() + "@";
        }
        String ftpUrl = settings.getFtpUrl();
        if (ftpUrl.startsWith("ftp://")){
            ftpUrl = ftpUrl.substring(6);
        }
        return "ftp://" + login + ftpUrl;
    }
}
