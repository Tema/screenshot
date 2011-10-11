package com.screenshot.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.screenshot.Settings;
import com.screenshot.util.ScreenUtils;

import static java.awt.BorderLayout.EAST;

public class SettingsUI {

    private final JFrame frame = new JFrame("Settings");

    private JPanel settingPanel = new JPanel();

    private JCheckBox systemTrayModeCheckBox = new JCheckBox("System Tray Mode (restart is required)");

    private ButtonGroup tabSwitcher = new ButtonGroup();

    private JRadioButton ftpRadioButton = new JRadioButton("FTP ");
    private JRadioButton picasawebRadioButton = new JRadioButton("Picasaweb ");
    {
        tabSwitcher.add(ftpRadioButton);
        tabSwitcher.add(picasawebRadioButton);
    }

    private JPanel transportTab = new JPanel();
    private JTextField ftpUrl = new JTextField(20);
    private JTextField ftpUser = new JTextField();
    private JTextField ftpPassword = new JPasswordField();

    private JTextField httpBase = new JTextField();
    private JTextField picasawebLogin = new JTextField();

    private JTextField picasawebPassword = new JPasswordField();

    private JButton okButton = new JButton("Save");

    private JButton cancelButton = new JButton("Cancel");

    GridBagConstraints layout = new GridBagConstraints();
    private int row = 0;
    private final JLabel ftpUrlLabel = new JLabel("URL ");
    private final JLabel ftpUserLabel = new JLabel("User ");
    private final JLabel ftpPasswordLabel = new JLabel("Password ");
    private final JLabel httpBaseLabel = new JLabel("Http base ");
    private final JLabel picasawebLoginLabel = new JLabel("Login ");
    private final JLabel picasawebPasswordLabel = new JLabel("Password ");

    public SettingsUI() {
        fill();
        frame.setContentPane(settingPanel);
        settingPanel.setLayout(new GridBagLayout());
        layout();
        bind();
        frame.pack();
    }

    private void fill() {
        Settings settings = Settings.getInstance();
        systemTrayModeCheckBox.setSelected(settings.isSystemTrayMode());
        picasawebRadioButton.setSelected(settings.isPicasawebMode());
        ftpRadioButton.setSelected(!settings.isPicasawebMode());
        ftpUrl.setText(settings.getFtpUrl());
        ftpUser.setText(settings.getFtpUser());
        ftpPassword.setText(settings.getFtpPassword());
        httpBase.setText(settings.getHttpBaseUrl());
        picasawebLogin.setText(settings.getGoogleAccount());
        picasawebPassword.setText(settings.getGooglePwd());
    }

    public void open(Point mouse){
        switchTab(Settings.getInstance().isPicasawebMode());
        frame.setLocation(ScreenUtils.getPanelLocation(mouse, frame.getWidth(), frame.getHeight()));
        frame.setVisible(true);
    }

    public void close() {
        frame.setVisible(false);
    }

    private void layout() {
        settingPanel.setBorder(new LineBorder(settingPanel.getBackground(), 5));

        add(0, sameRow(), 2, systemTrayModeCheckBox);

        add(0, nextRow(), 2, new JLabel(" "));

        add(0, sameRow(), 2, layoutTransportTab());

        add(0, nextRow(), 2, new JLabel(" "));

        add(0, nextRow(), 2, layoutButtons());
    }

    private JComponent layoutButtons() {
        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 5, 0));
        panel.add(okButton);
        panel.add(cancelButton);
        parentPanel.add(panel, EAST);
        return parentPanel;
    }

    private JPanel layoutTransportTab() {
        transportTab.setLayout(new GridBagLayout());

        add(0, 0, ftpRadioButton, transportTab);
        layout.weighty = 0.25;
        add(1, 0, ftpUrlLabel, transportTab);
        layout.weightx = 1.0;
        add(2, 0, ftpUrl, transportTab);
        layout.weightx = 0.0;
        add(1, 1, ftpUserLabel, transportTab);add(2, 1, ftpUser, transportTab);
        add(1, 2, ftpPasswordLabel, transportTab);add(2, 2, ftpPassword, transportTab);
        add(1, 3, httpBaseLabel, transportTab);add(2, 3, httpBase, transportTab);
        layout.weighty = 0.0;

        add(0, 4, new JLabel(" "), transportTab);

        add(0, 5, picasawebRadioButton, transportTab);
        add(1, 5, picasawebLoginLabel, transportTab);
        layout.weightx = 1.0;
        add(2, 5, picasawebLogin, transportTab);
        layout.weightx = 0.0;
        add(1, 6, picasawebPasswordLabel, transportTab);
        add(2, 6, picasawebPassword, transportTab);

        return transportTab;
    }

    private void bind() {
        ftpRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (ftpRadioButton.isSelected()){
                    switchTab(false);
                }
            }
        });
        picasawebRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (picasawebRadioButton.isSelected()){
                    switchTab(true);
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
                close();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
    }

    private void save() {
        Settings settings = Settings.getInstance();
        settings.setPicasawebMode(picasawebRadioButton.isSelected());
        settings.setSystemTrayMode(systemTrayModeCheckBox.isSelected());
        settings.setFtpUrl(ftpUrl.getText().trim());
        settings.setFtpUser(ftpUser.getText().trim());
        settings.setFtpPassword(ftpPassword.getText().trim());
        settings.setHttpBase(httpBase.getText().trim());
        settings.setPicasawebLogin(picasawebLogin.getText().trim());
        settings.setPicasawebPassword(picasawebPassword.getText().trim());
        settings.save();
    }

    private void switchTab(boolean picasaweb){
        picasawebRadioButton.setSelected(picasaweb);
        ftpRadioButton.setSelected(!picasaweb);
        enable(picasaweb, picasawebLogin, picasawebPassword, picasawebLoginLabel, picasawebPasswordLabel);
        enable(!picasaweb, ftpPassword, ftpUrl, ftpUser, httpBase, ftpPasswordLabel, ftpUrlLabel, ftpUserLabel, httpBaseLabel);
    }

    private void enable(boolean enable, JComponent... args){
        for (JComponent component : args) {
            component.setEnabled(enable);
        }
    }

    private void add(int x, int y, JComponent component) {
        add(x, y, component, settingPanel);
    }

    private void add(int x, int y, int width, JComponent component) {
        layout.gridwidth = width;
        add(x, y, component, settingPanel);
        layout.gridwidth = 1;
    }

    private void add(int x, int y, JComponent component, JPanel pane) {
        layout.fill = GridBagConstraints.HORIZONTAL;
        //layout.weightx = 0.5;
        layout.gridx = x;
        layout.gridy = y;
        pane.add(component, layout);
    }

    private int sameRow(){
        return row;
    }

    private int nextRow(){
        return ++row;
    }

    public static void main(String[] args) {
        new SettingsUI();
    }

}