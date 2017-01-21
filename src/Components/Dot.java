package Components;

import Render.DrawHandler;
import Render.Drawable;
import Render.ResourceLibrary;

import java.awt.*;

/**
 * Created by danielkim802 on 1/18/17.
 */
public class Dot extends Drawable {
    private Component parent;
    private boolean input;
    private String key;

    public Dot(Component p, boolean type, String k) {
        parent = p;
        input = type;
        key = k;
        setImages(ResourceLibrary.getImages("dot"));
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

    public void drawSelected(Graphics2D g) {
        if (isSelected()) {
            DrawHandler.drawRect(g, Color.red, 5, 5, getX(), getY());
        }
    }

    public void draw(Graphics2D g) {
        setImageIndex(input ? 0 : 1);
        DrawHandler.drawImage(g, getImage(), getX(), getY());
        drawSelected(g);
    }

    public void updateDots() {}
    public void click() {}
}
