package dk.mtdm.neuralNetwork;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import dk.mtdm.Main;

public class NetworkManager extends Thread {
  private int highestID;
  private Node[][] network;
  private int minNumber;
  private int maxNumber;
  private float[][][] results;
  public boolean calculating = false;
  private MultiNetworkManager parent;
  private int ID;
  private SigmoidCurve normalFunktion;
  private SigmoidCurve outputFunktion;
  private float[][][][][] z;
  /**
   * 
   * @param testIDend highest ID to be tested (can be understood as testsize) (exlusive)
   * @param networkSize the number of neurons at each layer E.x [5, 21, ...] where the layers are [layer 0, layer 1, ...]
   * @param minNumber the lowest number to be tested (inclusive)
   * @param maxNumber the highest number to be tested (inclusive)
   */
  public NetworkManager(int testIDEnd, int[] networkSize, int minNumber, int maxNumber, MultiNetworkManager resultLoad, int ID, SigmoidCurve StandardNodeMath, SigmoidCurve outputNodeMath){
    highestID = testIDEnd;
    this.minNumber = minNumber;
    this.maxNumber = maxNumber;
    this.parent = resultLoad;
    this.ID = ID;
    this.normalFunktion = StandardNodeMath;
    this.outputFunktion = outputNodeMath;

    //intialize array
    {
      network = new Node[networkSize.length+2][];
      for (int i = 1; i < network.length-1; i++) {
        network[i] = new Node[networkSize[i-1]];
      }
    }
    //initialize inputNodes
    {
      int[] size = Main.readSize();
      network[0] = new Node[size[0]*size[1]];
      for (int i = 0; i < network[0].length; i++) {
        network[0][i] = new InputNode(null, i%size[0], (i-i%size[0])/size[0]);
      }
    }
    //initialize nodes
    for (int i = 1; i < network.length-1; i++) {
      for (int j = 0; j < network[i].length; j++) {
        
        //initialize weights
        float[] weights = new float[network[i-1].length];
        for (int w = 0; w < weights.length; w++) {
          weights[w] = RandomWeight();
        }
        //create node
        try {
          this.network[i][j] = new Node(RandomBias(),weights,network[i-1],StandardNodeMath);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    //initialize output nodes
    {
      network[network.length-1] = new Node[maxNumber-minNumber+1];
      for (int i = 0; i < network[network.length-1].length; i++) {
        //initialize weights
        float[] weights = new float[network[network.length-2].length];
        for (int w = 0; w < weights.length; w++) {
          weights[w] = RandomWeight();
        }
        //create node
        try {
          this.network[network.length-1][i] = new Node(RandomBias(),weights,network[network.length-2],outputNodeMath);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    results = new float[10][this.highestID][this.maxNumber - this.minNumber + 1];
  }

  private float RandomBias() {
    return ((float) Math.random()*2-1)/4;
  }
  private float RandomWeight() {
    return ((float) Math.random()*2-1)/3;
  }
  public float[] calc(int answer, int ID){
    z[answer][ID] = new float[network.length][][];
    for (int i = 1; i < network.length; i++) {
      z[answer][ID][i] = new float[network[i].length][];
      for (int j = 0; j < network[i].length; j++) {
        this.z[answer][ID][i][j] = this.network[i][j].calc();
      }
    }
    //get value from last layer
    float[] out = new float[network[network.length-1].length];
    for (int i = 0; i < out.length; i++) {
      out[i] = network[network.length-1][i].getValue();
    }
    return out;
  }

  public void setDataPoint(BufferedImage Data){
    for (int i = 0; i < network[0].length; i++) {
      network[0][i].setPicture(Data);
    }
  }

  @Override
  public void run(){
    calculating = true;
    z = new float[maxNumber-minNumber+1][highestID][][][];
    for (int i = this.minNumber; i <= this.maxNumber; i++) {
      for (int j = 1; j < highestID; j++) {
        setDataPoint(imageReader(i, j));
        this.results[i][j] = calc(i,j);
      }
    }
    if(parent != null){
      parent.saveResult(this.ID, this.results);
    }
    calculating = false;
  }

  public BufferedImage imageReader(int number, int ID){
    String path = Main.imagePath + number + "\\" + number + "_" + ID + ".jpg";
    try {
      File file = new File(path);
      return ImageIO.read(file);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }
/**
 * 
 * @param result [chocen number][ID of picture][result from network]
 */
  public void stochasticGradientDescent(float eta){
    

    float[][] biases = new float[network.length][];
    float[][][] weights = new float[network.length][][];
    for (int i = 0; i < network.length; i++) {
      biases[i] = new float[network[i].length];
      for (int j = 0; j < network[i].length; j++) {
        biases[i][j] = network[i][j].getBias();
      }
    }
    for (int i = 0; i < network.length; i++) {
      weights[i] = new float[network[i].length][];
      for (int j = 0; j < network[i].length; j++) {
        weights[i][j] = network[i][j].getWeights();
      }
    }
    for (int i = minNumber; i < highestID; i++) {
      for (int j = 0; j < this.highestID; j++) {
        backPropagation(i,j);
      }
    }
  }
  private void backPropagation(int answer,int inputID){
    
  }
    //run backpropagation on all dataPieces
    
    //save weight change and bias change 
    
    //gem alle node aktiverings niveauer
    //gem også i zs som er det samme som activations men før activation funktion
    
    //impliment magical number
    
    //
  

  public synchronized float[][][] getResult(){
    return this.results;
  }

  public float getCost(float[][][] result){
    float totalCost = 0f;
    for (int i = minNumber; i < maxNumber-minNumber+1; i++) {
      for (int j = 0; j < highestID; j++) {
        for (int l = minNumber; l < maxNumber-minNumber+1; l++) {
          if(l == i){
            totalCost += (float) Math.pow(result[i][j][l]-1,2);
          }else{
            totalCost += (float) Math.pow(result[i][j][l],2);
          }
        }
      }
    }
    return 1f/((float)(((maxNumber-minNumber+1))*highestID))*totalCost;
  }

  public int getCorrect(){
    int correct = 0;
    for (int i = minNumber; i < maxNumber-minNumber+1; i++) {
      for (int j = 0; j < highestID; j++) {
        int highest = minNumber;
        for (int l = minNumber+1; l < maxNumber-minNumber+1; l++) {
          if(results[i][j][l] > results[i][j][highest]){
            highest = l;
          }
        }
        if(highest == i){
          correct++;
        }
      }
    }
    return correct;
  }

  public float[] deriCost(float[] guess, float[] answer){
    float[] out = new float[guess.length];
    for (int i = 0; i < out.length; i++) {
      out[i] = guess[i] - answer[i];
    }
    return out;
  }

  public void randomChange(float width, float chance){
    for (int i = 1; i < network.length; i++) {
      for (int j = 0; j < network[i].length; j++) {
        if(Math.random() < chance){
          network[i][j].modBias((float)(Math.random()*2-1)*width);
        }
        float[] weightChange = new float[network[i][j].getWeightLength()];
          for (int l = 0; l < network[i][j].getWeightLength(); l++) {
          if(Math.random() < chance){
            weightChange[l] = (float)(Math.random()*2-1)*width;
          }else{
            weightChange[l] = 0f;
          }
        }
        try {
          network[i][j].modWeights(weightChange);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void setAllWeights(float[][][] weights){
    for (int i = 1; i < network.length; i++) {
      for (int j = 0; j < network[i].length; j++) {
        try {
          network[i][j].setWeights(weights[i-1][j]);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
  public float[][][] getAllWeights(){
    float[][][] weights = new float[network.length-1][][];
    for (int i = 1; i < network.length; i++) {
      weights[i-1] = new float[network[i].length][];
      for (int j = 0; j < network[i].length; j++) {
        try {
          weights[i-1][j] =network[i][j].getWeights();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return weights;
  }

  public void setAllBias(float[][] bias){
    for (int i = 1; i < network.length; i++) {
      for (int j = 0; j < network[i].length; j++) {
        network[i][j].setBias(bias[i][j]);
      }
    }
  }
  public float[][] getAllBias(){
    float[][] bias = new float[network.length][];
    for (int i = 1; i < network.length; i++) {
      bias[i] = new float[network[i].length];
      for (int j = 0; j < network[i].length; j++) {
        bias[i][j] = network[i][j].getBias();
      }
    }
    return bias;
  }

  public NetworkManager copy(){
    int[] networkSize = new int[network.length-2];
    for (int i = 0; i < networkSize.length; i++) {
      networkSize[i] = network[i+1].length;
    }
    NetworkManager NetworkNetwork = new NetworkManager(highestID,networkSize,minNumber,maxNumber,parent,ID,normalFunktion,outputFunktion);
    NetworkNetwork.setAllBias(getAllBias());
    NetworkNetwork.setAllWeights(getAllWeights());
    return NetworkNetwork;
  }
  public void setID(int ID){
    this.ID = ID;
  }
}
