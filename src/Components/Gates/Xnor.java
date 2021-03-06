package Components.Gates;

import Components.Gate;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Xnor extends Gate {
    {
        setOp( (a, b) -> a ^ b );
        setFinalOp( a -> ~a );
    }

    public Xnor(int ins) {
        super(ins);
    }
    public Xnor() {
        super(2);
    }
}
