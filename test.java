package IBMS;
import IBMS.database.*;
import IBMS.windows.BusDriver;
import IBMS.windows.Controller;
import IBMS.windows.Window;

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*; 

// Create a simple GUI window
public class test {

  private static String  driverName;
  private static int     driverNumber;
  
  public static void main(String[] args) {
    DBOperations dbo = new DBOperations();
    Roster.generateRoster();
  }
  
}
