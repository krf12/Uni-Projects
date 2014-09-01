/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package retinextheoryproject.gui;

/**
 *
 * @author Kit
 */

import java.awt.*;
import javax.swing.*;

public class TitlePanel extends JPanel {
    
    JLabel title = new JLabel("Retinex Theory Project");
    
    public TitlePanel(){
        
        this.setLayout(new BorderLayout());
        
        this.add(title, BorderLayout.NORTH);
        
    }
    
}
