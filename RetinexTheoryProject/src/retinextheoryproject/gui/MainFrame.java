/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package retinextheoryproject.gui;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 *
 * @author Kit
 */
public class MainFrame  extends BasicFrame{
    
    
        JPanel tp;
        JPanel nip;

    
        public MainFrame(String filename){
         
         tp = new TitlePanel();
         nip = new NewImagePanel(filename);
            
         this.setLayout(new BorderLayout());
         
        this.add(tp, BorderLayout.NORTH);
       
        this.add(nip, BorderLayout.CENTER);
        
        showIt(); 
        
        }
       
}
