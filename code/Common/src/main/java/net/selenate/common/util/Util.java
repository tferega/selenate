package net.selenate.common.util;

import java.util.List;

public final class Util {
  private Util() { }

  public static <T> String simpleListToString(List<T> list) {
    return listToString(list, "[", ":", "]");
  }

  public static <T> String multilineListToString(List<T> list) {
    return listToString(list, "{", " | ", "}");
  }

  public static <T> String listToString(List<T> list, String prefix, String delimiter, String suffix) {
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
