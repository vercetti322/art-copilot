package io.jatinjindal.service;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;
import io.jatinjindal.exception.ArtCopilotException;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.locks.LockSupport;

public class Window {

    private WinDef.HWND handle;
    public Window() { this.handle = null; }

    public void init()  {
        try {
            var process = new ProcessBuilder("mspaint.exe").start();
            handle = waitForPaintWindow(process);
        } catch (IOException e) {
            throw new ArtCopilotException(
                    "Error while starting MS Paint", e
            );
        }

        User32.INSTANCE.SetForegroundWindow(handle);
        User32.INSTANCE.ShowWindow(handle, User32.SW_MAXIMIZE);
    }

    private WinDef.HWND waitForPaintWindow(Process process) {
        long deadline = System.nanoTime() + Duration
                .ofSeconds(30).toNanos();

        while (System.nanoTime() < deadline) {
            if (!process.isAlive()) {
                throw new ArtCopilotException(
                        "MS Paint process exited before it appeared"
                );
            }

            var hwnd = findPaintWindow(process);
            if (hwnd != null) { return hwnd; }

            LockSupport.parkNanos(Duration.ofMillis(200).toNanos());
        }

        throw new ArtCopilotException(
                "MS Paint window did not appear within 15 seconds"
        );
    }

    private WinDef.HWND findPaintWindow(Process process) {
        int paintPid = (int) process.pid();
        final WinDef.HWND[] windows = new WinDef.HWND[1];

        User32.INSTANCE.EnumWindows((hwnd, _) -> {
            IntByReference pid = new IntByReference();
            User32.INSTANCE.GetWindowThreadProcessId(hwnd, pid);

            if (pid.getValue() == paintPid
                    && User32.INSTANCE.IsWindowVisible(hwnd)
            ) {
                windows[0] = hwnd; return false;
            } return true; }, null);

        return windows[0];
    }

    public void ensureFocused() {
        if (handle != null) {
            User32.INSTANCE.SetForegroundWindow(handle);
            User32.INSTANCE.ShowWindow(handle, User32.SW_MAXIMIZE);
        } else {
            throw new ArtCopilotException("Paint window not found.");
        }
    }

    public void stop() {
        if (handle != null) {
            User32.INSTANCE.PostMessage(handle, WinUser.WM_CLOSE, null, null);
            handle = null;
        } else {
            throw new ArtCopilotException("Paint window not found.");
        }
    }
}
