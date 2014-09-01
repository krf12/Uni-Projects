/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package retinextheoryproject.gui;
import javax.swing.*;

/**
 *
 * @author
 */
public class BasicFrame extends JFrame {

    //Sets the default size and location of the frame
    BasicFrame() {
        this.setSize(1000, 500);
        this.setLocation(150, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     *
     * @param title
     */
    public void showIt(String title) {
        this.setTitle(title);
        this.setVisible(true);
    }

    //Show the frame and it's components
    /**
     *
     */
    public void showIt() {
        this.setVisible(true);
    }

    //Hide the frame and all it's components
    /**
     *
     */
    public void hideIt() {
        this.setVisible(false);
    }
}

