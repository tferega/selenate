package net.selenate.common.sessions;

public class SessionDisplay {
  public static ISessionDisplay Main = new Main();
  public static ISessionDisplay FirstFree = new FirstFree();

  public static class Main implements ISessionDisplay {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
      return "SessionDisplay.Main";
    }
  }

  public static class FirstFree implements ISessionDisplay {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
      return "SessionDisplay.FirstFree";
    }
  }

  public static class Specific implements ISessionDisplay {
    private static final long serialVersionUID = 1L;

    private int num;

    public Specific(int num) {
      this.num = num;
    }

    public int getNum() {
      return num;
    }

    @Override
    public String toString() {
      return String.format("SessionDisplay.Specific(%d)", num);
    }
  }
}
