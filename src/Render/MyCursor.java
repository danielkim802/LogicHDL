package Render;

import Actions.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by danielkim802 on 1/21/17.
 */
public class MyCursor {
    private View view;
    private int imageIndex;
    private List<BufferedImage> images;
    private int x, y, xSave, ySave, xHot, yHot;

    public MyCursor(View v) {
        view = v;
        images = ResourceLibrary.getImages("cursor");
        imageIndex = 0;
        xHot = getImage().getWidth() / 2;
        yHot = getImage().getHeight() / 2;
        xSave = 0;
        ySave = 0;
        view.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(getImage(), new Point(xHot,yHot), "cursor"));
    }

    public void setXY(int xpos, int ypos) {
        x = xpos;
        y = ypos;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    private BufferedImage getImage() {
        if (!images.isEmpty()) {
            return images.get(imageIndex);
        }
        return null;
    }
    public int getIndex() {
        return imageIndex;
    }
    private void setIndex(int i) {
        imageIndex = i;
        view.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(getImage(), new Point(xHot,yHot), "cursor"));
    }
    public void setMode(Constants.MouseMode mode) {
        switch (mode) {
            case PLACE:
                setIndex(0);
                break;
            case SELECT:
                setIndex(1);
                break;
            case CLICK:
                setIndex(2);
                break;
        }
    }

    public void savePoint(int x, int y) {
        xSave = x;
        ySave = y;
    }
    public void resetSave() {
        xSave = 0;
        ySave = 0;
    }
    public int getXSave() {
        return xSave;
    }
    public int getYSave() {
        return ySave;
    }
}
