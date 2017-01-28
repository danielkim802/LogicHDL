package Actions;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Constants {
    public enum Component {
        // gates
        AND, NAND, NOR, NOT, OR, XNOR, XOR,

        // literals
        CONSTANT, OUTPUT, JOINT,

        // modules
        FULLADDER
    }

    public enum MouseMode {
        SELECT, PLACE, CLICK
    }

    public enum DraggingMode {
        DRAGGING_NONE, DRAGGING_CAMERA, DRAGGING_COMPONENT, DRAGGING_SELECT
    }

    public enum Direction {
        NORTH, EAST, SOUTH, WEST
    }
}
