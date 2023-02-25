package dk.mtdm;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageScale{
  public static void main(String[] args) {
    int[] size = findBiggest();
    
    for (int i = 0; i < 1; i++) {
      for (int j = 1; j < 100; j++){
        String path = "src\\dk\\mtdm\\MNISTDataset\\numbers\\" + i + "\\" + i + "_" + j +".jpg";
        BufferedImage img = null;
        try {
          File file = new File(path);
          img = ImageIO.read(file);
        } catch (IOException e) {
          System.out.println(e);
        }
        BufferedImage resizedImage = new BufferedImage(size[0], size[1], BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(img, 0, 0, size[0], size[1], null);
        graphics2D.dispose();
        img = resizedImage;
        
        try {
          File file = new File(path);
          ImageIO.write(img, "jpg", file);
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    }
  }

  private static int[] findBiggest() {
    int width = 0;
    int height = 0;
    for (int i = 0; i < 10; i++) {
      for (int j = 1; j < 1001; j++){
        String path = "src\\dk\\mtdm\\MNISTDataset\\numbers\\" + i + "\\" + i + "_" + j +".jpg";
        BufferedImage img = null;
        try {
          File file = new File(path);
          img = ImageIO.read(file);
        } catch (IOException e) {
          System.out.println(e);
        }
        if(width < img.getWidth()){
          width = img.getWidth();
        }
        if(height < img.getWidth()){
          height = img.getHeight();
        }
      }
    }
    int[] out = {height,width};
    return out;
  }


}