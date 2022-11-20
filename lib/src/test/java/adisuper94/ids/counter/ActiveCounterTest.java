package adisuper94.ids.counter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ActiveCounterTest {
  @Test
  void test() {
    ActiveCounter classUnderTest = new ActiveCounter();
    for (int i = 0; i < 1000000; i++) {
      classUnderTest.inc();
    }
    int res = classUnderTest.get();
    File outFile = new File("src/test/resources/counter/activeCounter.txt");

    FileOutputStream outputStream;
    try {
      outputStream = new FileOutputStream(outFile, false);
      outputStream.write(String.valueOf(String.valueOf(res)).getBytes());
      outputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
