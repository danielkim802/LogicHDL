package Render;

import Components.Circuit;
import Components.Gates.*;
import Components.Literals.*;
import Components.Modules.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by danielkim802 on 1/17/17.
 */
public class ResourceLibrary {
    private static Map<Class, List<BufferedImage>> imageLibrary;
    private static Map<String, List<BufferedImage>> accessoryLibrary;
    private static ResourceLibrary resourceLibrary = new ResourceLibrary();

    // src: http://stackoverflow.com/questions/196890/java2d-performance-issues
    private BufferedImage optimizeImage(BufferedImage image) {
        // obtain the current system graphical settings
        GraphicsConfiguration gfx_config = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

        /*
         * if image is already compatible and optimized for current system
         * settings, simply return it
         */
        if (image.getColorModel().equals(gfx_config.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage new_image = gfx_config.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = (Graphics2D) new_image.getGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return new_image;
    }

    private void loadImages() {
        List<Class> allClasses = Arrays.asList(
                // gates
                And.class, Nand.class, Nor.class, Not.class,
                Or.class, Xnor.class, Xor.class,

                // literals
                Constant.class, Joint.class, Output.class,

                // modules
                Circuit.class, Fulladder.class
        );

        for (Class clazz : allClasses) {
            boolean done = false;
            int frame = 0;
            imageLibrary.put(clazz, new ArrayList<>());
            while (!done) {
                try {
                    imageLibrary.get(clazz).add(optimizeImage(ImageIO.read(new File("src/Resources/"+clazz.getSimpleName()+"_"+frame+".png"))));
                    System.out.println("loaded image: " + "src/Resources/" + clazz.getSimpleName() + "_" + frame+".png");
                    frame ++;
                }
                catch (IOException e) {
                    done = true;
                }
            }
        }
    }

    private void loadAccessories() {
        List<String> allAccessories = Arrays.asList(
                "dot",
                "cursor"
        );

        for (String accessory : allAccessories) {
            boolean done = false;
            int frame = 0;
            accessoryLibrary.put(accessory, new ArrayList<>());
            while (!done) {
                try {
                    accessoryLibrary.get(accessory).add(optimizeImage(ImageIO.read(new File("src/Resources/Misc_"+accessory+"_"+frame+".png"))));
                    System.out.println("loaded accessory: " + "src/Resources/Misc_" + accessory + "_" + frame+".png");
                    frame ++;
                }
                catch (IOException e) {
                    done = true;
                }
            }
        }
    }

    private ResourceLibrary() {
        imageLibrary = new HashMap<>();
        accessoryLibrary = new HashMap<>();
        loadImages();
        loadAccessories();
    }

    public static List<BufferedImage> getImages(Class clazz) {
        return imageLibrary.get(clazz);
    }
    public static List<BufferedImage> getImages(String accessory) {
        return accessoryLibrary.get(accessory);
    }
}
