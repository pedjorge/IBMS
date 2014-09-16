package IBMS;
import java.util.*;
import java.text.*;
import java.io.*;

public class Service {

  public ServiceStop stops[];
  public int id;
  public int route;

  public Service (int serviceNum) {
    this.id = serviceNum;
  }

  public Service (ServiceStop[] stops, int serviceNum) {
    this.id    = serviceNum;
    this.stops = stops;
  }

  public Service (ServiceStop[] stops, int serviceNum, int route) {
    this.id    = serviceNum;
    this.stops = stops;
    this.route = route;
  }

}


// class BusStop {

//   public String id;

//   public BusStop(String id) {
//     this.id = id;
//   }

//   public String key() {
//     return this + "";
//   }

//   // @Override
//   // public String toString() {
//   //   return StopName + " - " + AreaName;
//   // }

//   @Override
//   public String toString() {
//     return this.id;
//   }

// }