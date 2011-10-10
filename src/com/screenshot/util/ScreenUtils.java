package com.screenshot.util;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class ScreenUtils {

    private static Rectangle virtualBounds;

    public static Rectangle getScreenBounds() {
        //TODO: reset after open second time
        if (virtualBounds == null) {
            virtualBounds = new Rectangle();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            for (GraphicsDevice gd : ge.getScreenDevices()) {
                for (GraphicsConfiguration aGc : gd.getConfigurations()) {
                    virtualBounds = virtualBounds.union(aGc.getBounds());
                }
            }
        }
        return virtualBounds;
    }

    public static Point getPanelLocation(Point mouse, int width, int height){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (GraphicsDevice gd : ge.getScreenDevices()) {
            for (GraphicsConfiguration aGc : gd.getConfigurations()) {
                Rectangle bounds = aGc.getBounds();
                if (bounds.contains(mouse)){
                    return leftTopCorner(width, height, bounds);
                }
            }
        }
        return leftTopCorner(width, height, new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }

    private static Point leftTopCorner(int width, int height, Rectangle bounds) {
        return new Point((int) bounds.getCenterX() - width/2, (int) bounds.getCenterY() - height/2);
    }
}
