import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

import java.util.HashMap;

import static java.util.Objects.isNull;

public class ForegroundChecker {

    public static void main(String[] args) {
        HashMap<String, Integer> usedWindows = new HashMap<>();
        while (true) {
            byte[] windowText = new byte[512];
            PointerType foregroundWindow = User32.INSTANCE.GetForegroundWindow();
            User32.INSTANCE.GetWindowTextA(foregroundWindow, windowText, 512);
            if (isNull(usedWindows.get(Native.toString(windowText)))) usedWindows.put(Native.toString(windowText), 0);
            usedWindows.put(Native.toString(windowText), usedWindows.get(Native.toString(windowText)) + 8);

            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
        WinDef.HWND GetForegroundWindow();  // add this
        int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
    }
}
