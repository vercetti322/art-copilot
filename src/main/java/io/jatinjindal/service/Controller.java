package io.jatinjindal.service;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public final class Controller {

    private final Window window;
    private final Robot robot;
    private final Random random;
    private final Clipboard clipboard;

    public Controller() throws AWTException {
        this.window = new Window(); this.robot = new Robot();
        this.random = new Random();
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public void startWindow() {
        window.init(); Utils.delay(2000, 500);
    }

    public void stopWindow() {
        Utils.delay(2000, 500); window.stop();
    }

    public void drawLine(Point start, Point end) {
        window.ensureFocused(); Utils.delay(150, 100);
        robot.mouseMove(start.x, start.y);

        Utils.delay(150, 100);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        int steps = 6 + random.nextInt(4);
        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            int x = (int) (start.x + (end.x - start.x) * t);

            int y = (int) (start.y + (end.y - start.y) * t);
            robot.mouseMove(x, y); robot.delay(10);
        }

        Utils.delay(150, 100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void drawRectangle(Point start, Point end, String hexColor) {
        Point topRight = new Point(end.x, start.y);
        Point bottomLeft = new Point(start.x, end.y);

        drawLine(start, topRight); drawLine(topRight, end);
        drawLine(end, bottomLeft); drawLine(bottomLeft, start);

        Point center = new Point((start.x + end.x) / 2,
                (start.y + end.y) / 2
        );

        Utils.delay(150, 100); pickColor(hexColor);
        paintColor(center); Utils.delay(150, 100);
    }

    private void paintColor(Point center) {
        robot.keyPress(KeyEvent.VK_B); Utils.delay(150, 100);
        robot.keyRelease(KeyEvent.VK_B); Utils.delay(150, 100);

        robot.mouseMove(center.x, center.y); Utils.delay(150, 100);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);

        Utils.delay(150, 100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void pickColor(String hexColor) {
        // open color palette
        clipboard.setContents(new StringSelection(""), null);
        Utils.delay(150, 100);

        openColorPicker(); int i = 10;
        // go to hex color input field
        while (i > 0) {
            checkForHexColorField();
            String value = getClipboardText();

            Utils.delay(150, 100);
            if ("#000000".equals(value)) { break; } i--;
        }

        // type the hex color and press ENTER
        selectHexColor(hexColor);

        clipboard.setContents(new StringSelection(""), null);
        Utils.delay(300, 150);
    }

    private void selectHexColor(String hexColor) {
        robot.keyPress(KeyEvent.VK_CONTROL); Utils.delay(150, 100);
        robot.keyPress(KeyEvent.VK_A); Utils.delay(150, 100);

        robot.keyRelease(KeyEvent.VK_A); Utils.delay(150, 100);
        robot.keyRelease(KeyEvent.VK_CONTROL); Utils.delay(150, 100);

        for (char c : hexColor.toCharArray()) {
            Utils.typeChar(c); Utils.delay(100, 50);
        }

        robot.keyPress(KeyEvent.VK_ENTER); Utils.delay(150, 100);
        robot.keyRelease(KeyEvent.VK_ENTER); Utils.delay(150, 100);
    }

    private void openColorPicker() {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_ALT); Utils.delay(150, 100);

        robot.keyPress(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_E); Utils.delay(150, 100);

        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_C); Utils.delay(150, 100);
    }

    private void checkForHexColorField() {
        robot.keyPress(KeyEvent.VK_TAB); Utils.delay(150, 100);
        robot.keyRelease(KeyEvent.VK_TAB); Utils.delay(150, 100);

        robot.keyPress(KeyEvent.VK_CONTROL); Utils.delay(150, 100);
        robot.keyPress(KeyEvent.VK_A); Utils.delay(150, 100);

        robot.keyRelease(KeyEvent.VK_A); Utils.delay(150, 100);
        robot.keyRelease(KeyEvent.VK_CONTROL); Utils.delay(150, 100);

        robot.keyPress(KeyEvent.VK_CONTROL); Utils.delay(150, 100);
        robot.keyPress(KeyEvent.VK_C); Utils.delay(150, 100);

        robot.keyRelease(KeyEvent.VK_C); Utils.delay(150, 100);
        robot.keyRelease(KeyEvent.VK_CONTROL); Utils.delay(150, 100);
    }

    private String getClipboardText() {
        try {
            return (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) { return ""; }
    }

    public void drawCircle(Point center, double radius, String hexColor) {
        window.ensureFocused(); Utils.delay(150, 100);
        int startX = (int) (center.x + radius);

        int startY = center.y; robot.mouseMove(startX, startY);
        Utils.delay(150, 100);

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        int steps = 600 + random.nextInt(300);

        for (int i = 0; i <= steps; i++) {
            double angle = 2 * Math.PI * i / steps;
            int x = (int) (center.x + (radius * Math.cos(angle)));

            int y = (int) (center.y + (radius * Math.sin(angle)));
            robot.mouseMove(x, y);

            if (random.nextInt(152) < 12) { robot.delay(1); }
        }

        Utils.delay(150, 100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        Utils.delay(150, 100); pickColor(hexColor);
        paintColor(center); Utils.delay(150, 100);
    }

    public void drawPolygon(String hexColor, Point ... points) {
        for (int i = 0; i < points.length; i++) {
            drawLine(points[i], points[(i + 1) % points.length]);
        }

        int x = 0, y = 0;
        for (Point point : points) { x += point.x; y += point.y; }

        Point center = new Point(x, y);
        pickColor(hexColor); Utils.delay(150, 100);
        paintColor(center); Utils.delay(150, 100);
    }
}
