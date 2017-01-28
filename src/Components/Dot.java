package Components;

import Actions.GUIElement;
import Render.Camera;
import Render.ResourceLibrary;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by danielkim802 on 1/18/17.
 */
public class Dot extends GUIElement {
    private Component parent;
    private boolean input;
    private String key;

    public Dot(Component p, boolean type, String k) {
        super();

        parent = p;
        input = type;
        key = k;
        setImages(ResourceLibrary.getImages("dot"));
        setXY(parent.getX(), parent.getY());
    }

    public Component getParent() {
        return parent;
    }

    public void connect(Dot dot) {
        if (!input && dot.input) {
            parent.connect(key, dot.key, dot.parent);
        }
        else if (input && !dot.input) {
            dot.connect(this);
        }
    }
    public void update() {
        setXY(parent.getX(), parent.getY());
    }

    public void press(MouseEvent e, Camera c) {}
    public void click() {
        setSelected(true);
    }
    public void drag(MouseEvent e, Camera c) {}
    public void release(MouseEvent e, Camera c) {}
    public void draw(Graphics2D g) {
        setImageIndex(input ? 0 : 1);
        drawImage(g);
        drawSelected(g);
    }
}
