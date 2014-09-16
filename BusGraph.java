
package IBMS;

import java.util.*;
import java.text.*;
import IBMS.database.*;
import java.io.*;

/**
  * A class that helps plan the shortest route from one bus stop to another bus stop
  * @author Mark Larah
  */
public class BusGraph {

  boolean populated = false;

  TimetableInfo.timetableKind day;
  int time;

  /**
    * Finds the shortest route from a bus stop to another bus stop
    * @param from             The bus stop to travel from
    * @param to               The bus stop to travel to
    * @return String[]        A string array with all the bus stops in the route from one bus stop to another
    */
  public String[] findRoute (String from, String to) {

    if (!populated) {
      return new String[0];
    }
    
    HashSet<Node> Q = new HashSet<Node>(Graph.values());
    HashSet<Node> S = new LinkedHashSet<Node>();

    HashMap<String, String> prev = new HashMap<String, String>();


    Map<String, Integer> dist = new HashMap<String, Integer>();
    dist.put(from, 0);

    ArrayList<String> routeList = new ArrayList<String>();

    for (Node v : Graph.values()) {
      if (v.key().equals(from)) continue;
      dist.put(v.key(), 10000); 
    }

    while (!Q.isEmpty()) {
      Node u = findSmallestV(dist, Q);
      // System.out.println("distance: " + dist.get(u.key()));
      S.add(u);

      if (u.key().equals(to)) {
        int i = 1;
        System.out.println("Found!");
        System.out.println("#"+i+": " + u.key());

        routeList.add(u.key());

        String p = prev.get(u.key());
        while (p != null){
          i++;
          System.out.println("#"+i+": " + p);
          routeList.add(p);
          p = prev.get(p);
        }
	
        System.out.println("\nTa-Dah!");
        break;
      }
      // System.out.println("currNode: " + u.key());
      for (Edge v : u.adjacencies) {
        int newDist = getNewDist(dist.get(u.key()));
        if (dist.get(v.to) > newDist) {
          dist.put(v.to, newDist);
          prev.put(v.to, u.key());
        }
      }
      Q.remove(u);
    }

    return reverseRoute(routeList);
  }

  /**
    * Gets a new distance
    * @param oldDist    The old distance
    * @return int       An int representing the new distance calculated
    */
  public int getNewDist(int oldDist) {
    return oldDist + 1;
  }

  /**
    * Finds the smallest vertice in the graph
    * @param dist           The distance
    * @param Nodes          The hash set of nodes to search from
    * @return Node          The node with the smallest distance
    */
  public Node findSmallestV(Map<String, Integer> dist, HashSet<Node> Nodes) {
    // Unfortunately, there's no better way to do this. Can't sory easily...
    Node theNode = null;
    for (Node aNode : Nodes) {
      if (theNode == null)                                      theNode = aNode;
      else if (dist.get(aNode.key()) < dist.get(theNode.key())) theNode = aNode;
    }
    return theNode;
  }

  //
  // ================================================
  //


  private HashMap<String, Node> Graph;

  /**
    * Populates a graph
    * @param day              The timetable kind for a day
    * @param time             The time selected
    */
  public void populate(TimetableInfo.timetableKind day, int time) {

    this.day  = day;
    this.time = time;

    Graph = new HashMap<String, Node>();

    int routes[] = BusStopInfo.getRoutes();

    for (int route : routes) {

      // System.out.println("Route: " + route);
      int stops[] = BusStopInfo.getBusStops(route);
      // for (int st : stops) System.out.print(st + " | ");
      // System.out.println("^length: " + stops.length);   
      ArrayList<int[]> timingPoints = new ArrayList<int[]>();

      int numServices = TimetableInfo.getNumberOfServices(route, this.day);
      for (int j = 0; j < numServices; j++)
      {
        int serviceTimes[] = TimetableInfo.getServiceTimes(route, this.day, j);
        for (int st : serviceTimes) System.out.print(st + " | ");
        // System.out.println("^length: " + serviceTimes.length);
        int serviceLength = getServiceLength(serviceTimes);
        // Handle any edge cases.
        if (serviceLength > 1000) {
          int k;
          for (k = 0; k < serviceTimes.length; k++)
            if (serviceTimes[k] < 1000) serviceTimes[k] += 1440;
          
          Arrays.sort(serviceTimes);
        }
        timingPoints.add(serviceTimes);
      }

      String previousStop = "";

      for (int i = 0; i < stops.length; i++)
      {
        BusStop Stop = createBusStop(stops[i]);
        if (Stop.key() ==  previousStop) continue;

        // System.out.println("  " + Stop);

        if (!Graph.containsKey(Stop.key())) {
          Node node = createNode(Stop);
          Graph.put(Stop.key(), node);
          // System.out.println("  added");
        } else {
          System.out.println("  didn't add.");
        }

        if (previousStop != "") {

          Node lastNode = Graph.get(previousStop);
          Edge connection = new Edge(Stop.key(), route, 0, 1);
          lastNode.adjacencies.add(connection);
          // Graph.put(previousStop, lastNode);
        }

        previousStop = Stop.key();
        System.out.println();
      }

    }

    printEdges();
    populated = true;

  }

  /**
    * Gets the length of a service
    * @param nums         The array of services
    * @return int         An int representing the length of the service
    */
  public int getServiceLength(int[] nums) {
    return nums[nums.length - 1] - nums[0];
  }

  /**
    * Prints the edges of a graph
    */
  public void printEdges() {
    for (Node aNode : Graph.values()) {
      System.out.println("Working on Node - " + aNode.info);
      for (Edge anEdge : aNode.adjacencies) {
        System.out.println("  Adjacency to: " + anEdge.to + " on route " + anEdge.route);
      }
    }
  }

  private String[] getBusStopInfo(int busStop) {
    String fullName = BusStopInfo.getFullName(busStop);
    return fullName.split(", ");
  }

  private int getAreaCode(String area) {
    return BusStopInfo.findAreaByName(area);
  }

  private BusStop createBusStop(int stop) {
    String[] stopInfo = getBusStopInfo(stop);
    int areaCode      = getAreaCode(stopInfo[0]);
    return new BusStop(stopInfo[1], areaCode, stopInfo[0]);
  }

  private Node createNode(BusStop stop) {
    return new Node(stop);
  }
  //  public static String[] getArray(){
   //   return opOutput;
  //} 

  /**
    * Reverses the route so that it can be viewed in the correct order
    * @param revList              The shortest route found
    * @return String[]            A string array containing the reversed route
    */
  public String[] reverseRoute(ArrayList<String> revList) {
    String list[] = new String[revList.size()];
    for (int i = 0; i < revList.size(); i++) {
      list[i] = "#" + (i+1) + " " + revList.get(revList.size() - i - 1);
    }
    return list;
  }
}