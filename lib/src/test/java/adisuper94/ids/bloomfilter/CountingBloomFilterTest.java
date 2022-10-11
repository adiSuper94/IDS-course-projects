package adisuper94.ids.bloomfilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class CountingBloomFilterTest {
  @Test
  void test() {
    CountingBloomFilter classUnderTest = new CountingBloomFilter(10000, 7);
    Set<Integer> setA = new HashSet<>();
    Set<Integer> setB = new HashSet<>();
    int n = 1000, add = 500, remove = 500;
    for (int i = 0; i < n; i++) {
      int flowId = (int) (Math.random() * Integer.MAX_VALUE);
      setA.add(flowId);
      setB.add(flowId);
    }

    for (int flowId : setA) {
      classUnderTest.recordFlow(flowId);
    }
    for (int i = 0; i < remove; i++) {
      int flowIdToRemove = setB.stream().skip(new Random().nextInt(setB.size())).findFirst().orElse(null);
      setB.remove(flowIdToRemove);
      classUnderTest.removeFlow(flowIdToRemove);
    }
    for (int i = 0; i < add; i++) {
      int flowId = (int) (Math.random() * Integer.MAX_VALUE);
      setB.add(flowId);
      classUnderTest.recordFlow(flowId);
    }

    int foundInSetA = 0;
    for (int flowId : setA) {
      if (classUnderTest.containsFlow(flowId)) {
        foundInSetA++;
      }
    }

    System.out.println(foundInSetA);
    File outFile = new File("src/test/resources/bloomfilter/counting-bf-out.txt");
    FileOutputStream outputStream;
    try {
      outputStream = new FileOutputStream(outFile, false);
      outputStream.write(String.valueOf(foundInSetA).getBytes());
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
