package adisuper94.ids.bloomfilter;

public class CountingBloomFilter {
  IDSBloomFilter filter;

  public CountingBloomFilter(int m, int k) {
    this.filter = new IDSBloomFilter(m, k, true);
  }

  public void recordFlow(int flowId) {
    this.filter.recordFlow(flowId);
  }

  public boolean containsFlow(int flowId) {
    return this.filter.containsFlow(flowId);
  }

  public void removeFlow(int flowId) {
    this.filter.removeFlow(flowId);
  }
}
