// package IBMS;
// import IBMS.database.*;
// import IBMS.windows.BusDriver;
// import IBMS.windows.Controller;
// import IBMS.windows.Window;
// import java.util.*;

// import java.awt.*; 
// import java.awt.event.*;
// import javax.swing.*; 

// public class RoutePlanner {

//   private GoodDB gdb;
//   boolean populated = false;
//   int startTime = 500;

//   public RoutePlanner () {
//     gdb = new GoodDB();
//   }

//   private HashMap<String, Node> Graph;

//   public void populate(int day) {

//     Graph = new HashMap<String, Node>();
//     ArrayList<Service> services = new ArrayList<Service>();

//     int[] routes = gdb.getRoutes();
//     for (int route : routes) {

//       // ==================
//       // Get info from DB
//       // ==================

//       int stops[] = gdb.getBusStops(route);
//       // for (int st : stops) System.out.print(st + " | ");
//       // System.out.println("^length: " + stops.length);  
//       int serviceArr[] = gdb.getServices(day, route);
//       for (int i = 0; i < serviceArr.length; i++)
//       {
//         Service service = gdb.getService(serviceArr[i]);

//         int serviceLength = service.stops[service.stops.length - 1].time - service.stops[0].time;
//         // Handle any edge cases.
//         if (serviceLength > 1000) {
//           int k;
//           for (k = 0; k < service.stops.length; k++)
//             if (service.stops[k].time < 1000) service.stops[k].time += 1440;
//           Arrays.sort(service.stops);
//         }

//         // for (int j = 0; j < service.stops.length; j++) {
//         //   System.out.println("  Time: " + service.stops[j].time + ", Stop: " + service.stops[j].busStop);
//         // }
//         services.add(new Service(service.stops, service.id, route));
//       }

//       // ==================
//       // Add nodes.
//       // ==================

//       for (int i = 0; i < stops.length; i++)
//       {
//         BusStop Stop = createBusStop(stops[i]);

//         if (!Graph.containsKey(Stop.key())) {
//           Node node = createNode(Stop);
//           Graph.put(Stop.key(), node);
//         } else {
//           // System.out.println("  didn't add.");
//         }
//       }
//     }

//     for (int i = 0; i < services.size(); i++)
//     {
//       Service service = services.get(i);
//       System.out.println(
//         "Service: " + service.id + " " +
//         "Route: " + service.route
//       );


//       for (int j = 1; j < service.stops.length; j++)
//       { 
//         int weight = service.stops[j].time - service.stops[j-1].time;
//         // System.out.println("stop" + service.stops[j-1].busStop);
//         Node lastNode = Graph.get(Integer.toString(service.stops[j-1].busStop));
//         // System.out.println(lastNode.info);
//         Edge connection = new Edge(Integer.toString(service.stops[j].busStop), service.route, service.stops[j].time, weight);
//         lastNode.adjacencies.add(connection);
//         if (lastNode.info.key().equals("808")) {
//           System.out.println("Added to node 808: to stop " + service.stops[j].busStop + " at time " + service.stops[j].time);
//         }
//       }
//     }



//       // ArrayList<Service> services = gdb.getServices(0, route);
//       // service

//       //   int serviceTimes[] = TimetableInfo.getServiceTimes(route, this.day, j);

    

//     printEdges();
//     populated = true;

//   }


//   private BusStop createBusStop(int stop) {
//     return new BusStop(Integer.toString(stop));
//   }

//   private Node createNode(BusStop stop) {
//     return new Node(stop);
//   }


//   // ================================================
//   // Node and Graph stuff
//   // ================================================

//   public void printEdges() {
//     for (Node aNode : Graph.values()) {
//       System.out.println("Working on Node - " + aNode.info);
//       for (Edge anEdge : aNode.adjacencies) {
//         System.out.println("  Adjacency to: " + anEdge.to + " at time " + anEdge.time + " on route " + anEdge.route);
//       }
//     }
//   }

//   private Node findSmallestV(Map<String, Integer> dist, HashSet<Node> Nodes) {
//     // Unfortunately, there's no better way to do this. Can't sory easily...
//     Node theNode = null;
//     for (Node aNode : Nodes) {
//       if (theNode == null)                                      theNode = aNode;
//       else if (dist.get(aNode.key()) < dist.get(theNode.key())) theNode = aNode;
//     }
//     return theNode;
//   }

//   public void findRoute (String from, String to) {

//     if (!populated) return ;

//     HashSet<Node> Q = new HashSet<Node>(Graph.values());
//     HashSet<Node> S = new LinkedHashSet<Node>();

//     HashMap<String, ServiceStop> prev = new HashMap<String, ServiceStop>();
//     HashMap<String, Integer> elapsedTime = new HashMap<String, Integer>();


//     Map<String, Integer> dist = new HashMap<String, Integer>();
//     dist.put(from, 0);

//     for (Node v : Graph.values()) {
//       elapsedTime.put(v.key(), 0);
//       if (v.key().equals(from)) continue;
//       dist.put(v.key(), 10000); 
//     }

//     while (!Q.isEmpty()) {
//       Node u = findSmallestV(dist, Q);
//       System.out.println("distance: " + dist.get(u.key()));
//       S.add(u);

//       if (u.key().equals(to)) {
//         int i = 1;
//         System.out.println("Found " + u.key() + "!");
//         System.out.println("#"+i+": " + u.key());
//         ServiceStop p = prev.get(u.key());
//         while (p != null) {
//           i++;
//           System.out.println("#"+i+": " + p.busStop + " at time " + p.time + " on route " + p.route);
//           p = prev.get(Integer.toString(p.busStop));
//         }
//         System.out.println("\nTa-Dah!");
//         return;
//       }
//       System.out.println("currNode: " + u.key());
//       int currDist = dist.get(u.key());

//       for (Edge v : u.adjacencies) {
//         int timeToWait = v.time - (startTime + currDist);
//         if (timeToWait < 0) continue;
//         int alt = currDist + v.weight + timeToWait;
//         if (dist.get(v.to) > alt) {
//           dist.put(v.to, alt);
//           ServiceStop ss = new ServiceStop(v.time, Integer.parseInt(u.key()), v.route);
//           prev.put(v.to, ss);
//           System.out.println("inserted stop:"  +ss.busStop + " at time " + ss.time + " on route " + ss.route);

//         }
//       }
//       Q.remove(u);
//     }

//   }






  
// }