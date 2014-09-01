
package retinextheoryproject.data;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 *
 * 
 */
public class ImageMethods {
    
    

  /** Create Image from a file, then turn that into a BufferedImage.
   */

  public  BufferedImage getBufferedImage(String imageFile,
                                               Component c) {
    Image image = c.getToolkit().getImage(imageFile);
    waitForImage(image, c);
    BufferedImage bufferedImage =
      new BufferedImage(image.getWidth(c), image.getHeight(c),
                        BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = bufferedImage.createGraphics();
    g2d.drawImage(image, 0, 0, c);
    return(bufferedImage);
  }

  /** Take an Image associated with a file, and wait until it is
   *  done loading. Just a simple application of MediaTracker.
   *  If you are loading multiple images, don't use this
   *  consecutive times; instead use the version that takes
   *  an array of images.
   */

  public boolean waitForImage(Image image, Component c) {
    MediaTracker tracker = new MediaTracker(c);
    tracker.addImage(image, 0);
    try {
      tracker.waitForAll();
    } catch(InterruptedException ie) {}
    return(!tracker.isErrorAny());
  }
  
   public RGBArray loadRGBArrays(int rows, int cols, BufferedImage sourceImage, short[][] red, short[][] green, short[][] blue,  int[] sourceImageData) {

            red   = new short[rows][cols];
            green = new short[rows][cols];
            blue  = new short[rows][cols];
        

        // get pixels as ints of the form 0xRRGGBB
        sourceImageData = sourceImage.getRGB(0, 0, sourceImage.getWidth(),
                                           sourceImage.getHeight(), null, 0,
                                           sourceImage.getWidth());

        // extract red, green, and blue components from each pixel
        int index;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                index = (row * cols) + col;
                unpackPixel(sourceImageData[index], red, green, blue, row, col);
            }
        }
        
        RGBArray rgb = new RGBArray(red, green, blue);
       
        return rgb;
    }
   
    public BufferedImage makeNewBufferedImage(int rows, int cols, short[][] ImageDataRed, short[][] ImageDataGreen, short[][] ImageDataBlue ) {
        int[] newBufferedImageData = new int[rows * cols];
        int index;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (ImageDataRed[row][col] < 0) {
                    ImageDataRed[row][col] = 0;
                }
                if (ImageDataRed[row][col] > 255) {
                    ImageDataRed[row][col] = 255;
                }
                if (ImageDataGreen[row][col] < 0) {
                    ImageDataGreen[row][col] = 0;
                }
                if (ImageDataGreen[row][col] > 255) {
                    ImageDataGreen[row][col] = 255;
                }
                if (ImageDataBlue[row][col] < 0) {
                    ImageDataBlue[row][col] = 0;
                }
                if (ImageDataBlue[row][col] > 255) {
                   ImageDataBlue[row][col] = 255;
                }
                index = (row * cols) + col;
                newBufferedImageData[index] = packPixel(ImageDataRed[row][col],
                                                        ImageDataGreen[row][col],
                                                        ImageDataBlue[row][col]
							 );
            }
        }

        BufferedImage newImage = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
               index = (row * cols) + col;
                newImage.setRGB(col, row, newBufferedImageData[index]);
                }
                }

        return newImage;
    }
    
    public BufferedImage convolveColorImage(short[][] red, short[][] blue, short[][] green, int rows, int cols, float[] kern) {
        short[][] newRed = new short[rows][cols];
        short[][] newGreen = new short[rows][cols];
        short[][] newBlue = new short[rows][cols];
        
        float[] kernel =kern;
        
        int length = kernel.length;
        
        float tr[] = new float[length];
        float tg[] = new float[length];
        float tb[] = new float[length];
        int k;
        

        float tempr, tempg, tempb;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row == 0 || row == rows - 1 || col == 0 || col == cols - 1) {
                    newRed[row][col] = red[row][col];
                    newGreen[row][col] = green[row][col];
                    newBlue[row][col] = blue[row][col];
                } else {
                    k = -1;
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            k++;
                            tr[k] = red[row + i][col + j];
                            tg[k] = green[row + i][col + j];
                            tb[k] = blue[row + i][col + j];
                        }
                    }
                    tempr = tempg = tempb = 0;
                    for (int i = 0; i < length; i++) {
                        tempr += kernel[i] * tr[i];
                        tempg += kernel[i] * tg[i];
                        tempb += kernel[i] * tb[i];
                    }
                   

                    if (tempr < 0f) {
                        tempr = 0f;
                    }
                    if (tempr > 255f) {
                        tempr = 255f;
                    }
                    if (tempg < 0f) {
                        tempg = 0f;
                    }
                    if (tempg > 255f) {
                        tempg = 255f;
                    }
                    if (tempb < 0f) {
                        tempb = 0f;
                    }
                    if (tempb > 255f) {
                        tempb = 255f;
                    }

                    newRed[row][col] = (short) tempr;
                    newGreen[row][col] = (short) tempg;
                    newBlue[row][col] = (short) tempb;
                }

            }
        }

        BufferedImage convolveColorImage = makeNewBufferedImage(rows, cols, newRed, newGreen, newBlue);
        return convolveColorImage;
    }

      private int packPixel(int red, int green, int blue) {
        return (red << 16) | (green << 8) | blue;
    }

    private void unpackPixel(int pixel, short[][] red, short[][] green, short[][] blue, int row, int col) {
        red[row][col] = (short) ((pixel >> 16) & 0xFF);
        green[row][col] = (short) ((pixel >> 8) & 0xFF);
        blue[row][col] = (short) ((pixel >> 0) & 0xFF);
    }
    
     public float[] RGBtoHSV(float r, float g, float b) {
        float min, max, delta;
        float h, s, v;

        r /= 255f;
        g /= 255f;
        b /= 255f;

        min = Math.min(Math.min(r, g), b);
        max = Math.max(Math.max(r, g), b);

        v = max;				

        delta = max - min;

        if (max == 0) // r = g = b = 0		
        {
            s = 0f;
        } else {
            s = delta / max; // s
        }
        if (delta != 0) {
            if (r == max) {
                h = (g - b) / delta;		
                if (h < 0) {
                    h += 6f;
                }
            } else {
                if (g == max) {
                    h = 2f + (b - r) / delta;	
                } else {
                    h = 4f + (r - g) / delta;	
                }
            }

            h *= 60f;				

        } else {
            h = 0f;
        }

        return new float[]{h, s, v};

    }

    public short[] HSVtoRGB(float h, float s, float v) {
        int i;
        float f, p, q, t;
        float r, g, b;

        v *= 255f;

        if (s == 0) {
            r = g = b = v;
            return new short[]{(short) r, (short) g, (short) b};
        }

        h /= 60f;
        i = 0;			
        for (int j = 0; j < 6; j++) {
            if (h >= j && h < j + 1) {
                i = j;
            }
        }

        f = h - i;			
        p = v * (1 - s);
        q = v * (1 - s * f);
        t = v * (1 - s * (1 - f));

        switch (i) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            default:		
                r = v;
                g = p;
                b = q;
                break;
        }

        return new short[]{(short) r, (short) g, (short) b};

    }
    
    
    }
    
 

