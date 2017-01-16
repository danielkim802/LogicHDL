import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by danielkim802 on 12/26/16.
 */
public class View extends JFrame {

    JPanel panel = new JPanel();
    InputHandler ih = new InputHandler();

    public static void main(String[] args) {
        new View();
    }

    public View() {
        super("LogicSim");

        setSize(300, 400);
        setResizable(true);
        setLocationRelativeTo(null);
        addKeyListener(ih);
        addMouseListener(ih);
        addMouseMotionListener(ih);

        setUndecorated(true);
        /*panel.add(new JButton("hi"));
        panel.add(new AndGUI());
        add(panel);*/

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public void paint(Graphics g) {
        //g.clearRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;



        AffineTransform tx = new AffineTransform();
        tx.translate(100, 100);
        tx.scale(2, 2);
        Image image = ImageHandler.getImage("spr_gate_and_0");

        g2d.drawImage(image, tx, null);
        g.drawRect(0, 0, 100, 100);
        g.setColor(Color.getHSBColor(.4f,.5f, .5f));
        g.fillRect( 200, 200, 10, 50);
    }
}
