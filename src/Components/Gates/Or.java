package Components.Gates;

import Components.Gate;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Or extends Gate {
    {
        setOp( (a, b) -> a | b );
    }

    public Or(int ins) {
        super(ins);
    }
}
