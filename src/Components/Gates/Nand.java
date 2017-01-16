package Components.Gates;

import Components.Gate;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Nand extends Gate {
    {
        setOp( (a, b) -> a & b );
        setFinalOp( a -> ~a );
    }

    public Nand(int ins) {
        super(ins);
    }
    public Nand() {
        super(2);
    }
}
