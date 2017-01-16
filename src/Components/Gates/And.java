package Components.Gates;

import Components.Gate;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class And extends Gate {
    {
        setOp( (a, b) -> a & b );
    }

    public And(int ins) {
        super(ins);
    }
    public And() {
        super(2);
    }
}
