import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by danielkim802 on 1/6/17.
 */
public class ImageHandler {
    private HashMap<String, Image> imageLibrary = new HashMap<>();

    private static ImageHandler ih = new ImageHandler();

    private ImageHandler() {
        loadLibrary();
    }

    private void loadLibrary() {
        File dir = new File("src/Resources");
        File[] dirListings = dir.listFiles();

        if(dirListings != null) {
            for (File imageFile : dirListings) {
                loadImage(imageFile);
            }
        }
    }

    private void loadImage(File file) {
        String fileName = file.getName();
        Image image = null;

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageLibrary.put(fileName.substring(0, fileName.length() - 4), image);
    }

    public static Image getImage(String name) {
        return ih.imageLibrary.get(name);
    }
}
