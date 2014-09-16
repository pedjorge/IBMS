package IBMS;
import java.util.*;
import java.lang.*;
import java.io.*;

class BusService implements Serializable, Cloneable, Comparable<BusService> { 
  int startTime;
  int endTime;
  int length;
  int route;
  Boolean assigned;

  public BusService(int start, int end, int length, int route) {
    this.startTime  = (start > 1440) ? start - 1440 : start;
    this.endTime    = (end > 1440) ? end - 1440 : end;
    this.length     = length;
    this.route      = route;
    this.assigned   = false;
  }

  public int compareTo(BusService bus) {
    return this.startTime - bus.startTime;
  }

  // @Override
  // public String toString() {
  //   return this.route + " (" + this.startTime + ")" + "(" + this.endTime + ")";
  // }
  @Override
  public String toString() {
    int h_start = this.startTime / 60;
    int m_start = this.startTime % 60;
    if (String.valueOf(m_start).length() < 2) m_start *= 10;
    int h_end = this.endTime / 60;
    int m_end = this.endTime % 60;
    if (String.valueOf(m_end).length() < 2) m_end *= 10;
    return "Route: " + this.route + " Start:" + h_start + ":" + m_start + "h," + " End:" + h_end + ":" + m_end + "h";
  }

  @Override
  public BusService clone() throws CloneNotSupportedException  {
    return (BusService)super.clone();
  }

}


class Driver implements Serializable, Cloneable, Comparable<Driver> {
  int id;
  int minutesWorkedThisShift;
  int shift;
  int lastJobFinishedAt;
  int shiftStartedAt;

  public Driver(int id) {
    this.minutesWorkedThisShift   = 0;
    this.shift                    = 1;
    this.lastJobFinishedAt        = 0;
    this.shiftStartedAt           = 0;
    this.id                       = id;
  }

  public void reset() {
    this.minutesWorkedThisShift   = 0;
    this.shift                    = 1;
    this.lastJobFinishedAt        = 0;
    this.shiftStartedAt           = 0;
  }

  public void changeShift () {
    this.shift = 2;
    this.lastJobFinishedAt += 60;
    this.minutesWorkedThisShift += 60;
  }

  public int getLastJobFinishedAt () {
    return this.lastJobFinishedAt;
  }

  public void assignBus (BusService bus) {
    if (bus == null) {
      System.out.println("Error! Bus to assign is null!");
      return;
    }
    bus.assigned = true;

    this.lastJobFinishedAt       = bus.endTime;
    this.minutesWorkedThisShift += bus.length;

    if (this.shiftStartedAt == 0) 
      this.shiftStartedAt = bus.startTime;

    System.out.println("Assigning bus route " + bus.route + " at time " + bus.startTime + " to Driver " + this.id);

  }

  public int compareTo(Driver aDriver) {
    return this.lastJobFinishedAt - aDriver.lastJobFinishedAt;
  }

  @Override
  public String toString() {
    return "" + this.id;
  }

  public int getHole () {
    return 300 - minutesWorkedThisShift;
  }

  @Override
  public Driver clone() throws CloneNotSupportedException  {
    return (Driver)super.clone();
  }

}