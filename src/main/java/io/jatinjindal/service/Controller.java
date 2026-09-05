package io.jatinjindal.service;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public final class Controller {

    private final Window window;
    private final Robot robot;
    private final Random random;

    public Controller() throws AWTException {
        this.window = new Window();
        this.robot = new Robot();
        this.random = new Random();
    }

    public void startWindow() {
        window.init();
        robot.delay(2000 + random.nextInt(500));
    }

    public void stopWindow() {
        robot.delay(2000 + random.nextInt(500));
        window.stop();
    }

    public void drawLine(Point start, Point end) {
        window.ensureFocused();
        robot.delay(150 + random.nextInt(100));

        robot.mouseMove(start.x, start.y);
        robot.delay(150 + random.nextInt(100));

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        int steps = 6 + random.nextInt(4);

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            int x = (int) (start.x + (end.x - start.x) * t);

            int y = (int) (start.y + (end.y - start.y) * t);
            robot.mouseMove(x, y); robot.delay(10);
        }

        robot.delay(100 + random.nextInt(75));
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void drawRectangle(Point start, Point end) {
        Point topRight = new Point(end.x, start.y);
        Point bottomLeft = new Point(start.x, end.y);

        drawLine(start, topRight); drawLine(topRight, end);
        drawLine(end, bottomLeft); drawLine(bottomLeft, start);
    }

    public void drawCircle(Point center, double radius) {
        window.ensureFocused();
        robot.delay(150 + random.nextInt(100));

        int startX = (int) (center.x + radius);
        int startY = center.y;

        robot.mouseMove(startX, startY);
        robot.delay(150 + random.nextInt(100));

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        int steps = 600 + random.nextInt(300);

        for (int i = 0; i <= steps; i++) {
            double angle = 2 * Math.PI * i / steps;
            int x =  (int) (center.x + (radius * Math.cos(angle)));

            int y = (int) (center.y + (radius * Math.sin(angle)));
            robot.mouseMove(x, y);

            if (random.nextInt(152) < 12) {
                robot.delay(1);
            }
        }

        robot.delay(100 + random.nextInt(75));
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void drawPolygon(Point ... points) {
        for (int i = 0; i < points.length; i++) {
            drawLine(points[i], points[(i + 1) % points.length]);
        }
    }
}
