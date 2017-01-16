package Components;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Wire {
    private long value;
    private boolean valid;
    private Component connect;

    public Wire() {}
    public Wire(Component c) {
        // component wire is pointing at
        connect = c;
    }

    public Component getConnect() {
        return connect;
    }
    public void set(long val) {
        value = val;
        valid = true;
        connect.propagate();
    }
    public void setValid(boolean val) {
        valid = val;
    }
    public boolean isValid() {
        return valid;
    }
    public long value() {
        return value;
    }
}
