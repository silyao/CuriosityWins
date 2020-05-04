package topics.multi.threading.problems.producer.and.consumer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Demo class for classic Producer Consumer problem.
 *
 * This class implements the main thread that calls
 * M producer threads,
 * N consumer threads,
 * let producer and consumer juggle the resource of the queue,
 * and finally, once all producer finishes production,
 * all consumers will finish the consumption and leave the queue empty,
 * and then gracefully shut down the program.
 *
 * @author Silvester Yao
 * 05/03/2020
 */
public class ProducerConsumerDemo {
  public static void main(String[] args) throws InterruptedException {
    Queue<Integer> queue = new LinkedList<>();
    int producerCount = 2;
    int consumerCount = 3;
    int queueCapacity = 10;

    Producer[] producers = new Producer[producerCount];
    Consumer[] consumers = new Consumer[consumerCount];

    for (int i = 0; i < producerCount; i++) {
      Producer producerThread = new Producer(i, queue, queueCapacity);
      producers[i] = producerThread;
      producerThread.start();
    }

    for (int i = 0; i < consumerCount; i++) {
      Consumer consumerThread = new Consumer(i, queue);
      consumers[i] = consumerThread;
      consumerThread.start();
    }

    // Wait for producers to finish.
    for (Producer producer : producers) {
      producer.join();
    }
    System.out.println("All Producers completed.");

    // Producer completed.
    // Consumer set to shut down mode
    for (Consumer consumer : consumers) {
      consumer.shutDown();
      consumer.join();
    }
    System.out.println("All Consumers completed.");

    System.out.println("Final Queue = [" + queueToString(queue) + "]");
    System.out.println("Demo completed.");
  }

  /**
   * A helper method to print out the contents of the queue.
   * @param queue Queue to be printed
   * @return a serialization of all contents of the queue.
   */
  private static String queueToString(Queue<Integer> queue) {
    if (queue.isEmpty()) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    Iterator<Integer> iterator = queue.iterator();
    while (iterator.hasNext()) {
      sb.append(iterator.next() + ", ");
    }

    sb.setLength(sb.length() - 2);;
    return sb.toString();
  }
}
