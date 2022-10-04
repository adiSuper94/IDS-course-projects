package adisuper94.ids.hashtable;

public class DLeftCuckooHashtable {
  private IDSHashtable map;

  public DLeftCuckooHashtable(int n, int k, int c) {
    if (c > 3) {
      throw new IllegalArgumentException("cuckoo level cannot be more than 2");
    }
    map = new IDSHashtable(n, k, c, true);
  }

  public int recordFlow(int flowId) {
    return map.recordFlow(flowId);
  }

  /**
   * Returns count if found, else 0.
   */
  public int getCount(int flowId) {
    return map.getCount(flowId);
  }

  public int getFlowCount() {
    return map.getFlowCount();
  }

  public String print() {
    return this.map.print();
  }
}
