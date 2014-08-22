package net.selenate.client.user;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.Optional;
import java.util.function.LongFunction;
import scala.concurrent.duration.FiniteDuration;

class Utils {
  static FiniteDuration getDuration(long timeout) {
    return FiniteDuration.create(timeout, MILLISECONDS);
  }

  static void sleep(final long millis) {
    try {
      Thread.sleep(millis);
    } catch (final InterruptedException e) {
      throw new RuntimeException("Exception during sleep!", e);
    }
  }

  static <T> Optional<T> waitForEvent(
      final LongFunction<Optional<T>> event,
      final long resolution,
      final long timeout) {
    final long end = System.currentTimeMillis() + timeout;
    return waitFor(event, resolution, end);
  }

  private static <T> Optional<T> waitFor(
      final LongFunction<Optional<T>> event,
      final long resolution,
      final long end) {
    final long current   = System.currentTimeMillis();
    final long remaining = end - current;
    if (remaining < 0) {
      return Optional.empty();
    } else {
      final Optional<T> result = event.apply(remaining);
      if (result.isPresent()) {
        return result;
      } else {
        final long sleepAmount = Math.min(resolution, remaining);
        sleep(sleepAmount);
        return waitFor(event, resolution, end);
      }
    }
  }

}
