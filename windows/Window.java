package IBMS.windows;
import IBMS.*;
import IBMS.DBOperations.*;

public class Window{

  public static String version = "0.5";

  public static String driverName;
  public static int driverNumber;

  static public DBOperations db;

  public static void logoutButton () {
    System.out.println("Toodle-oo!");
    System.exit(0);
  }

  public static void setDriver(int number, String name) {
    driverName    = name;
    driverNumber  = number;
  }


}
