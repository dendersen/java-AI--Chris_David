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
    int[] networkSize = {};
    ais = new MultiNetworkManager(750, networkSize, 0, 9, 40, 10);
    for (int i = 0; i < 40; i++) {
      System.out.println("generation:" + (i+1) + "/" + 40);
      ais.runAll();
      ais.getCost(false);
      ais.trainRand(10, 0.5f/(i+1)*2,true);
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