package Actions;

import Components.Circuit;
import Render.Camera;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielkim802 on 1/18/17.
 */
public class ActionHandler {
    private static boolean inRange(int x, int r0, int r1) {
        return r0 <= x && x <= r1;
    }

    public static boolean inBox(int searchx, int searchy, int width, int height, int x, int y) {
        int h = height / 2;
        int w = width / 2;
        return inRange(searchx, x - w, x + w) && inRange(searchy, y - h, y + h);
    }

    public static GUIElement getSelectedWithPosition(Camera c, MouseEvent e, Circuit circuit) {
        int mousex = c.getXMouse(e);
        int mousey = c.getYMouse(e);

        GUIElement selected = null;

        // check inputs
        for (GUIElement element : circuit.getComponents()) {
            selected = inBox(mousex, mousey, element.getWidth(), element.getHeight(), element.getX(), element.getY()) ? element : selected;
        }
        for (GUIElement element : circuit.getAllDots()) {
            selected = inBox(mousex, mousey, element.getWidth(), element.getHeight(), element.getX(), element.getY()) ? element : selected;
        }

        return selected;
    }

    public static List<GUIElement> getSelectedWithBox(Camera c, Circuit circuit, int x1, int y1, int x2, int y2) {
        List<GUIElement> selected = new ArrayList<>();

        // check inputs
        for (GUIElement s : circuit.getComponents()) {
            if (inBox(s.getX(), s.getY(), Math.abs(x1 - x2), Math.abs(y1 - y2), (x1 + x2) / 2, (y1 + y2) / 2))
                selected.add(s);
        }

        return selected;
    }

    public static Point getAverageCoordinates(List<GUIElement> elements) {
        int avgx = 0;
        int avgy = 0;

        for (GUIElement element : elements) {
            avgx += element.getX();
            avgy += element.getY();
        }

        return new Point(avgx / elements.size(), avgy / elements.size());
    }
}
