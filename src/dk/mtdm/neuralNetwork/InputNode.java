package dk.mtdm.neuralNetwork;

import java.awt.image.BufferedImage;

public class InputNode extends Node{
  
  private BufferedImage img;
  private int x;
  private int y;

  public InputNode(BufferedImage input, int ID_X, int ID_Y) {
    img = input;
    x = ID_X;
    y = ID_Y;
    calc();
  }
  @Override
  public float calc(){
    this.output = (float)((img.getRGB(x, y)>>16)&255)/255;
    return this.output;
  }
  @Override
  public void setBias(float bias){}
  @Override
  public void setWeights(float[] weights){}
}
