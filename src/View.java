import javax.swing.*;

/**
 * Created by danielkim802 on 12/26/16.
 */
public class View extends JFrame {

    JPanel panel = new JPanel();

    public static void main(String[] args) {
        new View();
    }

    public View() {
        super("LogicSim");

        setSize(300, 400);
        setResizable(true);

        panel.add(new JButton("hi"));
        panel.add(new AndGUI());
        add(panel);

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
