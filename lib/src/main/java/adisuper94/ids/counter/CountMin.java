package adisuper94.ids.counter;

public class CountMin {
  int[] salts;
  int k, w;
  int[][] T;

  public CountMin(int k, int w) {
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
      this.T[i][idx] += 1;
    }
  }

  public int query(String flowId) {
    int count = Integer.MAX_VALUE;
    for (int i = 0; i < this.k; i++) {
      int idx = (Math.abs(flowId.hashCode()) ^ this.salts[i]) % this.w;
      count = Math.min(this.T[i][idx], count);
    }
    return count;
  }
}
