package IBMS;
import java.util.*;
import java.text.*;
import java.io.*;

public class ServiceStop implements Comparable<ServiceStop> {
  public int time;
  public int busStop;
  public int route;

  public ServiceStop (int time, int busStop) {
    this.time    = time;
    this.busStop = busStop;
  }

  public ServiceStop (int time, int busStop, int route) {
    this.time    = time;
    this.busStop = busStop;
    this.route = route;
  }


  public int compareTo(ServiceStop stop) {
    return this.time - stop.time;
  }

}