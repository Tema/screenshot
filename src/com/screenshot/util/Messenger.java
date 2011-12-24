package com.screenshot.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import com.screenshot.Settings;

public class Messenger implements Thread.UncaughtExceptionHandler {
    
    public final String LOG_FILE_NAME = System.getProperty("user.home") + "/screenshot.log";

    public Messenger() {
        if (!Settings.getInstance().isDevMode()) {
            try {
                PrintStream logFile = new PrintStream(new File(LOG_FILE_NAME));
                System.setErr(logFile);
                System.setOut(logFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace(System.err);
            }
        }
    }
    
    public void debug(String msg){
        if (Settings.getInstance().isDevMode()) {
            System.out.println(msg);
        }
    }
    
    public void error(String msg, Throwable th) {
        System.err.println(msg);    
        th.printStackTrace(System.err);
        JOptionPane.showMessageDialog(null, msg, "Screenshot", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(t);
        e.printStackTrace(System.err);
        if (e instanceof OutOfMemoryError){
            System.err.println("Exiting on OOM ...");
            System.exit(-1);
        }
    }
}
