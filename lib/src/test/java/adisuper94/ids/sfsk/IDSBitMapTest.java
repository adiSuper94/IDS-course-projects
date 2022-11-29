package adisuper94.ids.sfsk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class IDSBitMapTest {
  @Test
  void idsMapTest() {
    IDSBitMap classUnderTest = new IDSBitMap(10000);
    Set<Integer> spreadSet = new HashSet<>();
    int[] ss = { 100, 1000, 10000, 100000, 1000000 };
    String out = "";
    for (int s : ss) {
      classUnderTest.clear();
      while (true) {
        int element = (int) (Math.random() * Integer.MAX_VALUE);
        if (spreadSet.contains(element)) {
          continue;
        }
        if (spreadSet.size() >= s) {
          break;
        }
        classUnderTest.record(element);
        spreadSet.add(element);
      }
      double est = classUnderTest.getSpreadEstimate();
      int act = spreadSet.size();
      out = out + String.valueOf(act) + "\t" + String.valueOf(est) + "\n";
    }
    File outFile = new File("src/test/resources/bitmap-spread/bitmap.txt");
    FileOutputStream outputStream;
    try {
      outputStream = new FileOutputStream(outFile, false);
      outputStream.write(out.getBytes());
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void idsProbMapTest() {
    IDSBitMap classUnderTest = new IDSBitMap(10000, 0.1);
    Set<Integer> spreadSet = new HashSet<>();
    int[] ss = { 100, 1000, 10000, 100000, 1000000 };
    String out = "";
    for (int s : ss) {
      classUnderTest.clear();
      spreadSet.clear();
      while (true) {
        int element = (int) (Math.random() * Integer.MAX_VALUE);
        if (spreadSet.contains(element)) {
          continue;
        }
        if (spreadSet.size() >= s) {
          break;
        }
        classUnderTest.record(element);
        spreadSet.add(element);
      }
      double est = classUnderTest.getSpreadEstimate();
      int act = spreadSet.size();
      out = out + String.valueOf(act) + "\t" + String.valueOf(est) + "\n";
    }
    File outFile = new File("src/test/resources/bitmap-spread/prob-bitmap.txt");
    FileOutputStream outputStream;
    try {
      outputStream = new FileOutputStream(outFile, false);
      outputStream.write(out.getBytes());
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void hllTest() {
    HLLSketch classUnderTest = new HLLSketch(256, 5);
    Set<Integer> spreadSet = new HashSet<>();
    int[] ss = { 1000, 10000, 100000, 1000000 };
    String out = "";
    for (int s : ss) {
      classUnderTest.clear();
      spreadSet.clear();
      while (true) {
        int element = (int) (Math.random() * Integer.MAX_VALUE);
        if (spreadSet.contains(element)) {
          continue;
        }
        if (spreadSet.size() >= s) {
          break;
        }
        classUnderTest.record(element);
        spreadSet.add(element);
      }
      double est = classUnderTest.query();
      int act = spreadSet.size();
      out = out + String.valueOf(act) + "\t" + String.valueOf(est) + "\n";
    }
    File outFile = new File("src/test/resources/bitmap-spread/hll.txt");
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
