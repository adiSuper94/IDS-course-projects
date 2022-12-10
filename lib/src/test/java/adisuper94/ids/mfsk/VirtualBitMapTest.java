package adisuper94.ids.mfsk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class VirtualBitMapTest {
  @Test
  void testVirtualBitMap() {
    int m = 500000, l = 500;
    VirtualBitMap classUnderTest = new VirtualBitMap(m, l);
    record(classUnderTest);
    query(classUnderTest);
  }

  private void record(VirtualBitMap bitMap) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("src/test/resources/mfsk/project5input.txt"));
      int n = Integer.parseInt(reader.readLine());
      String line;
      for (int i = 0; i < n; i++) {
        line = reader.readLine();
        String[] input = line.split("\t");
        String flowId = input[0].trim();
        int count = Integer.parseInt(input[1].trim());
        // System.out.println(flowId);
        Set<Integer> set = new HashSet<>();
        while (set.size() < count) {
          int e = (int) (Math.random() * Integer.MAX_VALUE);
          if (set.contains(e)) {
            continue;
          }
          set.add(e);
          bitMap.record(flowId, e);
        }
      }

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void query(VirtualBitMap bitMap) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("src/test/resources/mfsk/project5input.txt"));
      int n = Integer.parseInt(reader.readLine());
      String line;
      Map<String, Double> res = new HashMap<>();
      Map<String, Integer> ex = new HashMap<>();
      String out = "";
      for (int i = 0; i < n; i++) {
        line = reader.readLine();
        String[] input = line.split("\t");
        String flowId = input[0].trim();
        int expected = Integer.parseInt(input[1].trim());
        double actual = bitMap.query(flowId);
        res.put(flowId, actual);
        ex.put(flowId, expected);
        if (expected > 500) {
          continue;
        }
        out += String.valueOf(expected) + ", " + String.valueOf(actual) + "\n";
      }

      reader.close();
      File outFile = new File("src/test/resources/mfsk/virtualBitmap.csv");

      FileOutputStream outputStream;
      try {
        outputStream = new FileOutputStream(outFile, false);
        outputStream.write(String.valueOf(out).getBytes());
        outputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
