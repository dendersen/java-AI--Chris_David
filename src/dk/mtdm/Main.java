package dk.mtdm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import dk.mtdm.neuralNetwork.MultiNetworkManager;


/**
 * Main
 */
public class Main {
  final public static String imagePath = "MNISTDataset\\numbers\\new";
  final public static String imagePathGet = "MNISTDataset\\numbers\\";
  private static MultiNetworkManager ais;
  public static void main(String[] args) {
    Thread.currentThread().setPriority(1);
    int[] networkSize = {25,45};
    ais = new MultiNetworkManager(750, networkSize, 0, 9, 200, 20);
    for (int i = 0; i < 5000; i++) {
      System.out.println("generation:" + (i+1) + "/" + 5000);
      ais.runAll(true);
      ais.getCost(15);
      ais.trainRand(15, 0.5f/(i+1)/4,0.5f,true);
    }
    return;
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