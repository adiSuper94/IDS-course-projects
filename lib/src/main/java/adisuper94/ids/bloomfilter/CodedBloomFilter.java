package adisuper94.ids.bloomfilter;

import java.util.HashSet;
import java.util.Set;

public class CodedBloomFilter {
  private int g, m, k, filterSize;

  private BloomFilter[] filters;

  public CodedBloomFilter(int g, int m, int k) {
    this.g = g;
    this.m = m;
    this.k = k;
    this.filterSize = (int) (Math.log(g + 1) / Math.log(2));
    this.filters = new BloomFilter[filterSize];
    for (int i = 0; i < filterSize; i++) {
      this.filters[i] = new BloomFilter(this.m, this.k);
    }
  }

  public void recordFlow(int flowId, int setId) {
    String code = this.getCode(setId);
    for (int i = 0; i < filterSize; i++) {
      if (code.charAt(i) == '0') {
        continue;
      }
      this.filters[i].recordFlow(flowId);
    }
  }

  public int containsFlow(int flowId) {
    Set<Integer> res = this.containsFlowIn(flowId);
    return res.stream().findAny().orElse(-1);
  }

  public Set<Integer> containsFlowIn(int flowId) {
    Set<Integer> result = new HashSet<>();
    for (int setId = 0; setId < this.g; setId++) {
      String code = this.getCode(setId);
      boolean containedInSet = true;
      for (int i = 0; i < filterSize; i++) {
        if (code.charAt(i) == '0') {
          continue;
        }
        containedInSet = containedInSet && this.filters[i].containsFlow(flowId);
        if (containedInSet) {
          result.add(setId);
        }
      }
    }
    return result;
  }

  private String getCode(int setId) {
    if (setId >= this.g) {
      System.out.println("enter setId value <=" + this.g);
      return "";
    }
    String code = Integer.toBinaryString(setId + 1);
    if (code.length() > this.filterSize) {
      System.out.println("Oh God");
      return "";
    } else if (code.length() < this.filterSize) {
      int diff = filterSize - code.length();
      StringBuilder bob = new StringBuilder();
      for (int i = 0; i < diff; i++) {
        bob.append("0");
      }
      bob.append(code);
      code = bob.toString();
    }
    return code;
  }
}
