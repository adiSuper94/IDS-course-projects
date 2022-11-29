package adisuper94.ids.sfsk;

public class IDSBitMap {
  boolean[] arr;
  int m = -1;
  boolean prob = false;
  double p;

  public IDSBitMap(int m) {
    this.arr = new boolean[m];
    this.m = m;
  }

  public IDSBitMap(int m, double p) {
    this.prob = true;
    this.p = p;
    this.m = m;
    this.arr = new boolean[m];
  }

  public void clear() {
    for (int i = 0; i < m; i++) {
      arr[i] = false;
    }
  }

  private int h(int x) {
    return (Integer.hashCode(x) % this.m);
  }

  private int ph(int e) {
    if (Integer.hashCode(e) < Integer.MAX_VALUE * this.p) {
      return 1;
    }
    return -1;
  }

  public void record(int e) {
    if (this.prob && this.ph(e) == -1) {
      return;
    }
    int idx = this.h(e);
    this.arr[idx] = true;
  }

  public double getSpreadEstimate() {
    int u = 0;
    for (int i = 0; i < this.m; i++) {
      if (arr[i] == false) {
        u++;
      }
    }
    double v = (double) u / m;
    double est = Math.log(v) * m * -1;
    if (this.prob) {
      est = est / this.p;
    }
    return est;
  }
}
