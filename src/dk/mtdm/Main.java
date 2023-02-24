package dk.mtdm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dk.mtdm.neuralNetwork.InputNode;

/**
 * Main
 */
public class Main {
  public static void main(String[] args) {
    BufferedImage img = null;
    try {
      File file = new File("src\\dk\\mtdm\\MNISTDataset\\numbers\\0\\0_1.jpg");
      img = ImageIO.read(file);
    } catch (IOException e) {
      System.out.println(e);
    }
    InputNode a = new InputNode(img, 0, 0);
  }
}