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
import java.awt.image.*;
import javax.swing.*;
import retinextheoryproject.data.ImageMethods;
import retinextheoryproject.data.RGBArray;

public class NewImagePanel extends JPanel{
    
    JLabel ap = new JLabel("After Processing");
    BufferedImage img;
    BufferedImage newimg;
    ImageMethods iu = new ImageMethods();
    
       float[] highPassKernel = {
            -1f, -1f, -1f,
            -1f, 9f, -1f,
            -1f, -1f, -1f
       };
       
       float[] brightKernel = {
           0, 0, 0,
           0, 1, 0,
           0, 0, 0
       };
       
    private short[][] red; // array of red which is extracted from the image data
    private short[][] green; // array of green which is extracted from the image data
    private short[][] blue; // array of blue which is extracted from the image data
            
    private int[] sourceImageData; // data array of input image
    
    int rows;
    int cols;
    
    
    public NewImagePanel(String filename){
            this.setLayout(new BorderLayout());
            
            this.add(ap, BorderLayout.NORTH);
            
            img = iu.getBufferedImage(filename, this);
            
            rows = img.getHeight();
            cols = img.getWidth();
            
             short[][] newRed = new short[rows][cols]; 
             short[][] newGreen = new short[rows][cols];
             short[][] newBlue = new short[rows][cols];
            
            RGBArray rgb = iu.loadRGBArrays(rows, cols, img, red, green, blue, sourceImageData);
            
            short[][] temRed = rgb.getRed();
            short[][] temBlue = rgb.getBlue();
            short[][] temGreen = rgb.getGreen();
            /*
            System.out.println("Red:");
            
             for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                
                
               System.out.println(temRed[i][j]);
               
                
            }
             }
             
             
             
               System.out.println("Blue:");
            
             for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                
                
               System.out.println(temBlue[i][j]);
               
                
            }
             }
             */
             
             
               System.out.println("Green:");
            
             for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                
                
               System.out.println(temGreen[i][j]);
               
                
            }
             }

            
            newimg = iu.convolveColorImage(temRed, temBlue, temGreen, rows, cols, highPassKernel);
            
            
            RGBArray rgb1 = iu.loadRGBArrays(rows, cols, newimg, red, green, blue, sourceImageData);
            
             short[][] temRed1 = rgb1.getRed();
            short[][] temBlue1 = rgb1.getBlue();
            short[][] temGreen1 = rgb1.getGreen();
            
            newimg = iu.convolveColorImage(temRed1, temBlue1, temGreen1, rows, cols, brightKernel);
            
            RGBArray rgb2 = iu.loadRGBArrays(rows, cols, newimg, red, green, blue, sourceImageData);
            
             short[][] temRed2 = rgb2.getRed();
            short[][] temBlue2 = rgb2.getBlue();
            short[][] temGreen2 = rgb2.getGreen();
            
            float[][] Luminance = new float[rows][cols];
            
            
            for(int i = 0; i < rows; i ++){
                for(int j =0; j < cols; j++){
                    //Formula for calculating the perceived Brightness from RGB values.
                        Luminance[i][j] = (float)Math.sqrt(Math.pow(0.241 * temRed2[i][j], 2) + Math.pow(0.691 * temGreen2[i][j], 2) + Math.pow(0.068 *temBlue2[i][j], 2));
                }
            }
            
            float[][] temHue = new float[rows][cols];
            float[][] temSat = new float[rows][cols];
            float[][] temVal = new float[rows][cols];
            float[][] Norm = new float[rows][cols];
                     
            for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float r = (float) temRed2[i][j];
                float g = (float) temGreen2[i][j];
                float b = (float) temBlue2[i][j];

                float[] tem = iu.RGBtoHSV(r, g, b);

                temHue[i][j] = tem[0];
                temSat[i][j] = tem[1];
                temVal[i][j] = tem[2];

            }
        }
            
       
            
       float  rmin = Float.MAX_VALUE;
       float rmax = Float.MIN_VALUE;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (temVal[i][j] < rmin) {
                    rmin = temVal[i][j];
                }
                if (temVal[i][j] > rmax) {
                    rmax = temVal[i][j];
                }
            }
        }
       

        rmax -= rmin;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
               float  temp = 255f * (temVal[i][j] - rmin) / rmax;
                Norm[i][j] = temp; 
            }
        }
        
        
            
            float[][] Reflectance = new float[rows][cols];
            
               for(int i = 0; i < rows; i ++){
                for(int j =0; j < cols; j++){
                    
                    Reflectance[i][j] =(float) Math.log((Norm[i][j] + 1) / (Luminance[i][j] + 1));
                    
                }
            }
               
                for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                        
                    float a = Reflectance[x][y];
                    float b = temHue[x][y];
                    float c = temSat[x][y];
                    
                    short[] newrgb = iu.HSVtoRGB(b, c, a);
                    newRed[x][y] = newrgb[0];
                    newGreen[x][y] = newrgb[1];
                    newBlue[x][y] = newrgb[2];
                    
                }
            }
                
           
           
            
             newimg = iu.makeNewBufferedImage(rows, cols, newRed, newGreen, newBlue);
               
            JLabel newimage = new JLabel(new ImageIcon(newimg));
            
            
            this.add(newimage, BorderLayout.CENTER);

            
    }
    
}
