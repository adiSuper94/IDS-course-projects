package adisuper94.ids.counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CounterSketch {
  int[] salts;
  int k, w;
  int[][] T;

  public CounterSketch(int k, int w) {
    this.k = k;
    this.w = w;
    salts = new int[k];
    for (int i = 0; i < k; i++) {
      this.salts[i] = (int) (Math.random() * Integer.MAX_VALUE);
    }
    T = new int[k][w];
  }

  public void record(String flowId) {
    for (int i = 0; i < this.k; i++) {
      int idx = (Math.abs(flowId.hashCode()) ^ this.salts[i]) % this.w;
      boolean MSB = idx >>> 30 == 1;
      if (MSB) {
        this.T[i][idx] += 1;
      } else {
        this.T[i][idx] -= 1;
      }
    }
  }

  public int query(String flowId) {
    List<Integer> vals = new ArrayList<>();
    for (int i = 0; i < this.k; i++) {
      int idx = (Math.abs(flowId.hashCode()) ^ this.salts[i]) % this.w;
      boolean MSB = idx >>> 30 == 1;
      int val = this.T[i][idx];
      if (!MSB) {
        val *= -1;
      }
      vals.add(val);
    }
    Collections.sort(vals, Comparator.naturalOrder());
    return vals.get(k / 2);
  }
}
