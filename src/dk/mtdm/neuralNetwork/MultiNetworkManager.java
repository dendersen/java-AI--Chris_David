package dk.mtdm.neuralNetwork;

public class MultiNetworkManager {
  NetworkManager[] networks;
  float[][][][] results;
  int reports;
  int numberOfPictures;
  float[] cost;
  public MultiNetworkManager(int testIDEnd, int[] networkSize, int minNumber, int maxNumber, int numberOfNetworks, int reports){
    this.numberOfPictures = testIDEnd;
    this.reports = reports;
    this.networks = new NetworkManager[numberOfNetworks];
    this.results = new float[numberOfNetworks][][][];
    for (int i = 0; i < numberOfNetworks; i++) {
      networks[i] = new NetworkManager(testIDEnd, networkSize, minNumber, maxNumber,this,i);
    }
  }
  
  public float[][][][] runAll(){
    for (int i = 0; i < networks.length; i++) {
      networks[i].start();
      if((i+1)%5 == 0){
        System.out.print("started: " + (i+1) + "/" + networks.length + " ");
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
        if (maxFinishCount <= finishCount){
          maxFinishCount = finishCount;
          if(maxFinishCount%this.reports == 0){
            System.out.print("completed: " + maxFinishCount + " ");
          }
        }
      }
    }
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

  public float[] getCost(boolean print){
    cost = new float[networks.length];
    for (int i = 0; i < networks.length; i++) {
      cost[i] = networks[i].getCost(results[i]);
    }
    if(print){
      for (int i = 0; i < cost.length; i++) {
        System.out.println("ID: " + i + "\tcost: " + cost[i]/numberOfPictures/10);
      }
    }
    return cost;
  }

  public void trainRand(int numberOfParents, float change, boolean print){
    if(cost == null){
      getCost(false);
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
    if(print){
      System.out.println("bestScore: " + (cost[win[0]]/numberOfPictures/10));
    }
    for (int i = 0; i < win.length; i++) {
      networktemp[i] = networks[win[i]];
    }
    for (int i = 0; i < networktemp.length; i++) {//save parents
      networks[i] = networktemp[i].copy();
    }
    for (int i = networktemp.length; i < networks.length; i++) { //make children
      networks[i] = networks[i-networktemp.length%numberOfParents].copy();
      networks[i].randomChange(change);
    }
    for (int j = 0; j < networks.length; j++) {
      networks[j].setID(j);
    }
    this.results = new float[this.networks.length][][][];
  }
}
