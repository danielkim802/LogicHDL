package Actions;

import Components.Circuit;
import Components.Component;
import Render.Camera;
import Render.Drawable;

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

    public static Selectable getSelectedWithPosition(Camera c, MouseEvent e, Circuit circuit) {
        int mousex = c.getXMouse(e);
        int mousey = c.getYMouse(e);

        Selectable selected = null;

        // check inputs
        for (Drawable s : circuit.getAllComponents()) {
            selected = inBox(mousex, mousey, s.getWidth(), s.getHeight(), s.getX(), s.getY()) ? s : selected;
        }
        for (Drawable s : circuit.getAllDots()) {
            selected = inBox(mousex, mousey, s.getWidth(), s.getHeight(), s.getX(), s.getY()) ? s : selected;
        }

        return selected;
    }

    public static List<Selectable> getSelectedWithBox(Camera c, Circuit circuit, int x1, int y1, int x2, int y2) {
        List<Selectable> selected = new ArrayList<>();

        // check inputs
        for (Drawable s : circuit.getAllComponents()) {
            if (inBox(s.getX(), s.getY(), Math.abs(x1 - x2), Math.abs(y1 - y2), (x1 + x2) / 2, (y1 + y2) / 2))
                selected.add(s);
        }

        return selected;
    }
}
