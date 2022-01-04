package topics.list.problem.performancecomparison;

import java.util.Comparator;
import java.util.List;
import java.util.stream.LongStream;
import utils.list.ListUtil;
import utils.timing.Timing;


/**
 * A Demo on iteration over an array list.
 * Operation performed during iteration: Max
 */
public class ArrayListIterationDemoOnMaxOperation {

  public static void main(String[] args) {

    // List volume
    int volume = 1000000;

    // Repetition
    int rep = 100;

    // Initialize Array List with volume
    List<Integer> list = ListUtil.buildNaturalNumberArrayList(volume);

    // For Index Iteration time
    double avgForIndexIterationRunTime =
        1.0 * LongStream.range(0, rep).map(index -> Timing.timeIt(() -> forIndexIteration(list))).sum() / rep;
    System.out.println("For Index Iteration Average Time: " + avgForIndexIterationRunTime + "ns");

    // Foreach Loop Iteration time
    double avgForeachLoopIterationRunTime =
        1.0 * LongStream.range(0, rep).map(index -> Timing.timeIt(() -> forEachLoopIteration(list))).sum() / rep;
    System.out.println("Foreach Loop Iteration Average Time: " + avgForeachLoopIterationRunTime + "ns");

    // Stream Iteration time
    double avgStreamIterationRunTime =
        1.0 * LongStream.range(0, rep).map(index -> Timing.timeIt(() -> streamIteration(list))).sum() / rep;
    System.out.println("Stream Iteration Average Time: " + avgStreamIterationRunTime + "ns");

    // Parallel Stream Iteration time
    double avgParallelStreamIterationRunTime =
        1.0 * LongStream.range(0, rep).map(index -> Timing.timeIt(() -> parallelStreamIteration(list))).sum() / rep;
    System.out.println("Parallel Stream Iteration Average Time: " + avgParallelStreamIterationRunTime + "ns");
  }

  /**
   * Perform iteration on the provided list using the following common for loop
   * for (int i = 0; i < list.size(); i++) {
   *   // Per Item logic
   *   // In this example, we return the max of all elements in the list
   * }
   *
   * @param list the list to iterate on
   * @return the max of all elements in the list
   */
  private static Integer forIndexIteration(List<Integer> list) {
    int max = 0;

    for (int i = 0; i < list.size(); i++) {
      max = Math.max(max, list.get(i));
    }

    return max;
  }

  /**
   * Perform iteration on the provided list using Foreach Loop
   * for (Integer integer: list) {
   *   // Per Item logic
   *   // In this example, we return the max of all elements in the list
   * }
   *
   * @param list the list to iterate on
   * @return the max of all elements in the list
   */
  private static Integer forEachLoopIteration(List<Integer> list) {
    int max = 0;

    for (Integer integer: list) {
      max = Math.max(max, integer);
    }

    return max;
  }

  /**
   * Perform iteration on the provided list using {@link java.util.stream.Stream} from Java 8
   * list.foreach(lambda function)
   * In this lambda function, we return the max of all elements in the list
   *
   * @param list the list to iterate on
   * @return the max of all elements in the list
   */
  private static Integer streamIteration(List<Integer> list) {
    return list.stream().max(Comparator.comparingInt(i -> i)).get();
  }

  /**
   * Perform iteration on the provided list using {@link java.util.stream.Stream} from Java 8 with parallelism
   * list.parallelStream().forEach(lambda function)
   * In this lambda function, we return the max of all elements in the list
   *
   * @param list the list to iterate on
   * @return the max of all elements in the list
   */
  private static Integer parallelStreamIteration(List<Integer> list) {
    return list.parallelStream().max(Comparator.comparingInt(i -> i)).get();
  }
}
