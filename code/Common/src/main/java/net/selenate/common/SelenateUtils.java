package net.selenate.common;

import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.selenate.common.exceptions.SeEmptyArgumentListException;
import net.selenate.common.exceptions.SeNullArgumentException;

public final class SelenateUtils {
  private SelenateUtils() { }

  public static final SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd HHmmss");

  // ---------------------------------------------------------------------------
  // G U A R D Z

  public static <T> T guardNull(final T t, final String field) {
    if (t == null) throw new SeNullArgumentException(field);
    return t;
  }

  public static <T> List<T> guardEmptyList(final List<T> tList, final String field) {
    if (guardNull(tList, field).isEmpty()) throw new SeEmptyArgumentListException(field);
    return tList;
  }

  public static String guardNullOrEmpty(final String t, final String field) {
    if ("".equals(guardNull(t, field))) throw new SeEmptyArgumentListException(field);
    return t;
  }

  // ---------------------------------------------------------------------------

  public static <T> String optionalToString(final Optional<T> o) {
    if (o == null) {
      return null;
    } else {
      if (o.isPresent()) {
        return String.format("Optional(%s)", o.get());
      } else {
        return "Empty";
      }
    }
  }

  public static String byteArrToString(byte[] byteArr) {
    if (byteArr == null) {
      return null;
    } else {
      return String.format("ByteArray(%d bytes)", byteArr.length);
    }
  }

  public static <T> String setToString(Set<T> set) {
    if (set == null) {
      return null;
    } else {
      final List<T> list = new ArrayList<T>(set);
      return listToString(list, "Set(", ", ", ")");
    }
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
