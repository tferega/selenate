package net.selenate.client;

import akka.util.Timeout;
import com.typesafe.config.Config;

public final class C {
  private C() {}
  public static final Config config = BaseConfig.config; static {
    System.out.println(config);
    System.exit(0);
  }

  public static final class Global {
    public static final Timeout Timeouts = BaseConfig.parseTimeout(config.getString("global.timeouts"));
    public static final boolean RecordSessions = config.getBoolean("global.record-sessions");
  }

  public static final class ServerSystem {
    public static final String Name = config.getString("server-system.name");
    public static final String Host = config.getString("server-system.host");
    public static final int Port = config.getInt("server-system.port");
  }
}
