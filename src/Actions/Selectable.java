package Actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by danielkim802 on 1/18/17.
 */
public abstract class Selectable {
    private boolean selected = false;

    public void setSelected(boolean bool) {
        selected = bool;
    }
    public boolean isSelected() {
        return selected;
    }

    public void click() {
        actionClicked();
    }

    public abstract void actionClicked();
}
