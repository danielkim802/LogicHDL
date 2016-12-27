import ComponentError.WireError;

/**
 * Created by danielkim802 on 12/26/16.
 */
public class Wire {
    private int ID1, index1, ID2, index2;

    public Wire(int id1, int ind1, int id2, int ind2) {
        ID1 = id1;
        index1 = ind1;
        ID2 = id2;
        index2 = ind2;
    }

    // returns the component at the other end of the wire given the current
    // id component
    public int getOther(int current) {
        if (current == ID1) {
            return ID2;
        }
        return ID1;
    }

    // returns input or output index of the other component
    public int getOtherIndex(int current) {
        if (current == ID1) {
            return index2;
        }
        return index1;
    }
}
