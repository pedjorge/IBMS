package IBMS;

import java.util.*;
import java.text.*;
import IBMS.database.*;
import java.io.*;

/**
  * A class that handles everything related to a 'node' in the IBMS system
  * @author Mark Larah
  * 
  */
public class Node {

  public BusStop info;
  public ArrayList<Edge> adjacencies;

  public int currDistance;
  public ArrayList<Integer> currPath;

  /**
    * Constructs a node given a name and its current distance
    * @param name               The name of the node
    * @param currDist           The current distance of a node                     
    */
  public Node(String name, int currDist) {
    info = new BusStop(name);
    adjacencies = new ArrayList<Edge>();

    // Should be infinity, but who's counting...
    currDistance = currDist;
    currPath = new ArrayList<Integer>();
  }

  /**
    * Constructs a node given a BusStop object
    * @param busStop            A BusStop object                  
    */
  public Node(BusStop busStop) {
    info = busStop;
    adjacencies = new ArrayList<Edge>();

    // Should be infinity, but who's counting...
    currDistance = Integer.MAX_VALUE;
    currPath = new ArrayList<Integer>();
  }

  /**
    * Returns the key
    * @return String          A string representing the key                   
    */
  public String key() {
    return info.key();
  }

}