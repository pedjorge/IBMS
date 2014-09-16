package IBMS;
import IBMS.*;

import java.util.*;
import java.text.*;
import IBMS.database.*;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;

/**
  * A class that handles the real-time simulation of a bus for the 'passenger' in the IBMS system
  * @author Audrey Leow Hui-Li
  * @author Pedro Jorge
  * 
  */
public class Interaction 
{
  static boolean first = false;
  static GoodDB gdb;

  /**
    * Starts the real-time simulation
    * @param chosenArea               The chosen area for the bus from the UI
    * @param chosenBusStop            The chosen bus stop for the bus from the UI
    * @param day                      The chosen day of the week from the UI
    * @param hour                     The chosen hour from the time from the UI
    * @param min                      The chosen minutes from the time from the UI
    * @throw Exception
    */
  public static void startInteraction (String chosenArea, String chosenBusStop, String day, int hour, int min) throws Exception
  {

    gdb = new GoodDB();

    System.out.println("Area: " + chosenArea + " / Bus Stop: " + chosenBusStop + " / Day: " + day);

    if (first) System.out.println("\nSystem UPDATE\n");
    database.openBusDatabase();

    Calendar cal = Calendar.getInstance();

    TimetableInfo.timetableKind tk = getTk(day);

    // ====================================================
    // Arrival times at bus stops
    //=====================================================

    int[] busStopIDs = BusStopNumber.getBusStopID(chosenArea, chosenBusStop);
    BusID[] busStopID = new BusID[busStopIDs.length];
    boolean feasible = true;
    //Loop through all the bus stop IDs 
    for (int i = 0; i < busStopIDs.length; i++)
    {
      //Get an array of routes for each bus stop ID
      int[] routes = BusStopInfo.getRoutes(busStopIDs[i]);
      //Loop through each route 
      for (int j = 0; j < routes.length; j++)
      {
        // System.out.println(routes[j] + " ROUTE");
        if (BusStopInfo.isTimingPointOnRoute(busStopIDs[i], routes[j])) 
        {
          // System.out.println("Can do it!");
          int[] services = gdb.getServices(1 , routes[j]);
          //Array to store arrival time and the service
          BusClass[] buses = new BusClass[services.length];
          for (int l=0; l<services.length; l++) 
          {
            Service current_service = gdb.getService(services[l]);
            for(int x = 0; x < current_service.stops.length; x++) 
            {
              if(getCondition(current_service, x, busStopIDs))
              {
                buses[l] = new BusClass();
                buses[l].arrival_time = current_service.stops[x].time;  //SOURCE OF PROBLEM!!
                buses[l].service = services[l];
                buses[l].name = BusStopInfo.getRouteName(routes[j]);
               // System.out.println("Name: " + buses[l].name + " Service: " + buses[l].service + " Time: " + buses[l].arrival_time);
                break;
              } // if
            } // for
          } // for
          busStopID[i] = new BusID(routes, services, buses);
        } // if
        else { feasible = false; break; }
      } // for
    } // for

    if (feasible) { System.out.println("INITIALISING!"); run(busStopID, cal, chosenArea, chosenBusStop, day, hour, min); }
    else System.out.println("NO BUSES DUE TO ARRIVE!");
  } // startInteraction

  /**
    * Gets the exact bus Stop time
    * @param current_service            The current service
    * @param index                      The exact service now
    * @param busStopIDs                 An array of bus stop IDs
    * @return boolean
    */
  public static boolean getCondition(Service current_service, int index, int[] busStopIDs)
  {
    boolean correct = false;
    for (int i = 0; i < busStopIDs.length; i++) {
      if (current_service.stops[index].busStop == busStopIDs[i])
        correct = true;
    }
    return correct;
  }

  /**
    * Gets the timetablekind given a day
    * @param day                                String representation of a day
    * @return TimetableInfo.timetableKind
    */
  public static TimetableInfo.timetableKind getTk(String day) {

    if ((day == "Monday") || (day == "Tuesday") || (day == "Wednesday") || (day == "Thursday") || (day == "Friday"))
      return TimetableInfo.timetableKind.weekday;
    else if (day == "Saturday")
      return TimetableInfo.timetableKind.saturday;
    else
      return TimetableInfo.timetableKind.sunday;
  }

  /**
    * Gets the day kind for the selected week day
    * @param day                          Day of the week selected
    * @return int
    */
  public static int getDay(String day) {

    if ((day == "Monday") || (day == "Tuesday") || (day == "Wednesday") || (day == "Thursday") || (day == "Friday"))
      return 0;
    else if (day == "Saturday")
      return 1;
    else
      return 2;
  }
  
  //=========================================================
  // Actually running the simulation
  //=========================================================

  /**
    * Runs the simulation
    * @param busStopID                An array of BusID objects
    * @param cal                      A Calendar object 
    * @param chosenArea               The chosen area for the bus from the UI
    * @param chosenBusStop            The chosen bus stop for the bus from the UI
    * @param day                      The chosen day of the week from the UI
    * @param hour                     The chosen hour from the time from the UI
    * @param min                      The chosen minutes from the time from the UI
    * @throw Exception
    */
  public static void run (BusID[] busStopID, Calendar cal, String chosenArea, String chosenBusStop, String day, int hour, int min) throws Exception
  {
    
    ArrayList copies = new ArrayList();

    int dayWeek = getDayWeek(day);
    cal.set(Calendar.DAY_OF_WEEK, dayWeek);
    cal.set(Calendar.HOUR_OF_DAY, hour);
    cal.set(Calendar.MINUTE, min);

    boolean done = false;
    while (!done) 
    {
      Thread.sleep(1000); 

      // Displaying time
      SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
      String formatted = format1.format(cal.getTime());
      System.out.println("\nIncoming Buses        " + formatted);
      System.out.println("----------------------------");
      
      for (int i = 0; i < busStopID.length; i++ ) {

        Random random_number = new Random();

        // Delay
        boolean delay = Math.random() < 0.5;
        int bus_delay = random_number.nextInt(busStopID[i].buses.length); 
        int delayed_time = random_number.nextInt(60); //number of minutes delayed

        // Cancel
        boolean cancel = Math.random() < 0.05;
        int bus_cancel = random_number.nextInt(busStopID[i].buses.length);
        
        for (int k = 0; k < busStopID[i].buses.length; k++) {
          String[] date1 = formatted.split(":");
          int current = (Integer.parseInt(date1[0]) * 60) + Integer.parseInt(date1[1]);
          int difference = busStopID[i].buses[k].arrival_time - current;

          if (delay == true && bus_delay == k && busStopID[i].buses[k].delay == false && delayed_time > 5 && busStopID[i].buses[k].cancel == false && difference > 0 && difference < 60)
          {
            cal.add(Calendar.MINUTE, (busStopID[i].buses[k].arrival_time - current + delayed_time));
            String formatted2 = format1.format(cal.getTime());
            cal.add(Calendar.MINUTE, -(busStopID[i].buses[k].arrival_time - current + delayed_time));

            int hours = busStopID[i].buses[k].arrival_time / 60; //since both are ints, you get an int
            int minutes = busStopID[i].buses[k].arrival_time % 60;
            String correct = String.format("%d:%02d", hours, minutes);

            System.out.println("Bus " + busStopID[i].buses[k].name + "                        Delayed! " + " Delay: " + delayed_time + " mins (Will arrive at: " +  formatted2 + ")");
            showDelayExcuses(busStopID[i].buses[k].name, delayed_time, formatted2);
            busStopID[i].buses[k].arrival_time += delayed_time;
            busStopID[i].buses[k].delay = true;
          }
          else if (cancel == true && bus_cancel == k && busStopID[i].buses[k].cancel == false && difference > 0 && difference < 120)
          {
            int hours = busStopID[i].buses[k].arrival_time / 60; //since both are ints, you get an int
            int minutes = busStopID[i].buses[k].arrival_time % 60;
            String correct = String.format("%d:%02d", hours, minutes);

            System.out.println("Bus " + busStopID[i].buses[k].name + "                       Supposed to arrive at: " + correct + "                " + " Cancelled! ");
            showCancelExcuses(busStopID[i].buses[k].name, correct); 
            busStopID[i].buses[k].arrival_time = -1;
            busStopID[i].buses[k].cancel = true;
            busStopID[i].buses[k].delay = true;
          }
          else
          {
            if (difference > 0 && difference < 10 && !copies.contains(busStopID[i].buses[k].service))
            {
              copies.add(busStopID[i].buses[k].service);
              System.out.println("Bus " + busStopID[i].buses[k].name + "                " + difference + "min");

            } 
            else if (difference == 0 && !copies.contains(busStopID[i].buses[k].service))
            {
              copies.add(busStopID[i].buses[k].service);
              System.out.println("Bus " + busStopID[i].buses[k].name + "             Arrived");
              busStopID[i].buses[k].cancel = true;
              busStopID[i].buses[k].arrival_time = -1;
            } // else if
          }//else
        } // for
      } // for

      copies.clear();
      cal.add(Calendar.MINUTE, 1); // adds one hour
      if (cal.get(Calendar.HOUR_OF_DAY) == 1 && cal.get(Calendar.MINUTE) == 30 && cal.get(Calendar.DAY_OF_WEEK) != dayWeek){ done = true; first = true; } 
    } // while

    String dayOfWeek = getDayWeek(cal.get(Calendar.DAY_OF_WEEK));
    int hourDay = cal.get(Calendar.HOUR_OF_DAY);
    int minDay = cal.get(Calendar.MINUTE);
    //System.out.println(dayOfWeek + " " + hourDay + " " + minDay);
    startInteraction (chosenArea, chosenBusStop, dayOfWeek, 1, 30);
  } // run

  /**
    * Gets an int representing the day of the week
    * @param day          The string representation of the day of the week
    * @return int         The int representation of the day of the week
    */
  public static int getDayWeek(String day) {

    int dayWeek;

    if (day.equals("Monday"))
      dayWeek = 2;
    else if (day.equals("Tuesday"))  
      dayWeek = 3;
    else if (day.equals("Wednesday"))  
      dayWeek = 4;
    else if (day.equals("Thursday"))  
      dayWeek = 5;
    else if (day.equals("Friday"))  
      dayWeek = 6;
    else if (day.equals("Saturday"))  
      dayWeek = 7;
    else  
      dayWeek = 1;

    return dayWeek;
  } // getDayWeek

  /**
    * Gets the String representation of the day of the week
    * @param day              An int representation of the day of the week
    * @return String          The String representation of the day of the week
    */
  public static String getDayWeek(int day) {

    String dayWeek;

    if (day == 2)
      dayWeek = "Monday";
    else if (day == 3)  
      dayWeek = "Tuesday";
    else if (day == 4)  
      dayWeek = "Wednesday";
    else if (day == 5)  
      dayWeek = "Thursday";
    else if (day == 6)  
      dayWeek = "Friday";
    else if (day == 7)  
      dayWeek = "Saturday";
    else  
      dayWeek = "Sunday";

    return dayWeek;
  } // getDayWeek

  /**
    * Chooses a random excuse to display if a delay occurs
    * @param name               The name of the bus delayed
    * @param min                The number of minutes the bus is being delayed
    * @param newTime            The new time at which this bus is going to arrive
    */
  public static void showDelayExcuses(String name, int min, String newTime) {
    Random delay_excuses = new Random();
    int d_excuses_no = delay_excuses.nextInt(7);   

    switch(d_excuses_no) {
      case 0:
        System.out.println("\nBus " + name + " has been delayed for " + min + " mins due to an infected exhaust pipe");
        System.out.println("Don't worry. This bus will arrive at " + newTime + " at this bus stop\n");
        break;
      case 1:
        System.out.println("\nBus " + name + " has been delayed for " + min + " mins because it developed sentience and has vowed revenge on the human race.");
        System.out.println("Don't worry. This bus will arrive at " + newTime + " at this bus stop\n");
        break;
      case 2:
        System.out.println("\nBus " + name + " has been delayed for " + min + " mins because it decided it actually wants to be an actor in the west end, and is currently appearing as one of the giraffes in the Lion King.");
        System.out.println("Don't worry. This bus will arrive at " + newTime + " at this bus stop\n");
        break;
      case 3:
        System.out.println("\nBus " + name + " has been delayed for " + min + " mins as it has broken 88MPH, and is currently stuck somewhere in the wild west having run out of nuclear rods.");
        System.out.println("Don't worry. This bus will arrive at " + newTime + " at this bus stop\n");
        break;
      case 4:
        System.out.println("\nBus " + name + " has been delayed for " + min + " mins as it is depressed, and is currently being given a motivational pep talk by its driver.");
        System.out.println("Don't worry. This bus will arrive at " + newTime + " at this bus stop\n");
        break;
      case 5:
        System.out.println("\nA black hole has appeared within the bus, and the bus is now scattered across 11 dimensions.");
        System.out.println("Therefore, bus " + name + " has been delayed for " + min + " mins");
        System.out.println("Don't worry. This bus will arrive at " + newTime + " at this bus stop\n");
        break;
      case 6:
        System.out.println("\nThe bus " + name + " has been delayed for " + min + " mins because it has developed a superiority complex and wishes to be a limo. It refuses to pick up ordinary passengers like you.");
        System.out.println("Don't worry. This bus will arrive at " + newTime + " at this bus stop\n");
        break;
      case 7:
        System.out.println("\nThe bus " + name + " is currently appearing in the MasterChef semi-finals, and is running late on its haddock dish.");
        System.out.println("Therefore, this bus has been delayed for " + min + " mins");
        System.out.println("Don't worry. This bus will arrive at " + newTime + " at this bus stop\n");
        break; 
      default:
        System.out.println("\nBus " + name + "has been delayed for " + min + " mins due to an unforseen circumstance");
        System.out.println("Don't worry. This bus will arrive at " + newTime + " at this bus stop\n");
        break;
    }
  }//showDelayExcuses

  /**
    * Chooses a random excuse to display if a cancellation occurs
    * @param name               The name of the bus cancelled
    * @param correctTime        The correct time at which this bus was supposed to arrive
    */
  public static void showCancelExcuses(String name, String correctTime) {
    Random cancel_excuses = new Random();
    int c_excuses_no = cancel_excuses.nextInt(7);   

    switch(c_excuses_no) {
      case 0:
        System.out.println("\nBus " + name + " has been cancelled due to an infected exhaust pipe\n");
        break;
      case 1:
        System.out.println("\nBus " + name + " has been cancelled because it developed sentience and has vowed revenge on the human race.\n");
        break;
      case 2:
        System.out.println("\nBus " + name + " has been cancelled because it decided it actually wants to be an actor in the west end, and is currently appearing as one of the giraffes in the Lion King.\n");
        break;
      case 3:
        System.out.println("\nBus " + name + " has been cancelled as it has broken 88MPH, and is currently stuck somewhere in the wild west having run out of nuclear rods.\n");
        break;
      case 4:
        System.out.println("\nBus " + name + " has been cancelled as it is depressed, and is currently being given a motivational pep talk by its driver.\n");
        break;
      case 5:
        System.out.println("\nA black hole has appeared within the bus, and the bus is now scattered across 11 dimensions.");
        System.out.println("Therefore, bus " + name + " has been cancelled\n");
        break;
      case 6:
        System.out.println("\nThe bus " + name + " has been cancelled because it has developed a superiority complex and wishes to be a limo. It refuses to pick up ordinary passengers like you.\n");
        break;
      case 7:
        System.out.println("\nThe bus " + name + " is currently appearing in the MasterChef semi-finals, and is running late on its haddock dish.");
        System.out.println("Therefore, this bus has been cancelled\n");
        break; 
      default:
        System.out.println("\nBus " + name + "has been cancelled due to an unforseen circumstance\n");
        break;
    }
  }//showCancelExcuses

} // Interaction