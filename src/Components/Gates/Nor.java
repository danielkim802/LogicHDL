package Components.Gates;

import Components.Gate;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Nor extends Gate {
    {
        setOp( (a, b) -> a | b );
        setFinalOp( a -> ~a );
    }

    public Nor(int ins) {
        super(ins);
    }
    public Nor() {
        super(2);
    }
}
