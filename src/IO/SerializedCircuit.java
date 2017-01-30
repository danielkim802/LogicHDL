package IO;

import Components.Circuit;
import Components.Component;
import Components.Literals.Constant;
import Components.Literals.Output;
import Components.Wire;

import java.io.Serializable;
import java.util.*;

/**
 * Created by danielkim802 on 1/29/17.
 */

// takes all info of a circuit and converts into a serializable object that
// can be used to reconstruct the circuit again
public class SerializedCircuit implements Serializable, SerializableComponent {

    private List<Connection> connections;
    private Map<Integer, SerializedComponent> unconvertedComponents;
    private Map<Integer, Component> convertedComponents;

    // class connection representing a wire
    class Connection implements Serializable {
        int hashcode1;
        String outputKey;
        int hashcode2;
        String inputKey;

        public Connection(int h1, String ok, int h2, String ik) {
            hashcode1 = h1;
            outputKey = ok;
            hashcode2 = h2;
            inputKey = ik;
        }
    }

    // adds connections from output of one component to inputs of another
    private void addConnections(Component component) {
        for (String key : component.getOutputs().keySet()) {
            for (Wire wire : component.getOutputs().get(key)) {
                if (wire.isAssigned()) {
                    connections.add(new Connection(component.hashCode(), key, wire.getConnect().hashCode(), wire.getConnectDot().getKey()));
                }
            }
        }
    }

    public SerializedCircuit(Circuit circuit) {
        connections = new ArrayList<>();
        unconvertedComponents = new HashMap<>();
        convertedComponents = new HashMap<>();

        serialize(circuit);
    }

    // stores relevant information of the circuit
    public void serialize(Component comp) {
        Circuit circuit = (Circuit) comp;

        // convert all components to serialized components
        for (Component component : circuit.getComponents()) {
            SerializedComponent serialized = new SerializedComponent(component);
            unconvertedComponents.put(component.hashCode(), serialized);
            addConnections(component);
        }
    }

    // recreates circuit based on stored information
    public Circuit make() {
        Circuit circuit = new Circuit();

        // convert all unconverted components
        for (int key : unconvertedComponents.keySet()) {
            convertedComponents.put(key, unconvertedComponents.get(key).make());
        }

        // form connections between all converted components
        for (Connection connection : connections) {
            Component comp1 = convertedComponents.get(connection.hashcode1);
            Component comp2 = convertedComponents.get(connection.hashcode2);
            comp1.connect(connection.outputKey, connection.inputKey, comp2);
        }

        // add components to circuit
        circuit.setComponents(new ArrayList<>(convertedComponents.values()));

        return circuit;
    }
}
