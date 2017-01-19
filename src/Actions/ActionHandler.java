package Actions;

import Components.Circuit;
import Components.Component;
import Render.Camera;
import Render.Drawable;

import java.awt.event.MouseEvent;

/**
 * Created by danielkim802 on 1/18/17.
 */
public class ActionHandler {
    public static boolean inBox(int searchx, int searchy, int width, int height, int x, int y) {
        int h = height / 2;
        int w = width / 2;
        return x - w <= searchx && searchx <= x + w && y - h <= searchy && searchy <= y + h;
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
}
