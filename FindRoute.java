package IBMS;
import IBMS.database.*;
import IBMS.windows.BusDriver;
import IBMS.windows.Controller;
import IBMS.windows.Window;
import IBMS.windows.Passenger;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 

/**
 * A class that provides a test to find a shortest route from one hardcoded location to another
 * @author Mark Larah
 */
public class FindRoute {

  /**
   * The main method that finds the shortest route from one hardcoded bus stop to another
   */
  public static void main(String[] args) {
    DBOperations dbo = new DBOperations();
    BusGraph graph = new BusGraph();

    graph.populate(TimetableInfo.timetableKind.weekday, 400);
    String route[] = graph.findRoute("Henry Street - Glossop", "Navigation Hotel - Marple");
    for (int i = 0; i < route.length; i++)
      System.out.println(route[i]);
  }
  
}
