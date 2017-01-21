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

    public enum Mode {
        SELECT, PLACE, CLICK
    }
}
