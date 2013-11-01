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
package com.screenshot.util;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class ScreenUtils {

    public static Rectangle getScreenBounds() {
        Rectangle virtualBounds = new Rectangle();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (GraphicsDevice gd : ge.getScreenDevices()) {
            for (GraphicsConfiguration aGc : gd.getConfigurations()) {
                virtualBounds = virtualBounds.union(aGc.getBounds());
            }
        }
        return virtualBounds;
    }

    public static Point getPanelLocation(Point mouse, int width, int height) {
        if (mouse != null) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            for (GraphicsDevice gd : ge.getScreenDevices()) {
                for (GraphicsConfiguration aGc : gd.getConfigurations()) {
                    Rectangle bounds = aGc.getBounds();
                    if (bounds.contains(mouse)) {
                        return leftTopCorner(width, height, bounds);
                    }
                }
            }
        }
        return leftTopCorner(width, height, new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }

    private static Point leftTopCorner(int width, int height, Rectangle bounds) {
        return new Point((int) bounds.getCenterX() - width / 2, (int) bounds.getCenterY() - height / 2);
    }
}
