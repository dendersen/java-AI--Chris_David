package dk.mtdm.neuralNetwork;

import java.awt.image.BufferedImage;

public class InputNode extends Node{
  
  private BufferedImage img;
  int x;
  int y;
  int out;

  public InputNode(BufferedImage input, int ID_X, int ID_Y) {
    img = input;
    x = ID_X;
    y = ID_Y;
    out = img.getRGB(x, y)>>24;
    System.out.println(out);
  }
  
}
