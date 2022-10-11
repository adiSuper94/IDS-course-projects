package adisuper94.ids.bloomfilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class BloomFilterTest {
  @Test
  void test() {
    BloomFilter classUnderTest = new BloomFilter(10000, 7);
    Set<Integer> setA = new HashSet<>();
    Set<Integer> setB = new HashSet<>();
    int n = 1000;
    for (int i = 0; i < n; i++) {
      int flowId = (int) (Math.random() * Integer.MAX_VALUE);
      setA.add(flowId);
      flowId = (int) (Math.random() * Integer.MAX_VALUE);
      setB.add(flowId);
    }

    for (int flowId : setA) {
      classUnderTest.recordFlow(flowId);
    }
    int foundInSetA = 0;
    int foundInSetB = 0;
    for (int flowId : setA) {
      if (classUnderTest.containsFlow(flowId)) {
        foundInSetA++;
      }
    }

    for (int flowId : setB) {
      if (!setA.contains(flowId) && classUnderTest.containsFlow(flowId)) {
        foundInSetB++;
      }
    }

    System.out.println(foundInSetA);
    System.out.println(foundInSetB);

    File outFile = new File("src/test/resources/bloomfilter/bf-out.txt");
    FileOutputStream outputStream;
    try {
      outputStream = new FileOutputStream(outFile, false);
      outputStream.write((String.valueOf(foundInSetA) + "\n").getBytes());
      outputStream.write(String.valueOf(foundInSetB).getBytes());
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
