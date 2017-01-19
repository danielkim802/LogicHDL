package Components;

import Render.DrawHandler;
import Render.Drawable;
import Render.ResourceLibrary;

import java.awt.*;

/**
 * Created by danielkim802 on 1/18/17.
 */
public class Dot extends Drawable {
    public Dot() {
        setImages(ResourceLibrary.getImages("dot"));
    }
    public Dot(int xpos, int ypos) {
        setXY(xpos, ypos);
        setImages(ResourceLibrary.getImages("dot"));
    }

    public void draw(Graphics2D g) {
        DrawHandler.drawImage(g, getImage(), getX(), getY());
    }

    public void updateDots() {}
}
