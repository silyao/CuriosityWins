package topics.multithreading.problems.producerandconsumer;

import java.util.Queue;
import java.util.Random;


/**
 * Producer class
 *
 * A class that produces "number" to the queue.
 *
 * Default time to produce: 500ms - 1500ms
 * Default total items to be produced: 10
 *
 * @author Silvester Yao
 */
public class Producer extends Thread implements Runnable {
  private static final int MAX_PRODUCTION = 10;

  private int id;
  private Queue<Integer> queue;
  private int queueCapacity;
  private int internalCoutner;
  private boolean inShutDownMode;

  private Random rng;

  public Producer (int id, Queue<Integer> queue, int capacity) {
    super();
    rng = new Random();
    this.id = id;
    this.queue = queue;
    queueCapacity = capacity;
    internalCoutner = 0;
    inShutDownMode = false;
  }

  /**
   * Mock a run time to produce a number
   * @param minWaitTime min time to wait, in milliseconds
   * @param maxWaitTime max time to wait, in milliseconds
   */
  private void randomWait(int minWaitTime, int maxWaitTime) {
    try {
      Thread.sleep(minWaitTime + rng.nextInt(maxWaitTime - minWaitTime));
    } catch (InterruptedException e) {
//      e.printStackTrace();
    }
  }

  /**
   * Core Method
   * To produce a number.
   *
   * This method competes for lock of the queue,
   * and then produce a number if queue is not at full capactiy.
   */
  private void produce() {
    synchronized (queue) {
      while (queue.size() >= queueCapacity) {
        try {
          System.out.println("Producer Thread " + id + " waits until elements on consumed from queue.");
          queue.wait();
        } catch (InterruptedException e) {
          System.out.println("Producer Thread " + id + " was awake.");
        }
      }

      int n = internalCoutner++;
      queue.offer(n);
      System.out.println("Producer Thread " + id + " producing " + n);
      queue.notifyAll();
    }
  }

  /**
   * Implements required `run` method for Runnable interface.
   * It keeps the thread producing until max production is achieved.
   */
  @Override
  public void run() {
    while (!inShutDownMode) {
      randomWait(500, 1500);
      produce();

      if (internalCoutner == MAX_PRODUCTION) {
        inShutDownMode = true;
      }
    }

    System.out.println("Producer Thread " + id + " shut down");
  }
}
