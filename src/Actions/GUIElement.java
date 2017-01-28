package Actions;

import Components.Component;
import Components.Dot;
import Render.DrawHandler;
import Render.ResourceLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static Actions.Constants.Direction.*;

/**
 * Created by danielkim802 on 1/27/17.
 */
public abstract class GUIElement {
    private int x, y, x2, y2, xOffset, yOffset;
    private boolean offsetSet = false;
    private int imageIndex = 0;
    private List<BufferedImage> images;
    private Constants.Direction orientation = EAST;
    private boolean selected = false;

    public GUIElement() {
        setImages(ResourceLibrary.getImages(this.getClass()));
    }

    private void updateDots() {
        if (this instanceof Component) {
            ((Component) this).updateDots();
        }
    }

    // selection methods
    public void setSelected(boolean bool) {
        selected = bool;
    }
    public boolean isSelected() {
        return selected;
    }

    // position methods
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
    public void setXYOffset(int xpos, int ypos) {
        xOffset = xpos;
        yOffset = ypos;
        offsetSet = true;
        updateDots();
    }
    public void resetXY() {
        x = getX();
        y = getY();
        xOffset = 0;
        yOffset = 0;
        offsetSet = false;
    }
    public void setDirection(Constants.Direction dir) {
        orientation = dir;
        updateDots();
    }
    public void rotate(int amt) {
        orientation = getIntAsDirection((getDirectionAsInt() + amt));
        if (this instanceof Component) {
            ((Component) this).rotateDots(amt);
        }
        updateDots();
    }
    public int getX() {
        return x + xOffset;
    }
    public int getY() {
        return y - yOffset;
    }
    public int getX2() {
        return x2;
    }
    public int getY2() {
        return y2;
    }
    public int getXOffset() {
        return xOffset;
    }
    public int getYOffset() {
        return yOffset;
    }
    public boolean isOffsetSet() {
        return offsetSet;
    }
    public int getXWithoutOffset() {
        return x;
    }
    public int getYWithoutOffset() {
        return y;
    }
    public int getWidth() {
        return getImage().getWidth();
    }
    public int getHeight() {
        return getImage().getHeight();
    }
    public Constants.Direction getDirection() {
        return orientation;
    }

    // image methods
    public void setImages(List<BufferedImage> imgs) {
        if (imgs != null) {
            images = imgs;
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

    // helper methods
    public int getDirectionAsInt() {
        switch (orientation) {
            case NORTH:
                return 3;
            case EAST:
                return 0;
            case SOUTH:
                return 1;
            case WEST:
                return 2;
        }

        return 0;
    }
    public Constants.Direction getIntAsDirection(int i) {
        int j = i % 4 >= 0 ? i % 4 : (i % 4) + 4;
        switch (j) {
            case 3:
                return NORTH;
            case 0:
                return EAST;
            case 1:
                return SOUTH;
            case 2:
                return WEST;
        }

        return NORTH;
    }

    // draw methods
    public void drawImage(Graphics2D g) {
        DrawHandler.drawImage(g, getImage(), orientation, x + xOffset, y - yOffset);
    }
    public void drawSelected(Graphics2D g) {
        if (selected) {
            DrawHandler.drawRect(g, Color.red, getWidth(), getHeight(), getX(), getY());
        }
    }
    public abstract void draw(Graphics2D g);

    // action methods
    public abstract void click();
}
