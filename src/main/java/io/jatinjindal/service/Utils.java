package io.jatinjindal.service;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Utils {

    private static final Robot robot;
    private static final Random random = new Random();

    static {
        try { robot = new Robot(); }
        catch (AWTException e) { throw new RuntimeException(e); }
    }

    public static void delay(int fixed, int variable) {
        robot.delay(fixed + random.nextInt(variable));
    }

    public static void typeChar(char c) {
        if (c >= '0' && c <= '9') {
            int keyCode = KeyEvent.VK_0 + (c - '0');
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode); return;
        }

        if (c >= 'A' && c <= 'Z') {
            int keyCode = KeyEvent.VK_A + (c - 'A');
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode); return;
        }

        if (c == '#') {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_3);

            robot.keyRelease(KeyEvent.VK_3);
            robot.keyRelease(KeyEvent.VK_SHIFT); return;
        }

        throw new IllegalArgumentException("Illegal character " + c);
    }
}
