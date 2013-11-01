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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;
import com.screenshot.Settings;

public class PicasaClient {

    public String upload(BufferedImage img) throws ServiceException, IOException {
        PicasawebService picasa = new PicasawebService("Screenshot");
        String account = Settings.getInstance().getGoogleAccount();
        picasa.setUserCredentials(account + "@gmail.com", Settings.getInstance().getGooglePwd());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "PNG", os);
        MediaSource mediaSrc = new MediaByteArraySource(os.toByteArray(), "image/png");
        String feedURL = "https://picasaweb.google.com/data/feed/api/user/" + account + "/albumid/default";
        PhotoEntry returnedPhoto = picasa.insert(new URL(feedURL), PhotoEntry.class, mediaSrc);

        URL result = new URL(returnedPhoto.getMediaEditLink().getHref());
        return "http://" + result.getHost() + result.getPath();
    }
}
