package adisuper94.ids.hashtable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CuckooHashMapTest {
  @Test
  void test() {
    CuckooHashtable classUnderTest = new CuckooHashtable(1000, 3, 2);
    Map<Integer, Integer> expected = new HashMap<>();
    for (int i = 0; i < 1000; i++) {
      int flowId = (int) (Math.random() * Integer.MAX_VALUE);
      expected.put(flowId, expected.getOrDefault(flowId, 0) + 1);
      classUnderTest.recordFlow(flowId);
    }
    int actualCount = 0;
    StringBuilder bob = new StringBuilder();
    bob.append("{");
    String delim = "";
    for (int flowId : expected.keySet()) {
      int count = classUnderTest.getCount(flowId);
      bob.append(delim + flowId + ": " + count);
      delim = ", ";
      if (count == expected.get(flowId)) {
        actualCount++;
      }
    }
    bob.append("}");
    String out = "flow count: " + classUnderTest.getFlowCount() + "\n" + bob.toString();
    System.out.print(classUnderTest.getFlowCount());
    assertEquals(classUnderTest.getFlowCount(), actualCount);
    File outFile = new File("src/test/resources/cuckoo-out.txt");
    FileOutputStream outputStream;
    try {
      outputStream = new FileOutputStream(outFile, false);
      outputStream.write(out.getBytes());
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
