package dk.mtdm.neuralNetwork;

import java.io.IOException;

public class MultiNetworkManager {
  NetworkManager[] networks;
  float[][][][] results;
  int reports;
  int numberOfPictures;
  float[] cost;
  float[] topRange = new float[2];
  public MultiNetworkManager(int testIDEnd, int[] networkSize, int minNumber, int maxNumber, int numberOfNetworks, int reports){
    this.topRange[0] = Float.MAX_VALUE;
    this.topRange[1] = Float.MAX_VALUE;
    this.numberOfPictures = testIDEnd;
    this.reports = reports;
    this.networks = new NetworkManager[numberOfNetworks];
    this.results = new float[numberOfNetworks][][][];
    for (int i = 0; i < numberOfNetworks; i++) {
      networks[i] = new NetworkManager(testIDEnd, networkSize, minNumber, maxNumber,this,i,SigmoidCurves.Tanh,SigmoidCurves.logi);
    }
  }
  
  public float[][][][] runAll(boolean clear){
    for (int i = 0; i < networks.length; i++) {
      networks[i].start();
      if((i+1)%reports == 0){
        System.out.print("started: " + (i+1) + "/" + networks.length + "           \r");
      }
    }
    int maxFinishCount = 0;
    int finishCount = 0;
    while(maxFinishCount < networks.length){
      finishCount = 0;
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {}
      for (int j = 0; j < networks.length; j++) {
        if(results[j] == null){
          continue;
        }else{
          finishCount++;
        }
        if (maxFinishCount < finishCount){
          maxFinishCount = finishCount;
          if(maxFinishCount%this.reports == 0){
            System.out.print("completed: " + maxFinishCount + "/" + networks.length + "                   \r");
          }
        }
      }
    }
    if(clear){
      try {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } catch (InterruptedException | IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    System.out.println();
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for (int i = 0; i < networks.length; i++) {
      networks[i].interrupt();
    }
    return this.results;
  }

  public synchronized void saveResult(int ID, float[][][] result){
    this.results[ID] = result;
  }

  public float[] getCost(int print){
    cost = new float[networks.length];
    for (int i = 0; i < networks.length; i++) {
      cost[i] = networks[i].getCost(results[i]);
    }
    if(print>0){
      System.out.println();
      for (int i = 0; i < cost.length && i < print; i++) {
        System.out.println("ID: " + i + "\tcost: " + cost[i]);
      }
    }
    return cost;
  }

  public void trainRand(int numberOfParents, float change,float chance, boolean print){
    if(cost == null){
      getCost(0);
    }
    NetworkManager[] networktemp = new NetworkManager[numberOfParents];
    int[] win = new int[numberOfParents];
    for (int i = 0; i < win.length; i++) {
      win[i] = -1;
    }
    for (int i = 0; i < networktemp.length; i++) {
      int best = -1;
      for (int j = 0; j < networks.length; j++) {
        boolean dead = false;
        for (int j2 = 0; j2 < win.length; j2++) {
          if(win[j2] == j){
            dead = true;
            break;
          }
        }
        try {
          if(cost[j] <= cost[best] && !dead){
            best = j;
          }
        } catch (Exception e) {
          if(!dead){
            best = j;
          }
        }
      }
      win[i] = best;
    }
    if(cost[win[0]] < topRange[0] && print){
      System.out.println("improve best:  " + -(cost[win[0]]-topRange[0]));
      topRange[0] = cost[win[0]];
    }
    if(cost[win[win.length-1]] < topRange[1] && print){
      System.out.println("improve worst: " + -(cost[win[win.length-1]]-topRange[1]));
      topRange[1] = cost[win[win.length-1]];
    }
    for (int i = 0; i < win.length; i++) {
      networktemp[i] = networks[win[i]];
    }
    for (int i = 0; i < networktemp.length; i++) {//save parents
      networks[i] = networktemp[i].copy();
    }
    for (int i = networktemp.length; i < networks.length; i++) { //make children
      networks[i] = networks[i-networktemp.length%numberOfParents].copy();
      networks[i].randomChange(change,chance);
    }
    for (int j = 0; j < networks.length; j++) {
      networks[j].setID(j);
    }
    this.results = new float[this.networks.length][][][];
    if(print){
      System.out.println("bestScore: " + (cost[win[0]]));
    }
  }
}
