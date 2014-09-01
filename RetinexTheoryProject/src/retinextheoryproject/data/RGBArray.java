/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package retinextheoryproject.data;

/**
 *
 * @author Kit
 */
public class RGBArray {
    
        short[][] red;
        short[][] green;
        short[][]blue;
        
        public RGBArray(short[][] r, short[][] g, short [][] b){
            red = r;
            green = g;
            blue = b;
        }
        
        public void setRed(short[][] r){
            red = r;
        }
        
          public void setGreen(short[][] g){
            green = g;
        }
          
          public void setBlue(short[][] b){
            blue = b;
        }
        
        public short[][] getRed(){
            return red;
        }
        
          public short[][] getGreen(){
            return green;
        }
          
          public short[][] getBlue(){
            return blue;
        }
        
    
}
