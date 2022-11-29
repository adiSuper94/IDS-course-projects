package adisuper94.ids.sfsk;

import java.util.HashMap;
import java.util.Map;

public class HLLSketch {

  private class HLLRegister {
    int seed = 0;
    int w = 0;
    int regVal = -1;

    public HLLRegister(int w) {
      this.seed = (int) (Math.random() * Integer.MAX_VALUE);
      this.w = w;
      this.regVal = 0;
    }

    public void clear() {
      this.regVal = 0;
    }

    private int g(int e) {
      int val = Integer.hashCode(e) ^ this.seed;
      int rem = val;
      int div = 2;
      for (int i = 0; i < Math.pow(2, this.w); i++) {
        if (rem >= (int) Integer.MAX_VALUE / div) {
          return ++i;
        } else {
          div *= 2;
        }
      }
      return 0;
    }

    public void record(int e) {
      this.regVal = Math.max(this.regVal, this.g(e));
    }

    public int getRegVal() {
      return this.regVal;
    }
  }

  private double ALPHA = 0;
  int m = 0;
  int w = 0;
  HLLRegister[] registers;

  public HLLSketch(int m, int w) {
    this.m = m;
    this.w = w;
    registers = new HLLRegister[m];
    this.ALPHA = 0.7213 / (1 + (1.079 / this.m));
    for (int i = 0; i < m; i++) {
      registers[i] = new HLLRegister(this.w);
    }
  }

  public void record(int e) {
    registers[h(e)].record(e);
  }

  public void clear() {
    for (int i = 0; i < this.m; i++) {
      registers[i].clear();
    }
  }

  private int h(int e) {
    return Integer.hashCode(e) % this.m;
  }

  public double query() {
    double est = 0.0;
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < this.m; i++) {
      est += (1 / Math.pow(2, registers[i].getRegVal()));
      map.put(registers[i].getRegVal(), map.getOrDefault(registers[i].getRegVal(), 0) + 1);
    }
    est = this.ALPHA * Math.pow(m, 2) * Math.pow(est, -1);
    return est;
  }
}
