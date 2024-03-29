package adisuper94.ids.hashtable;

public class MultiHashtable {
  private IDSHashtable map;

  public MultiHashtable(int n, int k) {
    map = new IDSHashtable(n, k, 0, false);
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
