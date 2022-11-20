package adisuper94.ids.counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CounterSketchTest {
  @Test
  void test() {
    int k = 3, w = 3000;
    CounterSketch classUnderTest = new CounterSketch(k, w);
    record(classUnderTest);
    query(classUnderTest);
  }

  private void record(CounterSketch cmin) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("src/test/resources/project3input.txt"));
      int n = Integer.parseInt(reader.readLine());
      String line;
      for (int i = 0; i < n; i++) {
        line = reader.readLine();
        String[] input = line.split("\t");
        String flowId = input[0].trim();
        int count = Integer.parseInt(input[1].trim());
        // System.out.println(flowId);
        for (int j = 0; j < count; j++) {
          cmin.record(flowId);
        }
      }

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void query(CounterSketch cmin) {
    BufferedReader reader;
    int err = 0;
    try {
      reader = new BufferedReader(new FileReader("src/test/resources/project3input.txt"));
      int n = Integer.parseInt(reader.readLine());
      String line;
      Map<String, Integer> res = new HashMap<>();
      Map<String, Integer> ex = new HashMap<>();
      for (int i = 0; i < n; i++) {
        line = reader.readLine();
        String[] input = line.split("\t");
        String flowId = input[0].trim();
        int expected = Integer.parseInt(input[1].trim());
        int actual = cmin.query(flowId);
        res.put(flowId, actual);
        ex.put(flowId, expected);
        err += actual - expected;
      }
      double avgErr = err / n;
      String out = String.valueOf(avgErr) + "\n";
      for (int i = 0; i < 100; i++) {
        out = out + getHigHestCount(ex, res);
      }
      reader.close();
      File outFile = new File("src/test/resources/counter/counterSketch.txt");

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

  private String getHigHestCount(Map<String, Integer> expected, Map<String, Integer> res) {
    int max = Integer.MIN_VALUE;
    String ip = "";
    int realVal = 0;
    for (String ipp : res.keySet()) {
      if (max < res.get(ipp)) {
        ip = ipp;
        max = res.get(ipp);
        realVal = expected.get(ipp);
      }
    }
    expected.remove(ip);
    res.remove(ip);
    return ip + "\t \t" + max + "\t" + realVal + "\n";
  }
}
