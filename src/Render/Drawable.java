package Render;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Drawable {
    private int x, y, x2, y2;
    private int imageIndex = 0;
    private List<BufferedImage> images;
    private List<BufferedImage> accessories;
    private int orientation = 0;

    private boolean selected = false;

    public void setX(int pos) {
        x = pos;
    }
    public void setY(int pos) {
        y = pos;
    }
    public void setXY(int xpos, int ypos) {
        x = xpos;
        y = ypos;
    }

    public void setX2(int pos) {
        x2 = pos;
    }
    public void setY2(int pos) {
        y2 = pos;
    }
    public void setXY2(int xpos, int ypos) {
        x2 = xpos;
        y2 = ypos;
    }

    public void setImages(List<BufferedImage> imgs) {
        images = imgs;
    }
    public void setImageIndex(int index) {
        imageIndex = index;
    }
    public BufferedImage getImage() {
        if (images.size() > 0) {
            return images.get(imageIndex);
        }
        else {
            return null;
        }
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getX2() {
        return x2;
    }
    public int getY2() {
        return y2;
    }

    public void setSelected(boolean bool) {
        selected = bool;
    }

    public void placeDot(Graphics2D g, int state, int xpos, int ypos) {
        BufferedImage dot = ResourceLibrary.getImages("dot").get(state);
        g.drawImage(dot, null, xpos - dot.getWidth() / 2, ypos - dot.getHeight() / 2);
    }

    public abstract void draw(Graphics2D g);
}
