package dk.mtdm.neuralNetwork;

public class InputNode extends Node{
  
  private int input[] = new int[28*28];

  public InputNode(float bias, float[] weights, Node[] pulls) throws Exception {
    super(bias, weights, pulls);
  }
  
}
