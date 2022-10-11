package adisuper94.ids.bloomfilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class CodedBloomFilterTest {
  @Test
  void test() {
    int g = 7, k = 7, n = 1000, m = 30000;
    CodedBloomFilter classUnderTest = new CodedBloomFilter(g, m, k);
    Set<Integer> set = new HashSet<>();
    List<Set<Integer>> sets = new ArrayList<>();
    for (int j = 0; j < g; j++) {
      sets.add(new HashSet<Integer>());
    }
    // Add 7 * 1000 unique element filter
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < g; j++) {
        int flowId = (int) (Math.random() * Integer.MAX_VALUE);
        while (set.contains(flowId)) {
          flowId = (int) (Math.random() * Integer.MAX_VALUE);
        }
        set.add(flowId);
        sets.get(j).add(flowId);
        classUnderTest.recordFlow(flowId, j);
      }
    }

    int correctLookupCount = 0;
    int notFoundCount = 0;
    for (int flowId : set) {
      int actual = classUnderTest.containsFlow(flowId);
      int expected = -1;
      for (int j = 0; j < g; j++) {
        if (sets.get(j).contains(flowId)) {
          expected = j;
          break;
        }
      }
      if (actual == expected) {
        correctLookupCount++;
      }
      if (actual == -1) {
        notFoundCount++;
      }
    }
    int correctPossibleCount = 0;
    for (int flowId : set) {
      Set<Integer> possibilities = classUnderTest.containsFlowIn(flowId);
      int expected = -1;
      for (int j = 0; j < g; j++) {
        if (sets.get(j).contains(flowId)) {
          expected = j;
          break;
        }
      }
      if (possibilities.contains(expected)) {
        correctPossibleCount++;
      }
    }

    File outFile = new File("src/test/resources/bloomfilter/coded-bf-classifiction-out.txt");
    FileOutputStream outputStream;
    File outFile2 = new File("src/test/resources/bloomfilter/coded-bf-overall-membership-out.txt");
    FileOutputStream outputStream2;
    try {
      outputStream = new FileOutputStream(outFile, false);
      outputStream.write((String.valueOf(correctLookupCount) + "\n").getBytes());
      outputStream.close();
      outputStream2 = new FileOutputStream(outFile2, false);
      outputStream2.write((String.valueOf(correctPossibleCount) + "\n").getBytes());
      outputStream2.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
