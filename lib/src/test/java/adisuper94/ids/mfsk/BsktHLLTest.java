package adisuper94.ids.mfsk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class BsktHLLTest {
  @Test
  void testBsktHLLTestp() {
    int m = 4000, l = 128, k = 3;
    BSktHLL classUnderTest = new BSktHLL(m, k, l);
    record(classUnderTest);
    query(classUnderTest);
  }

  private void record(BSktHLL bitMap) {
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

  private void query(BSktHLL bitMap) {
    BufferedReader reader;
    int n = 0;
    try {
      reader = new BufferedReader(new FileReader("src/test/resources/mfsk/project5input.txt"));
      n = Integer.parseInt(reader.readLine());
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
        out += String.valueOf(expected) + ", " + String.valueOf(actual) + "\n";
      }

      reader.close();
      File outFile = new File("src/test/resources/mfsk/Bskthll.csv");

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
    sortResult(n);
  }

  private void sortResult(int n) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("src/test/resources/mfsk/Bskthll.csv"));
      Map<Integer, List<Double>> map = new HashMap<>();
      String line = "";
      for (int i = 0; i < n; i++) {
        line = reader.readLine();
        String[] input = line.split(", ");
        int actual = Integer.parseInt(input[0].trim());
        double expected = Double.parseDouble(input[1].trim());
        List<Double> vals = map.getOrDefault(actual, new ArrayList<>());
        vals.add(expected);
        map.put(actual, vals);
      }
      List<Integer> sortedKeys = map.keySet().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
      int top = 25;
      int i = 0;
      String out = "";
      for (Integer key : sortedKeys) {
        if (i >= top) {
          break;
        }
        List<Double> vals = map.get(key);
        for (double val : vals) {
          out += String.valueOf(key) + "," + String.valueOf(val) + "\n";
          i++;
          if (i >= top) {
            break;
          }
        }
      }
      File outFile = new File("src/test/resources/mfsk/Bskthll.csv");
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
