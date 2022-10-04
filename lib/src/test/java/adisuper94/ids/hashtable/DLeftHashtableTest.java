package adisuper94.ids.hashtable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DLeftHashtableTest {
  @Test
  void test() {
    DLeftHashtable classUnderTest = new DLeftHashtable(1000, 3);
    Map<Integer, Integer> expected = new HashMap<>();
    for (int i = 0; i < 1000; i++) {
      int toss = (int) (Math.random() * 20);
      int flowId = (int) (Math.random() * Integer.MAX_VALUE);
      if (toss < 1 && expected.size() > 0) {
        int id = (int) (Math.random() * expected.size());
        int count = 0;
        for (int key : expected.keySet()) {
          if (count == id) {
            flowId = key;
            break;
          }
          count++;
        }
      }
      expected.put(flowId, expected.getOrDefault(flowId, 0) + 1);
      classUnderTest.recordFlow(flowId);
    }
    int actualCount = 0;
    for (int flowId : expected.keySet()) {
      int count = classUnderTest.getCount(flowId);
      if (count != 0) {
        assertEquals(expected.get(flowId), count);
        if (count == expected.get(flowId)) {
          actualCount++;
        }
      }

    }
    String out = "flow count: " + classUnderTest.getFlowCount() + "\n" + classUnderTest.print();
    System.out.print(classUnderTest.getFlowCount());
    assertEquals(classUnderTest.getFlowCount(), actualCount);
    File outFile = new File("src/test/resources/dleft-out.txt");
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
