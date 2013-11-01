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
package com.screenshot.gui;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.screenshot.ScreenshotListener;
import com.screenshot.util.Messenger;
import com.screenshot.util.ScreenUtils;

public class ScreenshotPanel extends JLabel implements MouseListener, MouseMotionListener{

    private static final float DARK_FACTOR = 0.6f;

    private Point startPoint;
    private Point endPoint;
    private ScreenshotListener screenshotListener;
    private BufferedImage screenCapture;
    private BufferedImage selectedImage;
    private Robot robot;

    public ScreenshotPanel(ScreenshotListener screenshotListener, Messenger messenger) {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            messenger.error("The platform configuration does not allow low-level input control. Can't take screenshot. Sorry.", e);
        }
        this.screenshotListener = screenshotListener;
        init();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void init(){
        this.screenCapture = robot.createScreenCapture(ScreenUtils.getScreenBounds());
        setIcon(new ImageIcon(new RescaleOp(DARK_FACTOR, 0, null).filter(this.screenCapture, null)));
    }

    public void clear() {
        screenCapture = null;
        startPoint = endPoint = null;
        setIcon(null);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedImage != null) {
            screenshotListener.screenshotSelected(selectedImage);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startPoint != null && endPoint != null) {
            Rectangle r = normalise(startPoint, endPoint);
            if (r.width > 0 && r.height > 0) {
                selectedImage = screenCapture.getSubimage(r.x, r.y, r.width, r.height);
                g.drawImage(selectedImage, r.x, r.y, null);
                g.drawRect(r.x, r.y, r.width, r.height);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        endPoint = e.getPoint();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3){
            screenshotListener.screenshotCancelled();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) { }

    public static Rectangle normalise(Point p1, Point p2){
        int x, y, w, h;

        if (p1.x < p2.x){
            x = p1.x;
            w = p2.x - x;
        } else {
            x = p2.x;
            w = p1.x - x;
        }

        if (p1.y < p2.y){
            y = p1.y;
            h = p2.y - y;
        } else {
            y = p2.y;
            h = p1.y - y;
        }

        return new Rectangle(x, y, w, h);
    }
}
