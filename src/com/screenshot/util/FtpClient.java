package com.screenshot.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

public class FtpClient {

    public void upload(String fileName, BufferedImage img) throws IOException {

        OutputStream os = null;
        try {
            URL url = new URL(Settings.getInstance().getFtpUrl() + "/" + fileName + ";type=i");
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
}
