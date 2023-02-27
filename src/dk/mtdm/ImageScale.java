package dk.mtdm;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageScale{
  public static void main(String[] args) {
    int[] size = findBiggest();
    System.out.println("\nwriting");
    for (int i = 0; i < 10; i++) {
      for (int j = 1; j < 1001; j++){
        if(j%200 == 0){
          System.out.println(((i)*1000+j) + "/" + 10000 + "\tcomplete");
        }
        String path = Main.imagePathGet + i + "\\" + i + "_" + j +".jpg";
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
          File file = new File(Main.imagePath + i + "\\" + i + "_" + j +".jpg");
          ImageIO.write(img, "jpg", file);
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    }
    save(size[0], size[1]);
  }

  private static void save(int width, int height){
    try{
      File file = new File(Main.imagePath +"maxSize.txt");
      if(file.createNewFile()){
        System.out.println("file created: " + file.getName());
      }else {
        System.out.println("file already exists.");
      }
    }catch (IOException e){
      System.out.println("an error occured while making file");
      e.printStackTrace();
    }
    try {
      FileWriter myWriter = new FileWriter(Main.imagePath + "maxSize.txt");
      myWriter.write(width + " " + height);
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private static int[] findBiggest() {
    int width = 0;
    int height = 0;
    System.out.println("loading");
    for (int i = 0; i < 10; i++) {
      for (int j = 1; j < 1001; j++){
        if(j%200 == 0){
          System.out.println(((i)*1000+j) + "/" + 10000 + "\tcomplete");
        }
        String path = Main.imagePathGet  + i + "\\" + i + "_" + j +".jpg";
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