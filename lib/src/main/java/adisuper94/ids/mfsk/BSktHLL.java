package adisuper94.ids.mfsk;

import adisuper94.ids.sfsk.HLLSketch;

public class BSktHLL {
  HLLSketch[] A;
  int m = 0, k = 0, l;
  int[] R;

  public BSktHLL(int m, int k, int l) {
    this.m = m;
    this.k = k;
    this.l = l;
    A = new HLLSketch[this.m];
    R = new int[this.k];
    for (int i = 0; i < this.k; i++) {
      R[i] = (int) (Math.random() * Integer.MAX_VALUE);
    }
    for (int i = 0; i < this.m; i++) {
      A[i] = new HLLSketch(this.l, 5);
    }
  }

  public void record(String f, int e) {
    for (int i = 0; i < this.k; i++) {
      int idx = (Math.abs(f.hashCode()) ^ R[i]) % this.m;
      A[idx].record(e);
    }
  }

  public double query(String f) {
    double est = Double.MAX_VALUE;
    for (int i = 0; i < this.k; i++) {
      int idx = (Math.abs(f.hashCode()) ^ R[i]) % this.m;
      est = Math.min(A[idx].query(), est);
    }
    return est;
  }
}
