package dk.mtdm;

/**
 * Main
 */
public class Main {
  static int[] array1 = new int[5];
  static int[] array2 = new int[4];
  static int[] array3;
  public static void main(String[] args) {
    System.out.println("Hello world");
    array1[0] = 211;
    array1[1] = 221;
    array1[2] = 212;
    array1[3] = (int)(Math.random()*222);
    array1[4] = 221;
  
    array2[0] = (int)(Math.random()*Integer.MAX_VALUE); 
    array2[2] = 541; 
    array2[1] = 16345756; 
    array2[3] = Integer.MAX_VALUE; 
    // array2[4] = Integer.MIN_VALUE; 

    array3 = new int[Math.min(array1.length,array2.length)];
    for (int i = 0; i < array3.length; i++) {
      array3[i] = array1[i]+array2[i];
    }
  }
}