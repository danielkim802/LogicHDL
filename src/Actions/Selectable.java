package Actions;

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

    public abstract void click();
    public abstract void drag(int param1, int param2);
}
