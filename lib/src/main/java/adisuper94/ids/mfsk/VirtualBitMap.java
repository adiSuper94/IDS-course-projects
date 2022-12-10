package adisuper94.ids.mfsk;

import java.util.HashSet;
import java.util.Set;

public class VirtualBitMap {
  boolean[] B;
  int[] R;
  int m = 0, l = 0;

  public VirtualBitMap(int m, int l) {
    this.m = m;
    this.l = l;
    B = new boolean[this.m];
    R = new int[this.l];
    this.setR();
  }

  private void setR() {
    Set<Integer> set = new HashSet<>();
    while (set.size() < this.l) {
      int r = (int) (Math.random() * Integer.MAX_VALUE);
      if (set.contains(r)) {
        continue;
      }
      R[set.size()] = r;
      set.add(r);
    }
  }

  public void clear() {
    for (int i = 0; i < this.m; i++) {
      this.B[i] = false;
    }
    this.setR();
  }

  private int h(int e) {
    return Integer.hashCode(e);
  }

  public void record(String f, int e) {
    int index = Math.abs(h(f.hashCode() ^ R[h(e) % this.l])) % this.m;
    B[index] = true;
  }

  public double query(String f) {
    double Vb = 0, Vf = 0, est = 0;
    for (int i = 0; i < this.m; i++) {
      if (this.B[i] == false) {
        Vb++;
      }
    }
    Vb = Vb / this.m;
    for (int i = 0; i < this.l; i++) {
      int index = Math.abs(h(f.hashCode() ^ R[i])) % this.m;
      if (this.B[index] == false) {
        Vf++;
      }
    }
    Vf = Vf / this.l;
    est = this.l * Math.log(Vb) - this.l * Math.log(Vf);
    return est;
  }
}
