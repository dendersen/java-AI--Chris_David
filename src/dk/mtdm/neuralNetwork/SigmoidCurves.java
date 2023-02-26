package dk.mtdm.neuralNetwork;

import java.util.function.Function;

public class SigmoidCurves {
  static private Function<? super Float, ? extends Float> coolTanh = x -> (float) Math.tanh(x);
  static private Function<? super Float, ? extends Float> deriTanh = x -> (float) (Math.pow(Math.tanh(x),2));
  static private Function<? super Float, ? extends Float> logistic = x -> (float) ((float) 1/(1+Math.pow(Math.E, -x)));
  static private Function<? super Float, ? extends Float> derilogi = x -> (float) logistic.apply(x)*(1-logistic.apply(x));
  static private Function<? super Float, ? extends Float> leakReLU = x -> Math.max(0.1f*x, x);
  static private Function<? super Float, ? extends Float> deLeReLU = x -> (x >= 0f ? 1f : 0.01f);

  static public SigmoidCurve Tanh = new SigmoidCurve(coolTanh,deriTanh);
  static public SigmoidCurve logi = new SigmoidCurve(logistic,derilogi);
  static public SigmoidCurve leak = new SigmoidCurve(leakReLU, deLeReLU);
}
