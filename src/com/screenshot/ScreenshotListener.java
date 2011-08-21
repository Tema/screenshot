package com.screenshot;

import java.awt.image.BufferedImage;

public interface ScreenshotListener {

    void screenshotSelected(BufferedImage img);

    void screenshotCancelled();
}
