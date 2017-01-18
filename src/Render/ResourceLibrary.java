package Render;

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
    private Map<Class, List<BufferedImage>> library;
    private static ResourceLibrary resourceLibrary = new ResourceLibrary();

    public static ResourceLibrary library() {
        return resourceLibrary;
    }

    private ResourceLibrary() {
        library = new HashMap<>();
        List<Class> allClasses = Arrays.asList(
                // gates
                And.class,
                Nand.class,
                Nor.class,
                Not.class,
                Or.class,
                Xnor.class,
                Xor.class,

                // literals
                Constant.class,
                Joint.class,
                Output.class,

                // modules
                Fulladder.class
        );

        for (Class clazz : allClasses) {
            boolean done = false;
            int frame = 0;
            library.put(clazz, new ArrayList<>());
            while (!done) {
                try {
                    System.out.println("Components\\Resources\\" + clazz.getSimpleName() + "_" + frame+ ".png");
                    library.get(clazz).add(ImageIO.read(new File(""+clazz.getSimpleName()+"_"+frame+".png")));
                    System.out.println("loaded image: " + "Resources/" + clazz.getSimpleName() + "_" + frame+".png");
                    frame ++;
                }
                catch (IOException e) {
                    done = true;
                }
            }
        }
    }
}
