package utils.list;

import java.util.ArrayList;


public class ListUtil {

  private ListUtil() {
  }

  public static ArrayList<Integer> buildNaturalNumberArrayList(int size) {
    ArrayList<Integer> list = new ArrayList<>(size);

    for (int i = 0; i < size; i++) {
      list.add(i);
    }

    return list;
  }
}
