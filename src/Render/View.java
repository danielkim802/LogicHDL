package Render;

import Actions.Constants;
import Actions.GUIElement;
import Actions.ActionHandler;
import Components.Circuit;
import Components.Component;
import Components.Dot;
import Components.Gates.*;
import Components.Literals.*;
import Components.Modules.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static Actions.Constants.Component.*;
import static Actions.Constants.Direction.*;
import static Actions.Constants.DraggingMode.*;
import static Actions.Constants.MouseMode.*;

import static java.awt.event.KeyEvent.*;
import static java.lang.Math.max;

/**
 * Created by danielkim802 on 1/16/17.
 */
public class View extends JFrame implements MouseListener, KeyListener, MouseMotionListener, MouseWheelListener {
    // view components
    private Circuit circuit = new Circuit();
    private Camera camera = new Camera(this);
    private MyCursor cursor = new MyCursor(this);

    // settings and modes
    private Constants.Component placingComponent = AND;
    private Constants.MouseMode mouseMode = SELECT;
    private Constants.DraggingMode draggingMode = DRAGGING_NONE;
    private double zoomSpeed = 0.1;
    private boolean running = true;

    // temporary storage
    private List<GUIElement> selectedComponents = new ArrayList<>();
    private List<GUIElement> selectedDots = new ArrayList<>();
    private List<GUIElement> selectedElements = new ArrayList<>();

    private void initCircuit() {
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

    public View() {
        initCircuit();

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

        cursor.setMode(mouseMode);
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

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    private Constants.Component mapKeyToComponent(KeyEvent e) {
        switch (e.getKeyCode()) {
            case VK_1:
                return AND;
            case VK_2:
                return OR;
            case VK_3:
                return NOT;
            case VK_4:
                return NAND;
            case VK_5:
                return NOR;
            case VK_6:
                return XOR;
            case VK_7:
                return XNOR;
            case VK_8:
                return JOINT;
            case VK_I:
                return CONSTANT;
            case VK_O:
                return OUTPUT;
        }
        return null;
    }
    private Component mapComponentToObj(Constants.Component component) {
        switch (placingComponent) {
            case AND:
                return new And();
            case NAND:
                return new Nand();
            case NOR:
                return new Nor();
            case NOT:
                return new Not();
            case OR:
                return new Or();
            case XNOR:
                return new Xnor();
            case XOR:
                return new Xor();
            case CONSTANT:
                return new Constant(0);
            case OUTPUT:
                return new Output();
            case FULLADDER:
                return new Fulladder();
            case JOINT:
                return new Joint();
        }

        return null;
    }
    private void setSelectedDirection(Constants.Direction dir) {
        for (GUIElement selected : selectedComponents) {
            selected.setDirection(dir);
        }
    }
    private void setMouseMode(Constants.MouseMode mode) {
        mouseMode = mode;
        cursor.setMode(mode);
    }
    public void keyPressed(KeyEvent e) {
        Constants.Component comp = mapKeyToComponent(e);
        if (comp != null) {
            placingComponent = comp;
            return;
        }

        switch (e.getKeyCode()) {
            case VK_RIGHT:
                setSelectedDirection(EAST);
                break;
            case VK_LEFT:
                setSelectedDirection(WEST);
                break;
            case VK_UP:
                setSelectedDirection(NORTH);
                break;
            case VK_DOWN:
                setSelectedDirection(SOUTH);
                break;
            case VK_S:
                setMouseMode(SELECT);
                break;
            case VK_C:
                setMouseMode(CLICK);
                break;
            case VK_A:
                setMouseMode(PLACE);
                break;
            case VK_X:
                circuit.clear();
                break;
            case VK_BACK_SPACE:
                for (GUIElement selected : circuit.getAllSelectedComponents()) {
                    circuit.remove((Component) selected);
                }
                break;
        }
    }

    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}

    private void selectElement(GUIElement element) {
        element.setSelected(true);
        if (element instanceof Component) {
            selectedComponents.add(element);
        }
        if (element instanceof Dot) {
            selectedDots.add(element);
        }
        selectedElements.add(element);
    }
    private void unselectElement(GUIElement element) {
        element.setSelected(false);
        if (element instanceof Component) {
            selectedComponents.remove(element);
        }
        if (element instanceof Dot) {
            selectedDots.remove(element);
        }
        selectedElements.remove(element);
    }
    private void unselectAll() {
        for (GUIElement element : selectedElements) {
            element.setSelected(false);
        }
        selectedComponents.clear();
        selectedDots.clear();
        selectedElements.clear();
    }
    private void unselectDots() {
        for (GUIElement element : selectedElements) {
            if (element instanceof Dot) {
                element.setSelected(false);
            }
        }
        for (GUIElement element : selectedDots) {
            element.setSelected(false);
            selectedElements.remove(element);
        }
        selectedDots.clear();
    }
    public void mouseDragged(MouseEvent e) {
        switch (mouseMode) {
            case SELECT:
                // initialize a mode
                if (draggingMode == DRAGGING_NONE) {
                    GUIElement selected = ActionHandler.getSelectedWithPosition(camera, e, circuit);

                    if (selected == null) {
                        // move the view
                        if (e.isShiftDown()) {
                            draggingMode = DRAGGING_CAMERA;
                            cursor.savePoint(e.getX(), e.getY());
                        }
                        // draw selection box
                        else {
                            draggingMode = DRAGGING_SELECT;
                            cursor.savePoint(camera.getXMouse(e), camera.getYMouse(e));
                            cursor.setXY(camera.getXMouse(e), camera.getYMouse(e));
                        }

                        camera.savePoint();
                    }
                    else {
                        if (selectedComponents.size() == 0) {
                            selectElement(selected);

                            // set position relative to mouse at this time
                            selected.setXYOffset(selected.getX() - camera.getXMouse(e), camera.getYMouse(e) - selected.getY());
                            selected.setXY(camera.getXMouse(e), camera.getYMouse(e));
                        } else if (selectedComponents.size() == 1) {
                            if (selectedComponents.get(0) != selected) {
                                unselectElement(selectedComponents.get(0));
                                selectElement(selected);
                            }

                            // set position relative to mouse at this time
                            selected.setXYOffset(selected.getX() - camera.getXMouse(e), camera.getYMouse(e) - selected.getY());
                            selected.setXY(camera.getXMouse(e), camera.getYMouse(e));
                        } else {
                            if (!selected.isSelected()) {
                                unselectAll();
                                selectElement(selected);
                            }
                            for (GUIElement element : selectedComponents) {
                                element.setXYOffset(element.getX() - camera.getXMouse(e), camera.getYMouse(e) - element.getY());
                                element.setXY(camera.getXMouse(e), camera.getYMouse(e));
                            }
                        }

                        unselectDots();

                        draggingMode = DRAGGING_COMPONENT;
                    }
                }
                else if (draggingMode == DRAGGING_COMPONENT) {
                    for (GUIElement element : selectedComponents) {
                        element.setXY(camera.getXMouse(e), camera.getYMouse(e));
                    }
                }
                else if (draggingMode == DRAGGING_CAMERA) {
                    camera.setXY(camera.getXSave() + camera.convertDistX(cursor.getXSave() - e.getX()),camera.getYSave() + camera.convertDistY(cursor.getYSave() - e.getY()));
                }
                else if (draggingMode == DRAGGING_SELECT) {
                    cursor.setXY(camera.getXMouse(e), camera.getYMouse(e));
                }
        }
    }
    public void mouseClicked(MouseEvent e) {
        switch (mouseMode) {
            case PLACE:
                Component a = mapComponentToObj(placingComponent);
                a.setXY(camera.getXMouse(e), camera.getYMouse(e));
                circuit.addComponent(a);
                System.out.println(a);
                break;
            case SELECT:
                GUIElement selected = ActionHandler.getSelectedWithPosition(camera, e, circuit);

                // check if we have selected anything
                if (selected != null) {
                    // if multiple are selected, select only the one that is clicked on
                    if (selectedElements.size() > 1) {

                        for (GUIElement element : selectedElements) {
                            element.setSelected(false);
                        }
                        unselectAll();
                        selectElement(selected);
                    }

                    // if the only component that is selected is selected again, then unselect;
                    // else select another component
                    else if (selectedElements.size() == 1) {

                        // check if we are connecting dots
                        if (selectedElements.get(0) instanceof Dot && selected instanceof Dot) {
                            ((Dot) selected).connect((Dot) selectedElements.get(0));
                            unselectAll();
                        } else {
                            if (selectedElements.get(0) != selected) {
                                unselectElement(selectedElements.get(0));
                                selectElement(selected);
                            }
                        }
                    }

                    // if no components are selected then select the component
                    else if (selectedElements.size() == 0) {
                        selectElement(selected);
                    }
                } else {
                    unselectAll();
                }
                break;
            case CLICK:
                GUIElement clicked = ActionHandler.getSelectedWithPosition(camera, e, circuit);
                if (clicked != null) {
                    clicked.click();
                }
                break;
        }
    }
    public void mouseReleased(MouseEvent e) {
        for (GUIElement element : circuit.getAllComponents()) {
            element.resetXY();
        }

        if (draggingMode == DRAGGING_SELECT) {
            List<GUIElement> selected = ActionHandler.getSelectedWithBox(camera, circuit, cursor.getXSave(), cursor.getYSave(), cursor.getX(), cursor.getY());
            for (GUIElement s : selected) {
                selectElement(s);
            }
            unselectDots();
        }

        cursor.resetSave();
        camera.resetSave();
        draggingMode = DRAGGING_NONE;
    }
    public void mouseWheelMoved(MouseWheelEvent e) {
        double amt = e.getPreciseWheelRotation() / 10.0;
        camera.zoom(amt, amt);
    }

    public void update() {
        circuit.propagateLocal();
    }

    private void drawSelectSquare(Graphics2D g) {
        if (draggingMode == DRAGGING_SELECT) {
            DrawHandler.drawRectPoint(g, Color.red, cursor.getXSave(), cursor.getYSave(), cursor.getX(), cursor.getY());
        }
    }
    public void draw() {
        Graphics2D g = (Graphics2D) getBufferStrategy().getDrawGraphics();

        camera.clear(g);
        camera.draw(g);
        circuit.draw(g);
        drawSelectSquare(g);

        g.dispose();
        getBufferStrategy().show();
    }

    public static void main(String[] args) {
        new View();
    }
}
