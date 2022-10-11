package adisuper94.ids.bloomfilter;

public class BloomFilter {
  IDSBloomFilter filter;

  public BloomFilter(int m, int k) {
    this.filter = new IDSBloomFilter(m, k, false);
  }

  public void recordFlow(int flowId) {
    this.filter.recordFlow(flowId);
  }

  public boolean containsFlow(int flowId) {
    return this.filter.containsFlow(flowId);
  }
}
