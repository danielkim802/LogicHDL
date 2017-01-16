import Components.Circuit;
import Components.Component;
import Components.Gates.*;
import Components.Literals.*;
import Components.Modules.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class View extends JFrame implements MouseListener, KeyListener {
    Circuit circuit = new Circuit();
    Constants.Component mode = Constants.Component.AND;

    private boolean running = true;

    public View() {
        setTitle("LogicSim");
        setSize(900, 600);
        setLocationRelativeTo(null); /* Center the window */
        setUndecorated(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        createBufferStrategy(3);

        addMouseListener(this);
        addKeyListener(this);

        new Thread(this::loop).start();
    }

    private void loop() {
        while (running) {
            circuit.propagateLocal();
            draw();
        }
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch(code) {
            case 48: mode = Constants.Component.AND; break;
            case 49: mode = Constants.Component.NAND; break;
            case 50: mode = Constants.Component.NOR; break;
            case 51: mode = Constants.Component.NOT; break;
            case 52: mode = Constants.Component.OR; break;
            case 53: mode = Constants.Component.XNOR; break;
            case 54: mode = Constants.Component.XOR; break;
            case 55: mode = Constants.Component.CONSTANT; break;
            case 56: mode = Constants.Component.OUTPUT; break;
            case 57: mode = Constants.Component.FULLADDER;
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        Component a = new And();
        switch(mode) {
            case AND: a = new And(); break;
            case NAND: a = new Nand(); break;
            case NOR: a = new Nor(); break;
            case NOT: a = new Not(); break;
            case OR: a = new Or(); break;
            case XNOR: a = new Xnor(); break;
            case XOR: a = new Xor(); break;
            case CONSTANT: a = new Constant(0); break;
            case OUTPUT: a = new Output(); break;
            case FULLADDER: a = new Fulladder(); break;
        }
        System.out.println(a);
        circuit.addComponent(a);
        a.setX(e.getX());
        a.setY(e.getY());
    }

    public void draw() {
        Graphics2D g = (Graphics2D) getBufferStrategy().getDrawGraphics();
        circuit.draw(g);
        getBufferStrategy().show();
    }
}
