import Actions.ActionHandler;
import Actions.Selectable;
import Components.Circuit;
import Components.Component;
import Components.Gates.*;
import Components.Literals.*;
import Components.Modules.*;
import Render.Camera;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class View extends JFrame implements MouseListener, KeyListener {
    Circuit circuit = new Circuit();
    Camera camera = new Camera();
    Constants.Component component = Constants.Component.AND;
    Constants.Mode mode = Constants.Mode.PLACE;
    Selectable selected;

    {
//        circuit.addInput("A", 1);
//        circuit.getInput("A").setXY(150, 280);
//        circuit.addInput("B", 1);
//        circuit.getInput("B").setXY(150, 320);
//        circuit.addOutput("C");
//        circuit.getOutput("C").setXY(400, 300);
//        Or and1 = new Or();
//        circuit.addComponent(and1);
//        circuit.getInput("A").connect("0", and1);
//        circuit.getInput("B").connect("1", and1);
//        and1.connect("input", circuit.getOutput("C"));
//        and1.setXY(300, 300);

        circuit.addInput("A", 1);
        circuit.addInput("B", 1);
        circuit.addInput("C", 1);
        circuit.addOutput("S");
        circuit.addOutput("Cout");

        Xor xor1 = new Xor();
        Xor xor2 = new Xor();
        And and1 = new And();
        And and2 = new And();
        Or or1 = new Or();

        circuit.getInput("A").connect("0", xor1);
        circuit.getInput("A").connect("1", and2);
        circuit.getInput("B").connect("1", xor1);
        circuit.getInput("B").connect("0", and2);
        circuit.getInput("C").connect("1", xor2);
        circuit.getInput("C").connect("0", and1);
        xor1.connect("0", xor2);
        xor1.connect("1", and1);
        and1.connect("0", or1);
        and2.connect("1", or1);
        xor2.connect("input", circuit.getOutput("S"));
        or1.connect("input", circuit.getOutput("Cout"));

        ArrayList<Component> comps = new ArrayList<>();
        comps.addAll(Arrays.asList(xor1, xor2, and1, and2, or1));
        circuit.setComponents(comps);

        circuit.getInput("A").setXY(50, 50);
        circuit.getInput("B").setXY(50, 100);
        circuit.getInput("C").setXY(50, 150);
        xor1.setXY(100, 70);
        xor2.setXY(150, 70);
        and1.setXY(200, 130);
        and2.setXY(200, 180);
        or1.setXY(250, 155);
        circuit.getOutput("S").setXY(300, 70);
        circuit.getOutput("Cout").setXY(300, 155);
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
            update();
            draw();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            case 57: mode = (mode == Constants.Mode.PLACE) ? Constants.Mode.CLICK : Constants.Mode.PLACE;
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX());
        System.out.println(e.getY());
        switch (mode) {
            case PLACE:
                Component a = new And();
                switch (component) {
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
                a.setXY(camera.getXMouse(e), camera.getYMouse(e));
                circuit.addComponent(a);
                break;
            case SELECT:
                Selectable newselected = ActionHandler.getSelectedWithPosition(camera, e, circuit);
                if (newselected != null) {
                    if (selected != null) {
                        if (selected == newselected) {
                            newselected.setSelected(!newselected.isSelected());
                        }
                        else {
                            selected.setSelected(false);
                            newselected.setSelected(true);
                        }
                    }

                    selected = newselected;
                }
                break;
            case CLICK:
                Selectable clicked = ActionHandler.getSelectedWithPosition(camera, e, circuit);
                if (clicked != null) {
                    clicked.click();
                }
                break;
        }
    }

    public void update() {
        circuit.propagateLocal();
    }

    public void draw() {
        Graphics2D g = (Graphics2D) getBufferStrategy().getDrawGraphics();
        camera.scale(g, 2.0, 2.0);
//        camera.translate(g, -30, -100);
        circuit.draw(g);

        g.dispose();
        getBufferStrategy().show();
    }
}
