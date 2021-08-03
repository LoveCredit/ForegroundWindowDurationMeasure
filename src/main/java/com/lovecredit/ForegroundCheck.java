package com.lovecredit;

import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

import java.util.HashMap;

import static java.util.Objects.isNull;

public class ForegroundCheck {

    public static boolean gUIActive = true;
    public static HashMap<String, Double> usedWindows;

    public static void main(String[] args) {
        HashMap<String, Double> usedWindows;
        byte[] windowText = new byte[512];
        double waitDuration = 100;
        int sleepDuration = 100;
        GUI gui = new GUI();
        while (true) {
            usedWindows = new HashMap<>();
            int i = 0;
            while (i < 12000) {
                PointerType foregroundWindow = User32.INSTANCE.GetForegroundWindow();
                User32.INSTANCE.GetWindowTextA(foregroundWindow, windowText, 512);
                if (isNull(usedWindows.get(Native.toString(windowText))))
                    usedWindows.put(Native.toString(windowText), 0.);
                usedWindows.put(Native.toString(windowText), usedWindows.get(Native.toString(windowText)) + waitDuration / 60000); // + " - " + processName
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                gui.visualizeForegroundCheckData(usedWindows);
            }
            HashMap<String, Double> usedWindowWithThreshold = new HashMap();
            usedWindows.forEach((k, v) -> {
                if (v > 4) {
                    usedWindowWithThreshold.put(k, v);
                }
            });
            if (!usedWindowWithThreshold.isEmpty()) IOExcel.saveAsExcelFile(usedWindowWithThreshold);

        }
    }

    public static void foregroundCheck() {
        usedWindows = new HashMap<>();
        byte[] windowText = new byte[512];
        double waitDuration = 100;
        int sleepDuration = 100;
        GUI gui = GUI.getInstance();
        while (gUIActive) {
            PointerType foregroundWindow = User32.INSTANCE.GetForegroundWindow();
            User32.INSTANCE.GetWindowTextA(foregroundWindow, windowText, 512);
            if (isNull(usedWindows.get(Native.toString(windowText))))
                usedWindows.put(Native.toString(windowText), 0.);
            usedWindows.put(Native.toString(windowText), usedWindows.get(Native.toString(windowText)) + waitDuration / 60000); // + " - " + processName
            try {
                Thread.sleep(sleepDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gui.visualizeForegroundCheckData(usedWindows);
        }
    }

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
        WinDef.HWND GetForegroundWindow();
        int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
    }
}
