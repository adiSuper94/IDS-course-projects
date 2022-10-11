package adisuper94.ids.bloomfilter;

public class IDSBloomFilter {

  private int m, k;
  private boolean[] filter;
  private boolean isCounting;
  private short[] countingFilter;
  private int[] salts;

  public IDSBloomFilter(int m, int k, boolean isCounting) {
    this.m = m;
    this.k = k;

    this.isCounting = isCounting;
    if (isCounting) {
      countingFilter = new short[m];
    } else {
      filter = new boolean[m];
    }
    this.salts = new int[k];

    for (int i = 0; i < k; i++) {
      this.salts[i] = (int) (Math.random() * Integer.MAX_VALUE);
    }
  }

  public void recordFlow(int flowId) {
    int[] hashes = this.multiHash(flowId);
    for (int hash : hashes) {
      int id = hash % this.m;
      this.incrementCount(id);
    }
  }

  public boolean containsFlow(int flowId) {
    int[] hashes = this.multiHash(flowId);
    int res = this.getCount(hashes[0] % this.m);
    for (int hash : hashes) {
      int id = hash % this.m;
      res = Math.min(res, this.getCount(id));
    }
    return res > 0;
  }

  public void removeFlow(int flowId) {
    if (!this.isCounting) {
      return;
    }
    int[] hashes = this.multiHash(flowId);
    for (int hash : hashes) {
      int id = hash % this.m;
      this.decrementCount(id);
    }
  }

  private void incrementCount(int id) {
    if (this.isCounting) {
      this.countingFilter[id] += 1;
    } else {
      this.filter[id] = true;
    }
  }

  private void decrementCount(int id) {
    if (this.isCounting) {
      this.countingFilter[id] -= 1;
    } else {
      return;
    }
  }

  private int getCount(int id) {
    if (this.isCounting) {
      return this.countingFilter[id];
    } else {
      return this.filter[id] == true ? 1 : 0;
    }
  }

  private int[] multiHash(int flowId) {
    int masterHash = Integer.hashCode(flowId);
    int[] hashes = new int[this.k];
    for (int i = 0; i < this.k; i++) {
      hashes[i] = masterHash ^ this.salts[i];
    }
    return hashes;
  }
}
