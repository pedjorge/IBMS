
package IBMS;

import java.util.*;
import java.text.*;
import IBMS.database.*;
import java.io.*;

/**
  * A class that handles everything related to a 'BusStop' in the IBMS system
  * @author Mark Larah
  * 
  */
public class BusStop {

  public String StopName;
  public String AreaName;
  public int    AreaCode;

  /**
    * Constructs a BusStop given its name, area code and area
    * @param name               The name of the bus stop
    * @param areaCode           The area code of the bus stop
    * @param area               The area of the bus stop
    */
  public BusStop(String name, int areaCode, String area) {
    StopName = name;
    AreaCode = areaCode;
    AreaName = area;
  }

  /**
    * Constructs a BusStop given its name
    * @param name               The name of the bus stop
    */
  public BusStop(String name) {
    StopName = name;
    AreaName = "";
  }

  /**
    * Returns the key
    * @return String        A string representing the key
    */
  public String key() {
    return this + "";
  }

  /**
    * The toString method for this class
    * @return String        The string after the conversion
    */
  @Override
  public String toString() {
    return StopName + " - " + AreaName;
  }

}
