package IBMS;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoodDB {

  static Connection con = null;
  static Statement st = null;
  static ResultSet rs = null;
  static String url = "jdbc:mysql://fbfiddle.com:3306/mbyx9ml2";
  static String user = "mbyx9ml2";
  static String password = "fishwife";

  public GoodDB () {
    try {
      con = DriverManager.getConnection(url, user, password);
    } catch (SQLException ex) { log(ex); }
  }

  private static void log (SQLException ex) {
    Logger lgr = Logger.getLogger(GoodDB.class.getName());
    lgr.log(Level.SEVERE, ex.getMessage(), ex);
  }

  public static int[] getRoutes() {
    ArrayList<Integer> routes = new ArrayList<Integer>();
    try {
      st = con.createStatement();
      rs = st.executeQuery("SELECT * FROM route ORDER BY name DESC");
      while (rs.next())
      {
        routes.add(Integer.parseInt(rs.getString(1)));
      }
    } catch (SQLException ex) { log(ex);
    } finally { return toIntArr(routes); }
  }

  public static int[] getServices(int dayKind, int route) {
    ArrayList<Integer> services = new ArrayList<Integer>();
    try {
      st = con.createStatement();
      rs = st.executeQuery(
        "SELECT s.service_id, s.daily_timetable, d.kind, d.route " +
        "FROM service as s " +
        "LEFT JOIN daily_timetable as d " +
        "ON s.daily_timetable = d.daily_timetable_id " +
        "WHERE kind=" + dayKind + " AND route=" + route 
      );
      while (rs.next())
      {
        services.add(Integer.parseInt(rs.getString(1)));
      }
    } catch (SQLException ex) { log(ex);
    } finally { return toIntArr(services); }
  }

  public static int[] getBusStops(int route) {
    ArrayList<Integer> stops = new ArrayList<Integer>();
    try {
      st = con.createStatement();
      rs = st.executeQuery(
        "SELECT bus_stop FROM path " +
        "WHERE route = " + route + " " +
        "ORDER BY sequence ASC"
      );
      while (rs.next())
      {
        stops.add(Integer.parseInt(rs.getString(1)));
      }
    } catch (SQLException ex) { log(ex);
    } finally { return toIntArr(stops); }
  }

  public static Service getService(int service) {
    ArrayList<ServiceStop> stops = new ArrayList<ServiceStop>();
    try {
      st = con.createStatement();
      rs = st.executeQuery(
        "SELECT time, timing_point FROM timetable_line " +
        "WHERE service = " + service + " " +
        "ORDER BY time ASC"
      );
      while (rs.next())
      {
        stops.add(new ServiceStop(
          Integer.parseInt(rs.getString(1)),
          Integer.parseInt(rs.getString(2))
        ));
      }
    } catch (SQLException ex) { log(ex);
    } finally { 
      return new Service(toArr(stops), service);
    }
  }


  public static String getVersion() {
    String output = "";
    try {
      st = con.createStatement();
      rs = st.executeQuery(
        "SELECT VERSION()"
      );
      while (rs.next())
      {
        output += rs.getString(1);
      }
    } catch (SQLException ex) { log(ex);
    } finally { 
      return output;
    }
  }  


  public static int[] toIntArr(ArrayList<Integer> ints)
  {
    int[] intArr = new int[ints.size()];
    for (int i=0; i < intArr.length; i++)
      intArr[i] = ints.get(i).intValue();
    return intArr;
  }

  public static ServiceStop[] toArr(ArrayList<ServiceStop> stops)
  {
    ServiceStop[] stopArr = new ServiceStop[stops.size()];
    for (int i = 0; i < stopArr.length; i++)
      stopArr[i] = (ServiceStop) stops.get(i);
    return stopArr;
  }

}