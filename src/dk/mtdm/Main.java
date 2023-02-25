package dk.mtdm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import javax.imageio.ImageIO;

import dk.mtdm.neuralNetwork.InputNode;
import dk.mtdm.neuralNetwork.Node;
import dk.mtdm.neuralNetwork.SigmoidCurves;

/**
 * Main
 */
public class Main {
  Function<? super Float, ? extends Float> calc = x -> x+2;
  public static void main(String[] args) {
    BufferedImage img = null;
    try {
      File file = new File("src\\dk\\mtdm\\MNISTDataset\\numbers\\0\\0_1.jpg");
      img = ImageIO.read(file);
    } catch (IOException e) {
      System.out.println(e);
    }
    InputNode[] a = {new InputNode(img, 0, 0)};
    float[] x = {1};
    try {
      Node b = new Node(0, x, a, SigmoidCurves.logistic);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}