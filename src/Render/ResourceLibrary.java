package Render;

import Components.Dot;
import Components.Gates.*;
import Components.Literals.*;
import Components.Modules.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by danielkim802 on 1/17/17.
 */
public class ResourceLibrary {
    private static Map<Class, List<BufferedImage>> imageLibrary;
    private static Map<String, List<BufferedImage>> accessoryLibrary;
    private static ResourceLibrary resourceLibrary = new ResourceLibrary();

//    public static ResourceLibrary library() {
//        return resourceLibrary;
//    }

    private void loadImages() {
        List<Class> allClasses = Arrays.asList(
                // gates
                And.class, Nand.class, Nor.class, Not.class,
                Or.class, Xnor.class, Xor.class,

                // literals
                Constant.class, Joint.class, Output.class,

                // modules
                Fulladder.class
        );

        for (Class clazz : allClasses) {
            boolean done = false;
            int frame = 0;
            imageLibrary.put(clazz, new ArrayList<>());
            while (!done) {
                try {
                    imageLibrary.get(clazz).add(ImageIO.read(new File("src/Resources/"+clazz.getSimpleName()+"_"+frame+".png")));
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
                "dot"
        );

        for (String accessory : allAccessories) {
            boolean done = false;
            int frame = 0;
            accessoryLibrary.put(accessory, new ArrayList<>());
            while (!done) {
                try {
                    accessoryLibrary.get(accessory).add(ImageIO.read(new File("src/Resources/Misc_"+accessory+"_"+frame+".png")));
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
