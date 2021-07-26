package com.lovecredit;

import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

import java.util.HashMap;

import static java.util.Objects.isNull;

public class ForegroundCheck {

    public static void main(String[] args) {
        HashMap<String, Double> usedWindows = new HashMap<>();
        byte[] windowText = new byte[512];
        double waitDuration = 100;
        int sleepDuration = 100;
        GUI gui = new GUI();
        int i = 0;
        while (i < 36000) {
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
        IOExcel.saveAsExcelFile(usedWindows);
    }

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
        WinDef.HWND GetForegroundWindow();
        int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
        int GetWindowThreadProcessId(WinDef.HWND hWnd, IntByReference lpdwProcessId);
    }
}
