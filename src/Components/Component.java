package Components;

import Actions.GUIElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Component extends GUIElement {
    private Map<String, Wire> inputs = new HashMap<>();
    private Map<String, List<Wire>> outputs = new HashMap<>();
    private Map<String, Dot> inputDots = new HashMap<>();
    private Map<String, Dot> outputDots = new HashMap<>();

    private void makeDots() {
        for (String key : inputs.keySet()) {
            inputDots.put(key, new Dot(this, true, key));
        }
        for (String key : outputs.keySet()) {
            outputDots.put(key, new Dot(this, false, key));
        }
    }
    public Component(int inputlen, int outputlen) {
        super();

        // make inputs and outputs
        setIO(inputlen, outputlen);

        // make dots
        makeDots();

        // move dots to correct position
        setDotPositions();
        updateDots();
    }

    // input output methods
    public Map<String, Wire> getInputs() {
        return inputs;
    }
    public Map<String, List<Wire>> getOutputs() {
        return outputs;
    }
    public Map<String, Dot> getInputDots() {
        return inputDots;
    }
    public Map<String, Dot> getOutputDots() {
        return outputDots;
    }
    public List<Dot> getDots() {
        List<Dot> dots = new ArrayList<>();
        dots.addAll(inputDots.values());
        dots.addAll(outputDots.values());
        return dots;
    }
    public List<Wire> getWires() {
        List<Wire> all = new ArrayList<>();
        all.addAll(inputs.values());
        for (List<Wire> wires : outputs.values()) {
            all.addAll(wires);
        }
        return all;
    }
    public List<Wire> getInputWires() {
        List<Wire> wires = new ArrayList<>();
        wires.addAll(inputs.values());
        return wires;
    }
    public List<Wire> getOutputWires() {
        List<Wire> wires = new ArrayList<>();
        for (List<Wire> wirelist : outputs.values()) {
            wires.addAll(wirelist);
        }
        return wires;
    }

    // connects two components given an output key and input key
    public void connect(String output, String input, Component other) {
        if (!other.getInputs().get(input).isAssigned()) {
            Wire wire = new Wire(this, outputDots.get(output), other, other.inputDots.get(input));
            other.getInputs().put(input, wire);
            outputs.get(output).add(wire);
        }
    }

    // disconnects a wire given a wire object and boolean indicating whether
    // the wire is an input for the component
    public void disconnect(Wire wire, boolean in) {
        if (in) {
            for (String key : inputs.keySet()) {
                if (inputs.get(key) == wire) {
                    inputs.put(key, new Wire());
                    wire.getFrom().disconnect(wire, false);
                    return;
                }
            }
        }
        else {
            for (List<Wire> wires : outputs.values()) {
                for (int i = 0; i < wires.size(); i ++) {
                    if (wires.get(i) == wire) {
                        wires.set(i, new Wire());
                        wire.getConnect().disconnect(wire, true);
                        return;
                    }
                }
            }
        }
    }

    // deletes all wires leading into and out of the component
    public void delete() {
        for (Wire wire : inputs.values()) {
            if (wire.isAssigned()) {
                disconnect(wire, true);
            }
        }
        for (List<Wire> wires : outputs.values()) {
            for (Wire wire : wires) {
                if (wire.isAssigned()) {
                    disconnect(wire, false);
                }
            }
        }
    }

    // returns true if all inputs have been assigned to another component
    public boolean allInputsAssigned() {
        return getInputs().values().stream().filter( a -> !a.isAssigned() ).toArray().length == 0;
    }

    // draw methods
    public void drawDots(Graphics2D g) {
        for (Dot dot : getInputDots().values()) {
            dot.draw(g);
        }
        for (Dot dot : getOutputDots().values()) {
            dot.draw(g);
        }
    }

    // dot methods
    private Point mirrorXY(Point pos) {
        double tempx = pos.getX();
        double tempy = pos.getY();
        pos.setLocation(tempy, tempx);
        return pos;
    }
    private Point mirrorNegXY(Point pos) {
        double tempx = pos.getX();
        double tempy = pos.getY();
        pos.setLocation(-tempy, -tempx);
        return pos;
    }
    private Point mirrorY(Point pos) {
        pos.setLocation(-pos.getX(), pos.getY());
        return pos;
    }
    private Point rotateLeft(Point pos) {
        return mirrorY(mirrorXY(pos));
    }
    private Point rotateRight(Point pos) {
        return mirrorY(mirrorNegXY(pos));
    }
    public void rotateDot(Dot dot, int amt) {
        Point pos = new Point(dot.getXOffset(), dot.getYOffset());

        for (int j = 0; j < Math.abs(amt); j ++) {
            if (amt > 0) {
                pos = rotateRight(pos);
            }
            else if (amt < 0) {
                pos = rotateLeft(pos);
            }
        }

        dot.setXYOffset((int) pos.getX(), (int) pos.getY());
    }
    public void rotateDots(int amt) {
        if (amt == 0) return;

        // apply transforms
        List<Dot> dots = getDots();
        for (Dot dot : dots) {
            rotateDot(dot, amt);
        }
    }
    public void updateDots() {
        // reset relative dot positions
        setDotPositions();

        // update dot positions relative to the component and rotate
        for (Dot dot : inputDots.values()) {
            dot.update();
            rotateDot(dot, getDirectionAsInt());
        }
        for (Dot dot : outputDots.values()) {
            dot.update();
            rotateDot(dot, getDirectionAsInt());
        }
    }

    // abstract
    public abstract Component copy();
    public abstract void propagate();
    public abstract void setIO(int ins, int outs);
    public abstract void setDotPositions();
    public void draw(Graphics2D g) {
        drawImage(g);
        drawDots(g);
    }

    // action methods
    public void click() {}
}
