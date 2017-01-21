package Actions;

import Render.DrawHandler;
import Render.Drawable;
import Render.ResourceLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by danielkim802 on 1/21/17.
 */
public class MyCursor {
    private int imageIndex;
    private List<BufferedImage> images;
    private Cursor cursor;
    private int x;
    private int y;

    public MyCursor() {
        images = ResourceLibrary.getImages("cursor");
        imageIndex = 0;
        x = getImage().getWidth() / 2;
        y = getImage().getHeight() / 2;
        cursor = Toolkit.getDefaultToolkit().createCustomCursor(images.get(imageIndex), new Point(x,y), "cursor");
    }

    private BufferedImage getImage() {
        if (!images.isEmpty()) {
            return images.get(imageIndex);
        }
        return null;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public int getIndex() {
        return imageIndex;
    }
    public Cursor setIndex(int i) {
        imageIndex = i;
        cursor = Toolkit.getDefaultToolkit().createCustomCursor(images.get(imageIndex), new Point(x,y), "cursor");
        return cursor;
    }
}
