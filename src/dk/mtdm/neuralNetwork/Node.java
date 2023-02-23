package dk.mtdm.neuralNetwork;

public class Node {
  private float bias;
  private float[] weights;
  private Float output;
  private Node[] pulls;
  /**
   * 
   * @param bias the biasses of the neural network
   * @param weights the weights of the neural network 
   * @param pulls the nodes that are to be treated as inputs
   * @throws Exception if pulls is of different length than weights
   */
  public Node(float bias, float[] weights, Node[] pulls) throws Exception{
    this.bias = bias;
    this.weights = weights;
    this.pulls = pulls;
    
    if(this.pulls.length != this.weights.length){
      throw new Exception("pulls and weights are not same lenght");
    }
  }
  /**
   * @return the output of the 
   */
  public float calc(){
    float out = this.bias;
    for (int i = 0; i < this.weights.length; i++) {
      out += pulls[i].getValue() * this.weights[i];
    }
    output = out;
    return out;
  }
  public float getValue(){
    if(output == null){
      return calc();
    }
    return output;
  }
  public void resetValue(){
    output = null;
  }
  public void setBias(float bias) {
    this.bias = bias;
  }
  public void setWeights(float[] weights) throws Exception {
    if(this.pulls.length != weights.length){
      throw new Exception("pulls and weights are not same lenght");
    }
    this.weights = weights;
  }
}
