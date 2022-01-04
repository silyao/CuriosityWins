package utils.timing;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class Timing {

  private Timing() {
  }

  /**
   * Perform timing of the execution of the provided function
   * @param function A function that takes no input
   * @return the time it takes to execute the function
   */
  public static long timeIt(Supplier<Object> function) {
    long startTimestamp = System.nanoTime();

    // Execute Method
    function.get();

    long endTimestamp = System.nanoTime();
    long timeElapsed = endTimestamp - startTimestamp;

    return timeElapsed;
  }
}
