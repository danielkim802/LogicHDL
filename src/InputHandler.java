import java.awt.event.*;

/**
 * Created by danielkim802 on 1/6/17.
 */
public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == 27) {
            System.exit(0);
        }
        System.out.println("pressed: " + e.getKeyChar());
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("released: " + e.getKeyChar());
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClick: " + e.getX() + " " + e.getY());
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased: " + e.getX() + " " + e.getY());
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        System.out.println("mouseMove: " + e.getX() + " " + e.getY());
    }

    public void mouseMoved(MouseEvent e) {

    }
}
