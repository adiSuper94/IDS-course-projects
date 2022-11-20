package adisuper94.ids.counter;

import java.util.Random;

public class ActiveCounter {
  int number = 0;
  int exp = 0;
  Random rand = new Random();

  public ActiveCounter() {
  }

  public void inc() {
    if (this.number < Math.pow(2, 16) - 1) {
      if (rand.nextDouble() <= (1.0 / Math.pow(2, this.exp))) {
        this.number++;
      }
    } else {
      this.number = this.number >> 1;
      this.exp++;
    }

  }

  public int get() {
    return this.number * (int) Math.pow(2, this.exp);
  }
}
