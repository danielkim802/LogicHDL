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

    public Dot(Component p) {
        parent = p;
        setImages(ResourceLibrary.getImages("dot"));
    }
    public Dot(Component p, int xpos, int ypos) {
        parent = p;
        setXY(xpos, ypos);
        setImages(ResourceLibrary.getImages("dot"));
    }

    public void draw(Graphics2D g) {
        setImageIndex(isSelected() ? 1 : 0);
        DrawHandler.drawImage(g, getImage(), getX(), getY());
    }

    public void updateDots() {}
    public void click() {}
}
