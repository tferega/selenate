package net.selenate.client;

import com.ferega.props.japi.PropsPath;
import com.ferega.props.japi.PropsLoader;

public final class C {
  private C() {}

  public static final String Branch;
  public static final String ClientHost;
  public static final String ServerHost;
  public static final String ServerPort;
  public static final String ServerPath;

  static {
    try {
      final PropsLoader props = new PropsLoader(true, new PropsPath("%user.home%", ".config", "selenate", "%branch%", "server.config"));

      Branch     = props.get("branch");
      ClientHost = props.get("client.host");
      ServerHost = props.get("server.host");
      ServerPort = props.get("server.port");
      ServerPath = props.get("server.path");
    } catch (Exception e) {
      throw new RuntimeException("An error occured while loading configuration!", e);
    }
  }
}
