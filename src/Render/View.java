package Render;

import Actions.Constants;
import Actions.MyCursor;
import Actions.Selectable;
import Actions.ActionHandler;
import Components.Circuit;
import Components.Component;
import Components.Dot;
import Components.Gates.*;
import Components.Literals.*;
import Components.Modules.*;
import static Actions.Constants.Component.*;
import static Actions.Constants.Mode.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.awt.event.KeyEvent.*;
import static java.lang.Math.max;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class View extends JFrame implements MouseListener, KeyListener, MouseMotionListener, MouseWheelListener {
    Circuit circuit = new Circuit();
    Camera camera = new Camera(this);
    MyCursor cursor = new MyCursor();
    Constants.Component component = AND;
    Constants.Mode mode = Constants.Mode.PLACE;
    Selectable selected;
    Drawable moving;

    int mouseX = 0;
    int mouseY = 0;
    int movingX = 0;
    int movingY = 0;
    boolean movingView = false;
    boolean movingComp = false;

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
        circuit.getInput("A").connect("0", and2);
        circuit.getInput("B").connect("1", xor1);
        circuit.getInput("B").connect("1", and2);
        circuit.getInput("C").connect("1", xor2);
        circuit.getInput("C").connect("1", and1);
        xor1.connect("0", xor2);
        xor1.connect("0", and1);
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
        addMouseMotionListener(this);
        addKeyListener(this);
        addMouseWheelListener(this);
        setCursor(cursor.getCursor());
        camera.scale(2.0, 2.0);

        new Thread(this::loop).start();
    }

    private void loop() {
        long startTime;
        while (running) {
            startTime = System.currentTimeMillis();
            update();
            draw();
            try {
                Thread.sleep(max(0, 1000 / 60 - System.currentTimeMillis() + startTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        int speed = 5;
        double zoomspeed = 0.1;
        System.out.println("pressed");
        System.out.println(e.getKeyCode());
        switch (e.getKeyCode()) {
            case VK_RIGHT:
                if (selected != null && selected instanceof Component) {
                    ((Drawable) selected).rotate(1);
                }
                break;
            case VK_LEFT:
                if (selected != null && selected instanceof Component) {
                    ((Drawable) selected).rotate(-1);
                }
                break;
            case VK_UP: camera.translate(0, -speed); break;
            case VK_DOWN: camera.translate(0, speed); break;
            case VK_OPEN_BRACKET: camera.zoom(zoomspeed, zoomspeed); break;
            case VK_CLOSE_BRACKET: camera.zoom(-zoomspeed, -zoomspeed); break;
            case VK_0: component = AND; break;
            case VK_1: component = NAND; break;
            case VK_2: component = NOR; break;
            case VK_3: component = NOT; break;
            case VK_4: component = OR; break;
            case VK_5: component = XNOR; break;
            case VK_6: component = XOR; break;
            case VK_7: component = CONSTANT; break;
            case VK_8: component = OUTPUT; break;
            case VK_9:
                setCursor(cursor.setIndex((cursor.getIndex() + 1) % 3));
                mode = (mode == PLACE) ? SELECT : mode == SELECT ? CLICK : PLACE;
                break;
            case VK_MINUS: component = JOINT; break;
            case VK_C: circuit.clear(); break;
            case VK_BACK_SPACE:
                if (selected != null && selected instanceof Component) {
                    circuit.remove((Component) selected);
                    selected = null;
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {
        moving = null;
        movingView = false;
        movingComp = false;
    }
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (!e.isShiftDown()) {
            double amt = e.getPreciseWheelRotation() / 10.0;
            camera.zoom(amt, amt);
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

    private void setInitialPositions(MouseEvent e, boolean view) {
        if (view) {
            movingView = true;
            movingComp = false;
            movingX = camera.getX();
            movingY = camera.getY();
        } else {
            movingComp = true;
            movingView = false;
            movingX = moving.getX();
            movingY = moving.getY();
        }
        mouseX = e.getX();
        mouseY = e.getY();
    }
    public void mouseDragged(MouseEvent e) {
//        System.out.println(camera.getXMouse(e));
//        System.out.println(camera.getYMouse(e));
        if (moving == null && movingView && e.isShiftDown()) {
            camera.setXY(movingX + camera.convertDistX(mouseX - e.getX()), movingY + camera.convertDistY(mouseY - e.getY()));
        } else if (moving != null && movingComp) {
            moving.setXY(movingX + camera.convertDistX(e.getX() - mouseX), movingY + camera.convertDistY(e.getY() - mouseY));
        } else if (!movingComp && ! movingView) {
            moving = (Drawable) ActionHandler.getSelectedWithPosition(camera, e, circuit);

            if (moving == null)
                setInitialPositions(e, true);
            else if (moving != null && moving instanceof Component)
                setInitialPositions(e, false);
        }
    }
    public void mouseClicked(MouseEvent e) {
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
                    case JOINT: a = new Joint(); break;
                }
                a.setXY(camera.getXMouse(e), camera.getYMouse(e));
                circuit.addComponent(a);
                System.out.println(a);
                break;
            case SELECT:
                Selectable newselected = ActionHandler.getSelectedWithPosition(camera, e, circuit);
//                System.out.println("selected: " + selected);
//                System.out.println("newselected: " + newselected);
                if (selected != null) {
                    if (newselected != null) {
                        // toggle
                        if (selected == newselected) {
                            newselected.setSelected(!newselected.isSelected());
                        }
                        // connect dots
                        else if (selected instanceof Dot && newselected instanceof Dot) {
                            ((Dot) selected).connect((Dot) newselected);
                            selected.setSelected(false);
                            newselected.setSelected(false);
                            newselected = null;
                        }
                        // select something else
                        else {
                            selected.setSelected(false);
                            newselected.setSelected(true);
                        }
                    }
                    else {
                        selected.setSelected(false);
                    }
                }
                else if (newselected != null) {
                    newselected.setSelected(true);
                }
                selected = newselected;
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

        camera.clear(g);
        camera.draw(g);
        circuit.draw(g);

        g.dispose();
        getBufferStrategy().show();
    }
}
