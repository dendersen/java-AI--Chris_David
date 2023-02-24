package dk.mtdm.neuralNetwork;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
* File: Grayscale.java
* Description:
* Convert color image into grayscale image.
* @param path path of the image ex. "D:\\Image\\doggy.jpg"
* @throws IOException
*/

public class Grayscale {
  public static void main(String path)throws IOException{
    BufferedImage img = null;
    File file = null;

    //Read image
    try {
      file = new File(path);
      img = ImageIO.read(file);
    } catch (IOException e) {
      System.out.println(e);
    }

    
    //Get width and height of image
    int width = img.getWidth();
    int height = img.getHeight();

    //Get pixel values and exstract ARGB value
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int RGBval = img.getRGB(x, y);
        int alphaVal = (RGBval>>24)&0xff;
        int redVal = (RGBval>>16)&0xff;
        int greenVal = (RGBval>>8)&0xff;
        int blueVal = RGBval&0xff;
        int avg = (redVal+greenVal+blueVal)/3;

        RGBval = (alphaVal<<24) | (avg<<16) | (avg<<8) | avg;
        img.setRGB(x, y, RGBval);
      }
    }
    
    //Make image file
    try {
      file = new File("Image\\Output.jpg");
      ImageIO.write(img, "jpg", file);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}