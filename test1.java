/*package IBMS;
import IBMS.database.*;
import IBMS.windows.BusDriver;
import IBMS.windows.Controller;
import IBMS.windows.Window;
import java.util.*;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 

public class test1 {

  private static HashMap<String, Node> Graph;

  public static void main(String[] args) {
    DBOperations dbo = new DBOperations();
    GoodDB gdb = new GoodDB();

    Graph = new HashMap<String, Node>();

    int[] routes = gdb.getRoutes();
    for (int route : routes) {

      // ==================
      // Get info from DB
      // ==================

      ArrayList<Service> services = new ArrayList<Service>();
      // System.out.println("Route: " + route);

      int stops[] = gdb.getBusStops(route);
      // for (int st : stops) System.out.print(st + " | ");
      // System.out.println("^length: " + stops.length);  
      int serviceArr[] = gdb.getServices(0, route);
      for (int i = 0; i < serviceArr.length; i++)
      {
        Service service = gdb.getService(serviceArr[i]);
        // System.out.println("Service: " + service.id);

        int serviceLength = service.stops[service.stops.length - 1].time - service.stops[0].time;
        // Handle any edge cases.
        if (serviceLength > 1000) {
          int k;
          for (k = 0; k < service.stops.length; k++)
            if (service.stops[k].time < 1000) service.stops[k].time += 1440;
          Arrays.sort(service.stops);
        }

        // for (int j = 0; j < service.stops.length; j++) {
        //   System.out.println("  Time: " + service.stops[j].time + ", Stop: " + service.stops[j].busStop);
        // }
        services.add(service);
      }

      // ==================
      // Add nodes.
      // ==================

      String previousStop = "";
      for (int i = 0; i < stops.length; i++)
      {
        BusStop Stop = createBusStop(stops[i]);
        if (Stop.key() ==  previousStop) continue;
        System.out.println("  " + Stop);

        if (!Graph.containsKey(Stop.key())) {
          Node node = createNode(Stop);
          Graph.put(Stop.key(), node);
          System.out.println("  added " + Stop.key());
        } else {
          System.out.println("  didn't add.");
        }
      }

      for (int i = 0; i < services.size(); i++)
      {
        Service service = services.get(i);
        for (int j = 1; j < service.stops.length; j++)
        { 
          int weight = service.stops[j].time - service.stops[j-1].time;
          System.out.println("stop" + service.stops[j-1].busStop);
          Node lastNode = Graph.get(Integer.toString(service.stops[j-1].busStop));
          System.out.println(lastNode.info);
          Edge connection = new Edge(Integer.toString(service.stops[j].busStop), route, service.stops[j].time, weight);
          lastNode.adjacencies.add(connection);
        }
      }



      // ArrayList<Service> services = gdb.getServices(0, route);
      // service

      //   int serviceTimes[] = TimetableInfo.getServiceTimes(route, this.day, j);

    }

  }


  private static BusStop createBusStop(int stop) {
    return new BusStop(Integer.toString(stop));
  }

  private static Node createNode(BusStop stop) {
    return new Node(stop);
  }
  
}*/