package IBMS;

import java.util.*;
import java.text.*;
import IBMS.database.*;
import java.io.*;

public class DBOperations {

  private String result;

  public DBOperations() {
    database.openBusDatabase();
  }

  public String getResult() {
    return result;
  }

  // public static ArrayList<int[]> getTimingPoints() {
    
  //   int routes[] = BusStopInfo.getRoutes();
  //   ArrayList<int[]> points = new ArrayList<int[]>;
    
  //   for (int i = 0; i < 7; i++)
  //   {
  //     points.add(TimetableInfo.getTimingPoints(routes[i]));
  //   }

  //   return points;

  // }

  public static ArrayList<ArrayList<BusService>> getRosterData() {

    try {
      String fileName= "buses.sav";
      FileInputStream fin = new FileInputStream(fileName);
      ObjectInputStream ois = new ObjectInputStream(fin);
      ArrayList<ArrayList<BusService>> AllBuses = (ArrayList<ArrayList<BusService>>) ois.readObject();
      ois.close();
      return AllBuses;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    /*

    int routes[] = BusStopInfo.getRoutes();

    ArrayList<ArrayList<BusService>> AllBuses = new ArrayList<ArrayList<BusService>>();

    // ===============================
    // Get the next monday
    // ===============================

    // Snippet adapted from coderanch.com

    Calendar today = Calendar.getInstance();  
    int daysToAdd = (Calendar.MONDAY + 7 - today.get(Calendar.DAY_OF_WEEK)) % 7;
    if (daysToAdd == 0) daysToAdd = 7;
    today.add(Calendar.DAY_OF_YEAR, daysToAdd);
    Date time = today.getTime();

    // ===============================
    // Loop through 7 days (Mon - Fri)
    // ===============================

    for (int i = 0; i < 7; i++)
    {
      Date day = new Date(time.getTime() + (i * 86400000));
      TimetableInfo.timetableKind kind = TimetableInfo.timetableKind(day);
      System.out.println("Day kind: " + kind);

      ArrayList<BusService> buses = new ArrayList<BusService>();

      // ===============================
      // Loop through all services
      // ===============================

      for (int route : routes)
      {
        int numServices = TimetableInfo.getNumberOfServices(route, kind);
        for (int j = 0; j < numServices; j++)
        {
          int serviceTimes[] = TimetableInfo.getServiceTimes(route, kind, j);

          int serviceLength = serviceTimes[serviceTimes.length - 1] - serviceTimes[0];

          // Handle any edge cases.
          if (serviceLength > 1000) {
            int k;
            for (k = 0; k < serviceTimes.length; k++)
              if (serviceTimes[k] < 1000) serviceTimes[k] += 1440;
            
            Arrays.sort(serviceTimes);
            serviceLength = serviceTimes[serviceTimes.length - 1] - serviceTimes[0];
          }

          // BusServer class handles if time is > 1440.
          buses.add(new BusService(serviceTimes[0], serviceTimes[serviceTimes.length - 1], serviceLength, route));
        }
      }

      Collections.sort(buses);
      AllBuses.add(buses);

    }

    // try {
    //   String fileName= "buses.sav";
    //   FileOutputStream fos = new FileOutputStream(fileName);
    //   ObjectOutputStream oos = new ObjectOutputStream(fos);
    //   oos.writeObject(AllBuses);
    //   oos.close();
    // } catch (Exception e) {
    //   throw new RuntimeException(e);
    // }

    return AllBuses;
    */
  }

  public Boolean requestHoliday (int startDay, int endDay, int startMonth,
            int endMonth, int startYear, int endYear, int driverNumber) {

    Date startDate, endDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    dateFormat.setLenient(false);

    // ===============================
    // Error Checking - Valid Dates?
    // ===============================

    try {
      startDate = dateFormat.parse(startDay + "/" + startMonth + "/" + startYear);
      endDate = dateFormat.parse(endDay + "/" + endMonth + "/" + endYear);
    } catch(Exception e) {
      result = "Please select valid dates!";
      return false;
    }

    if (startDate.after(endDate)) {
      result = "The start day *CANNOT* be after the ending day!";
      return false;
    }

    Date today = new Date();
    if(today.after(startDate)) {
      result = "The start day *MUST* be after today!";
      return false;
    }

    // ===============================
    // Error Checking - Is authorised?
    // ===============================

    int daysRequested = (int)((endDate.getTime() - startDate.getTime())/1000/3600/24 +1);
    int daysTaken     = DriverInfo.getHolidaysTaken(driverNumber);
    int daysLeft      = 25 - daysTaken;

    if (daysRequested > daysLeft) {
      result =  "You don't have " + daysRequested + " days left, you " +
                "only have " + daysLeft + " days left!";
      return false;
    }

    for(long dayTime = startDate.getTime(); dayTime <= endDate.getTime(); dayTime += 1000*3600*24)
    {
      Date checkDate = new Date(dayTime);
      if(DriverInfo.isAvailable(driverNumber, checkDate) == false) {
        result = "The days you request have been requested before.";
        return false;
      }
    }

    // ===============================
    // No errors! Book holiday.
    // =============================== 

    DriverInfo.setHolidaysTaken(driverNumber, (daysTaken + daysRequested));

    for(long dayTime = startDate.getTime(); dayTime <= endDate.getTime(); dayTime += 1000*3600*24)
    {
      Date theDate = new Date(dayTime);
      DriverInfo.setAvailable(driverNumber, theDate, false);
    }
    result  = "You have successfully requested " + daysRequested + " days for holiday, enjoy!\n"
            + "You have " + (25 - (daysTaken + daysRequested)) + " days left.";
    return true;
  }

}