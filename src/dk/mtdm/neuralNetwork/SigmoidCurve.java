package dk.mtdm.neuralNetwork;

import java.util.function.Function;

public class SigmoidCurve {
  Function<? super Float, ? extends Float> normal;
  Function<? super Float, ? extends Float> derivitive;
  public SigmoidCurve(Function<? super Float, ? extends Float> standard,Function<? super Float, ? extends Float> derivitive){
    normal = standard;
    this.derivitive = derivitive;
  }
  public float apply(float x){
    return normal.apply(x);
  }
  public float derivitive(float x){
    return derivitive.apply(x);
  }
}
