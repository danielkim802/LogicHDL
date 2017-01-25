package Components;

import Actions.Selectable;
import Render.DrawHandler;
import Render.Drawable;
import Render.ResourceLibrary;

import javax.lang.model.type.NullType;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by danielkim802 on 1/16/17.
 */
public abstract class Component extends Drawable {
    private Map<String, Wire> inputs = new HashMap<>();
    private Map<String, List<Wire>> outputs = new HashMap<>();
    private Map<String, Dot> inputDots = new HashMap<>();
    private Map<String, Dot> outputDots = new HashMap<>();

    public Component(int inputlen, int outputlen) {
        setImages(ResourceLibrary.getImages(this.getClass()));

        // make inputs and outputs
        setIO(inputlen, outputlen);

        // make dots
        makeDots();

        // move dots to correct position
        setDotPositions();
        updateDots();
    }

    private void makeDots() {
        for (String key : inputs.keySet()) {
            inputDots.put(key, new Dot(this, true, key));
        }
        for (String key : outputs.keySet()) {
            outputDots.put(key, new Dot(this, false, key));
        }
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
    public void drawSelected(Graphics2D g) {
        if (isSelected()) {
            DrawHandler.drawRect(g, Color.red, getWidth(), getHeight(), getX(), getY());
        }
    }
    public void rotateDots(int amt) {
        System.out.println("rotate");
        Function<Point, Point> mirrorXY = pos -> {
            double tempx = pos.getX();
            double tempy = pos.getY();
            pos.setLocation(tempy, tempx);
            return pos;
        };

        Function<Point, Point> mirrorNegXY = pos -> {
            double tempx = pos.getX();
            double tempy = pos.getY();
            pos.setLocation(-tempy, -tempx);
            return pos;
        };

        Function<Point, Point> mirrorX = pos -> {
            pos.setLocation(pos.getX(), -pos.getY());
            return pos;
        };

        Function<Point, Point> mirrorY = pos -> {
            pos.setLocation(-pos.getX(), pos.getY());
            return pos;
        };

        Function<Point, Point> rotateLeft = pos -> mirrorY.apply(mirrorXY.apply(pos));
        Function<Point, Point> rotateRight = pos -> mirrorY.apply(mirrorNegXY.apply(pos));

        List<Dot> dots = new ArrayList<>();
        dots.addAll(inputDots.values());
        dots.addAll(outputDots.values());
        for (Dot dot : dots) {
            Point pos = new Point(dot.getXRelative(), dot.getYRelative());

            for (int j = 0; j < Math.abs(amt); j ++) {
                if (amt > 0) {
                    pos = rotateRight.apply(pos);
                }
                else if (amt < 0) {
                    pos = rotateLeft.apply(pos);
                }
            }

            dot.setXYRelative((int) pos.getX(), (int) pos.getY());
        }
    }

    public void updateDots() {
        List<Dot> dots = new ArrayList<>();
        dots.addAll(inputDots.values());
        dots.addAll(outputDots.values());
        for (Dot dot : dots) {
            dot.setXY(getX() + dot.getXRelative(), getY() - dot.getYRelative());
        }
    }

    // abstract
    public abstract Component copy();
    public abstract void propagate();
    public abstract void setIO(int ins, int outs);
    public abstract void setDotPositions();
    public void click() {}
    public void drag(int x, int y) {
        setXY(x, y);
    }

    public void draw(Graphics2D g) {
        DrawHandler.drawImage(g, getImage(), getDirection(), getX(), getY());
        drawDots(g);
        drawSelected(g);
    }
}
