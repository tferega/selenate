package net.selenate.common.actors;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownThread extends Thread {
  private static final Logger logger = LoggerFactory.getLogger(ShutdownThread.class);
  private final List<Runnable> hookList;

  private ShutdownThread() {
    this.hookList = new ArrayList<>();
  }

  public void addHandler(final Runnable handler) {
    hookList.add(handler);
  }

  public static ShutdownThread register() {
    final ShutdownThread st = new ShutdownThread();
    Runtime.getRuntime().addShutdownHook(st);
    return st;
  }

  @Override
  public void run() {
    hookList.forEach(Runnable::run);
    logger.info("HALTING");
    Runtime.getRuntime().halt(0);
  }
}
