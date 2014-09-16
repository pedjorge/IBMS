package IBMS;

import java.util.*;
import java.text.*;
import IBMS.database.*;
import java.io.*;


public class Roster {

  public static void generateRoster() {

    ArrayList<ArrayList<BusService>> AllBuses = DBOperations.getRosterData();
    //ArrayList<int[]> TimingPoints             = DBOperations.getTimingPoints();


    // For each day, put the list of buses that need assignment into 'buses'
    int i;
    for (i = 0; i < 7; i++)
    {
      ArrayList<BusService> buses = AllBuses.get(i);

      System.out.println("Busses to assign:");
      for (BusService bus : buses) {
        System.out.println(bus);
      } 

      // ===============================
      // Get minumum bus drivers
      // ===============================

      int minutes = 0;
      for (BusService bus : buses) minutes += bus.length;
    
      int numDrivers = (int) Math.ceil((minutes / 60) / 10);
      numDrivers = 1;
      System.out.println("num drivers: " + numDrivers);
      
      ArrayList<Driver> drivers = new ArrayList<Driver>();
      for (int j = 0; j < numDrivers; j++) drivers.add(new Driver(j+1));

      while (true) {
        resetArrys(drivers, buses);

        System.out.println("Running roster algorithm with " + numDrivers + " drviers.");
        try {
          if (assignBusses(drivers, buses, i+1)) break;
        } catch (IOException e) {
          System.out.println("File write error!");
        }
        System.out.println("Couldn't do it in " + numDrivers + "!");
        numDrivers++;
        drivers.add(new Driver(numDrivers));
      }

    }

  }

  public static void resetArrys(ArrayList<Driver> drivers, ArrayList<BusService> buses) {
    
    for (BusService bus : buses)
      bus.assigned = false;

    for (Driver driver : drivers)
      driver.reset();

  }

  public static Boolean busesLeft(ArrayList<BusService> buses) {
    for (BusService bus : buses) {
      if (!bus.assigned) return true;
    }
    return false;
  }

  public static BusService findNextAvailableBus(Driver driver, ArrayList<BusService>  buses) {
    int i;
    for (i = 0; i < buses.size(); i++)
    {
      BusService bus = buses.get(i);
      if (bus.assigned) continue;
      if (bus.startTime < driver.getLastJobFinishedAt()) continue;

      if ((driver.minutesWorkedThisShift + bus.length) > 300) {
        if (driver.shift == 1) driver.changeShift();
        return bus;
      }

      if ((driver.minutesWorkedThisShift + bus.length) <= 300) {
        return bus;
      }
    }
    return null;
  }

  public static Driver getNextAvailableDriver(ArrayList<Driver> drivers, BusService bus) {

    for (Driver driver : drivers)
    {
      if (bus.startTime < driver.lastJobFinishedAt) continue;
      if ((driver.minutesWorkedThisShift + bus.length) > 300) {
        if (driver.shift == 1) driver.changeShift();
        return driver;
      }
      if ((driver.minutesWorkedThisShift + bus.length) > 300) continue;

      if (driver.lastJobFinishedAt <= bus.startTime) {
        return driver;
      }
    }
    return null;
  }

  public static Boolean assignBusses(ArrayList<Driver> drivers, ArrayList<BusService> buses, int dayNum) throws IOException {
    
    File file = new File("Roster_Day_" + dayNum + ".txt");
    if(!file.exists()) file.createNewFile();

    PrintWriter writer = new PrintWriter(file);

    Collections.sort(drivers);


    // OLD ALGORITHM:
    //   for (BusService bus : buses)
    //   {
    
    //     Driver driverToAssign = null;
    //     driverToAssign = getNextAvailableDriver(drivers, bus);
    //     if (driverToAssign == null) return false;
    //     System.out.println("driver " + driverToAssign.id);

    //     try {
    //       writer.println(bus + " | " + driverToAssign);
    //       driverToAssign.assignBus(bus);
    //     } catch (java.lang.NullPointerException e) {
    //       e.printStackTrace();
    //       writer.close();
    //       return false;
    //     }
    //   }
    //   writer.close();

    //   return true;    
      
    // }

     while (true) {
      Boolean assignedBusThisLoop = false; 
      for (Driver driver : drivers)
      {
        // Select next earliest unassigned bus route
        BusService busToAssign = null;          

        if(!busesLeft(buses)) {
          writer.close();
          return true;
        }

        Collections.sort(buses);
        busToAssign = findNextAvailableBus(driver, buses);

        try {
          if (busToAssign != null) {
            assignedBusThisLoop = true;
            writer.println(busToAssign + " | " + driver);
            driver.assignBus(busToAssign);
            //buses.remove(busToAssign);
          }
        } catch (java.lang.NullPointerException e) {
          e.printStackTrace();
          writer.close();
          return false;
        }

        // Not enough Bus Drivers! Try again with one more.
        if (!assignedBusThisLoop) {
          writer.close();
          return false;
        }
      }
    }
  }
}