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
    Constants.Component component = Constants.Component.AND;
    Constants.Mode mode = Constants.Mode.PLACE;
    Component selected;

    {
        circuit.addInput("A", 0);
        circuit.addInput("B", 1);
        circuit.addOutput("C");
        And and1 = new And();
        circuit.addComponent(and1);
        circuit.getInput("A").connect("0", and1);
        circuit.getInput("B").connect("1", and1);
        and1.connect("input", circuit.getOutput("C"));
        and1.setX(200);
        and1.setY(200);
    }

    private boolean running = true;

    public View() {
        setTitle("LogicSim");
        setSize(900, 600);
        setLocationRelativeTo(null);
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
            case 48: component = Constants.Component.AND; break;
            case 49: component = Constants.Component.NAND; break;
            case 50: component = Constants.Component.NOR; break;
            case 51: component = Constants.Component.NOT; break;
            case 52: component = Constants.Component.OR; break;
            case 53: component = Constants.Component.XNOR; break;
            case 54: component = Constants.Component.XOR; break;
            case 55: component = Constants.Component.CONSTANT; break;
            case 56: component = Constants.Component.OUTPUT; break;
            case 57: mode = (mode == Constants.Mode.SELECT) ? Constants.Mode.PLACE : Constants.Mode.SELECT;
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        if (mode == Constants.Mode.PLACE) {
            Component a = new And();
            switch (component) {
                case AND:
                    a = new And();
                    break;
                case NAND:
                    a = new Nand();
                    break;
                case NOR:
                    a = new Nor();
                    break;
                case NOT:
                    a = new Not();
                    break;
                case OR:
                    a = new Or();
                    break;
                case XNOR:
                    a = new Xnor();
                    break;
                case XOR:
                    a = new Xor();
                    break;
                case CONSTANT:
                    a = new Constant(0);
                    break;
                case OUTPUT:
                    a = new Output();
                    break;
                case FULLADDER:
                    a = new Fulladder();
                    break;
            }
            System.out.println(a);
            circuit.addComponent(a);
            a.setX(e.getX());
            a.setY(e.getY());
        }
        else if (mode == Constants.Mode.SELECT) {
            for (Component component : circuit.getComponents()) {
                if (Math.abs(component.getX()-e.getX()) <= 15 && Math.abs(component.getY()-e.getY()) <= 15) {
                    if (selected == null) {
                        selected = component;
                        System.out.println("selected: " + component);
                        component.setSelected(true);
                        break;
                    }
                    else {
                        System.out.println((selected + " -> " + component));
                        selected.connect("output", "0", component);
                        break;
                    }
                }
            }
        }
    }

    public void draw() {
        Graphics2D g = (Graphics2D) getBufferStrategy().getDrawGraphics();
        circuit.draw(g);
        getBufferStrategy().show();
    }
}
