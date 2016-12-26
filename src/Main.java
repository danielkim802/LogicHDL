public class Main {

    public static void main(String[] args) {
        Circuit circuit = new Circuit();
        int and1 = circuit.addGate("AND", 3);
        int or1 = circuit.addGate("OR", 2);

        System.out.println(circuit.getValue(and1));
    }
}
