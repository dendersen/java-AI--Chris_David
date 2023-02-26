package dk.mtdm.neuralNetwork;

import java.awt.image.BufferedImage;

public class Node {
  protected float bias;
  protected float[] weights;
  protected Float output;
  protected Node[] pulls;
  protected SigmoidCurve activationFunction;
  /**
   * 
   * @param bias the biasses of the neural network
   * @param weights the weights of the neural network 
   * @param pulls the nodes that are to be treated as inputs
   * @throws Exception if pulls is of different length than weights
   */
  protected Node(){}
  public Node(float bias, float[] weights, Node[] pulls, SigmoidCurve ActivationFunction) throws Exception{
    this.bias = bias;
    this.weights = weights;
    this.pulls = pulls;
    this.activationFunction = ActivationFunction;
    
    if(this.pulls.length != this.weights.length){
      throw new Exception("pulls and weights are not same lenght");
    }
  }
  /**
   * @return the output of the 
   */
  public float[] calc(){
    float out = this.bias;
    for (int i = 0; i < this.weights.length; i++) {
      out += pulls[i].getValue() * this.weights[i];
    }
    output = activationFunction.apply(out);
    float[] z = {output,out};
    return z;
  }
  public float getValue(){
    if(output == null){
      return calc()[0];
    }
    return output;
  }
  public void resetValue(){
    output = null;
  }
  public void setBias(float bias) {
    this.bias = bias;
  }
  public float getBias() {
    return this.bias;
  }
  public void modBias(float bias) {
    this.bias += bias;
  }
  public void setWeights(float[] weights) throws Exception {
    if(this.pulls.length != weights.length){
      throw new Exception("pulls and weights are not same lenght");
    }
    this.weights = weights;
  }
  public float[] getWeights() {
    return this.weights.clone();
  }
  public int getWeightLength(){
    return this.weights.length;
  }
  public void modWeights(float[] weights) throws Exception {
    if(this.pulls.length != weights.length){
      throw new Exception("pulls and weights are not same lenght");
    }
    for (int i = 0; i < weights.length; i++) {
      this.weights[i] += weights[i];
    }
  }

  public void setPicture(BufferedImage input){}
  public SigmoidCurve getCurve(){
    return activationFunction;
  }
}
