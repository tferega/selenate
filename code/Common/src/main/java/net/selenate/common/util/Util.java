package net.selenate.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Util {
  private Util() { }

  public static String byteArrToString(byte[] byteArr) {
    return String.valueOf(byteArr.length);
  }

  public static <T> String setToString(Set<T> set) {
    final List<T> list = new ArrayList<T>(set);
    return listToString(list);
  }

  public static <T> String listToString(List<T> list) {
    return listToString(list, "List(", ", ", ")");
  }

  public static <T> String listToString(List<T> list, String prefix, String delimiter, String suffix) {
    if (list == null)  {
      return null;
    } else {
      String listStr = prefix;
      boolean isFirst = true;
      for (T element : list) {
        listStr += (isFirst ? "" : delimiter) + element.toString();
        isFirst = false;
      }
      listStr += suffix;

      return listStr;
    }
  }
}
