package topics.multi.threading.problems.producer.and.consumer;

import java.util.Queue;
import java.util.Random;


/**
 * Consumer class
 *
 * A class that consumes numbers from the queue.
 * Default time to produce: 500ms - 1500ms
 *
 * Consumer gracefully shutdown once producer stopped and queue is empty.
 *
 * @author Silvester Yao
 */
public class Consumer extends Thread implements Runnable {
  private int id;
  private Random rng;
  private volatile Queue<Integer> queue;
  private boolean inShutdownMode;
  private boolean finishedConsumption;

  public Consumer (int id, Queue<Integer> queue) {
    super();
    rng = new Random();
    this.id = id;
    this.queue = queue;
    inShutdownMode = false;
    finishedConsumption = false;
  }

  public void shutDown() {
    inShutdownMode = true;
  }

  private void randomWait(int minWaitTime, int maxWaitTime) {
    try {
      Thread.sleep(minWaitTime + rng.nextInt(maxWaitTime - minWaitTime));
    } catch (InterruptedException e) {
//      e.printStackTrace();
    }
  }

  private void consume() {
    synchronized (queue) {
      while (queue.isEmpty()) {
        if (inShutdownMode) {
          finishedConsumption = true;
          return;
        }

        try {
          System.out.println("Consumer Thread " + id + " waits until elements put in queue.");
          queue.wait(500);
        } catch (InterruptedException e) {
          System.out.println("Consumer Thread " + id + " awaken after waiting for queue.");
        }
      }

      int n = queue.poll();
      System.out.println("Consumer Thread " + id + " consumed " + n);
      queue.notifyAll();
    }
  }

  @Override
  public void run() {
    while (!finishedConsumption) {
      randomWait(500, 1500);
      consume();
    }

    System.out.println("Consumer Thread " + id + " shut down.");
  }
}
