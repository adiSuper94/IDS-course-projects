package adisuper94.ids.hashtable;

public class DLeftHashtable {
  private IDSHashtable map;

  public DLeftHashtable(int n, int k) {
    this.map = new IDSHashtable(n, k, 0, true);
  }

  public int getCount(int flowId) {
    return this.map.getCount(flowId);
  }

  public int getFlowCount() {
    return this.map.getFlowCount();
  }

  public int recordFlow(int flowId) {
    return this.map.recordFlow(flowId);
  }

  public String print() {
    return this.map.print();
  }
}
