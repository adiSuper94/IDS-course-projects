package adisuper94.ids.hashtable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class MultiHashtableTest {
  @Test
  void test() {
    MultiHashtable classUnderTest = new MultiHashtable(1000, 3);
    Map<Integer, Integer> expected = new HashMap<>();
    for (int i = 0; i < 1000; i++) {
      int flowId = (int) (Math.random() * Integer.MAX_VALUE);
      expected.put(flowId, expected.getOrDefault(flowId, 0) + 1);
      classUnderTest.recordFlow(flowId);
    }
    int actualCount = 0;
    for (int flowId : expected.keySet()) {
      int count = classUnderTest.getCount(flowId);
      if (count == expected.get(flowId)) {
        actualCount++;
      }
    }
    System.out.print(classUnderTest.getFlowCount());
    assertEquals(classUnderTest.getFlowCount(), actualCount);
  }
}
