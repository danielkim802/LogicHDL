import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by danielkim802 on 12/26/16.
 */
public class AndGUI extends JButton implements ActionListener {
    ImageIcon gatehi, gatelow;
    boolean state;

    public AndGUI() {
        gatehi = new ImageIcon(this.getClass().getResource("Resources/spr_gate_and_0.png"));
        gatelow = new ImageIcon(this.getClass().getResource("Resources/spr_gate_and_1.png"));
        state = false;
        setIcon(gatelow);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        state = !state;
        if (state) {
            setIcon(gatehi);
        }
        else {
            setIcon(gatelow);
        }
    }
}
