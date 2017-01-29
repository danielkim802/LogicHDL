package IO;

import Components.Circuit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by danielkim802 on 1/29/17.
 */
public class IOHandler {
    public static void saveCircuit(Circuit circuit) {
        try {
            FileOutputStream fout = new FileOutputStream("src/SaveFiles/" + circuit.getName() + ".circuit");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(circuit);
            fout.close();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Circuit loadCircuit(String name) {
        try {
            FileInputStream fin = new FileInputStream("src/SaveFiles" + name + ".circuit");
            ObjectInputStream ois = new ObjectInputStream(fin);
            Circuit circuit = (Circuit) ois.readObject();
            fin.close();
            ois.close();
            return circuit;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
