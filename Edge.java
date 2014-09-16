package IBMS;

import java.util.*;
import java.text.*;
import IBMS.database.*;
import java.io.*;

/**
 * A class that handles everything to do with an 'edge' in a graph in the IBMS system
 * @author Mark Larah
 */
public class Edge {

  public String to;
  public int route;

  public int time;
  public int weight;

  /**
   * Constructor method to create an edge
   * @param to              Where this edge points to
   * @param route           The route this edge represents
   * @param time            The time attached to the edge
   * @param weight          The weight of the edge
   */
  public Edge(String to, int route, int time, int weight){
    this.to     = to;
    this.route  = route;
    this.time   = time;
    this.weight = weight;
  }

}