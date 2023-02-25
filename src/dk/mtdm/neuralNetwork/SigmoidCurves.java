package dk.mtdm.neuralNetwork;

import java.util.function.Function;

public class SigmoidCurves {
  static public Function<? super Float, ? extends Float> coolTanh = x -> (float) Math.tanh(x);
  static public Function<? super Float, ? extends Float> logistic = x -> (float) ((float) 1/(1+Math.pow(Math.E, -x)));
  static public Function<? super Float, ? extends Float> leakReLU = x -> Math.max(0.1f*x, x);
  static public Function<? super Float, ? extends Float> deriReLU = x -> (x >= 0f ? 1f : 0f);
  static public Function<? super Float, ? extends Float> deLeReLU = x -> (x >= 0f ? 1f : 0.01f);
  static public Function<? super Float, ? extends Float> yourGELU = x -> (float) (0.5 * x * (1 + Math.tanh(Math.sqrt(2/Math.PI)*(x+0.044715 * Math.pow(x,3)))));
}
