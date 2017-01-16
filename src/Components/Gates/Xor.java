package Components.Gates;

import Components.Gate;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Xor extends Gate {
    {
        setOp( (a, b) -> a ^ b );
    }

    public Xor(int ins) {
        super(ins);
    }
}
