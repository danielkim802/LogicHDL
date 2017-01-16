package Components.Gates;

import Components.Gate;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class Not extends Gate {
    {
        setOp( (a, b) -> a );
        setFinalOp( a -> ~a );
    }

    public Not() {
        super(1);
    }
}
