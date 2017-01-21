package Render;

import Actions.Selectable;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Drawable extends Selectable {
    private int x, y, x2, y2;
    private int imageIndex = 0;
    private List<BufferedImage> images;
    private int width;
    private int height;
    private int orientation = 0;

    public void setX(int pos) {
        x = pos;
        updateDots();
    }
    public void setY(int pos) {
        y = pos;
        updateDots();
    }
    public void setXY(int xpos, int ypos) {
        x = xpos;
        y = ypos;
        updateDots();
    }

    public void setX2(int pos) {
        x2 = pos;
        updateDots();
    }
    public void setY2(int pos) {
        y2 = pos;
        updateDots();
    }
    public void setXY2(int xpos, int ypos) {
        x2 = xpos;
        y2 = ypos;
        updateDots();
    }

    public void setImages(List<BufferedImage> imgs) {
        if (imgs != null) {
            images = imgs;
            setDimensions(getImage().getWidth(), getImage().getHeight());
        }
    }
    public void setImageIndex(int index) {
        imageIndex = index;
    }
    public BufferedImage getImage() {
        if (images != null && images.size() > 0) {
            return images.get(imageIndex);
        }
        else {
            return null;
        }
    }

    public void setDimensions(int w, int h) {
        width = w;
        height = h;
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
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public abstract void draw(Graphics2D g);

    // used to set position of dots relative to component
    public abstract void updateDots();

    public void drag(int x, int y) {}
}
