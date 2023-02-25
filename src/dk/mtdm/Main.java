package dk.mtdm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import dk.mtdm.neuralNetwork.NetworkManager;


/**
 * Main
 */
public class Main {
  public static String imagePath = "MNISTDataset\\numbers\\";
  public static void main(String[] args) {
    int numberOfParents = 30;
    int worldSize = 100;
    int numberOfGenerations = 5000;
    
    NetworkManager[] networks = new NetworkManager[worldSize];
    int[] network = {40,20};
    for (int i = 0; i < networks.length; i++) {
      networks[i] = new NetworkManager(30, network, 0, 9);
    }
    for (int tv = 0; tv < numberOfGenerations; tv++) {
      System.out.println("\n\ngeneration number: " + (tv+1) + "/" + numberOfGenerations);
      float[][][][] results = new float[networks.length][][][];
      for (int i = 0; i < networks.length; i++) {
        if((i+1)%5 == 0){
          System.out.print("working: " + (i+1) + "/" + worldSize + " ");
        }
        results[i] = networks[i].calcALL();
      }
      System.out.println();
      float[] cost = new float[networks.length];
      for (int i = 0; i < networks.length; i++) {
        cost[i] = networks[i].getCost(results[i]);
      }
      for (int i = 0; i < cost.length; i++) {
        System.out.println("ID: " + i + "\tcost: " + cost[i]/30/10);
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
      for (int i = 0; i < win.length; i++) {
        networktemp[i] = networks[win[i]];
      }
      for (int i = 0; i < networktemp.length; i++) {//save parents
        networks[i] = networktemp[i];
      }
      for (int i = networktemp.length; i < networks.length; i++) { //make children
        networks[i] = networks[i-networktemp.length%numberOfParents].copy();
        networks[i].randomChange(0.5f/(float)(tv%4+1f));
      }
    }
  }

  public static int[] readSize(){
    int[] size = new int[2];
    try {
      File file = new File(Main.imagePath + "maxSize.txt");
      Scanner myReader = new Scanner(file);
      size[0] = myReader.nextInt();
      size[1] = myReader.nextInt();
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    return size;
  }
}