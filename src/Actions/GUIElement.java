package Actions;

import Components.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static Actions.Constants.Direction.*;
import static Actions.Constants.Direction.NORTH;

/**
 * Created by danielkim802 on 1/27/17.
 */
public abstract class GUIElement {
    private int x, y, x2, y2, xRef, yRef;
    private int imageIndex = 0;
    private List<BufferedImage> images;
    private int width;
    private int height;
    private Constants.Direction orientation = EAST;
    private boolean selected = false;

    public void setSelected(boolean bool) {
        selected = bool;
    }
    public boolean isSelected() {
        return selected;
    }

    // position methods
    public void setX(int pos) {
        x = pos;
        xRef = x;
        updateDots();
    }
    public void setY(int pos) {
        y = pos;
        yRef = y;
        updateDots();
    }
    public void setXY(int xpos, int ypos) {
        x = xpos;
        y = ypos;
        xRef = x;
        yRef = y;
        updateDots();
    }
    public void resetRelative() {
        xRef = x;
        yRef = y;
    }
    public void setXYRelative(int xpos, int ypos) {
        x = xRef + xpos;
        y = yRef + ypos;
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
    public Constants.Direction getDirection() {
        return orientation;
    }
    private void updateDots() {
        if (this instanceof Component) {
            ((Component) this).updateDots();
        }
    }

    // image methods
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

    // helper methods
    public int dirToInt() {
        switch (orientation) {
            case NORTH:
                return 0;
            case EAST:
                return 1;
            case SOUTH:
                return 2;
            case WEST:
                return 3;
        }

        return 0;
    }
    public Constants.Direction intToDir(int i) {
        int j = i % 4 >= 0 ? i % 4 : (i % 4) + 4;
        System.out.println(j);
        switch (j) {
            case 0:
                return NORTH;
            case 1:
                return EAST;
            case 2:
                return SOUTH;
            case 3:
                return WEST;
        }

        return NORTH;
    }
    public void rotate(int amt) {
        orientation = intToDir((dirToInt() + amt));
        if (this instanceof Component) {
            ((Component) this).rotateDots(amt);
        }
        updateDots();
    }

    // abstract methods
    public abstract void draw(Graphics2D g);
}
