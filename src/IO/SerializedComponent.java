package IO;

import Actions.Constants;
import Components.Component;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by danielkim802 on 1/29/17.
 */
public class SerializedComponent implements Serializable {
    private int hashcode;
    private int x, y;
    private Constants.Direction orientation;
    private Class clazz;

    public int getCode() {
        return hashcode;
    }

    public SerializedComponent(Component component) {
        x = component.getX();
        y = component.getY();
        orientation = component.getDirection();
        hashcode = component.hashCode();
        clazz = component.getClass();
    }

    public Component make() {
        try {
            Component component = (Component) clazz.asSubclass(clazz).getConstructor().newInstance();
            component.setXY(x, y);
            component.setDirection(orientation);
            return component;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
