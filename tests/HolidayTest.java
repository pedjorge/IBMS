package IBMS.tests;
import IBMS.*;

import org.junit.* ;
import static org.junit.Assert.* ;

public class HolidayTest {

  @Test
  public void invalid_date () {
    System.out.println("Testing trying to book a holiday with an invalid date");
    DBOperations dbo = new DBOperations();
    assertFalse(dbo.requestHoliday(
      40, 41,
      50, 51,
      2050, 2061,
      2018
    ));    
  }

  @Test
  public void inconsistent_dates () {
    System.out.println(
      "Testing trying to book a holiday with end date sooner than start date");
    DBOperations dbo = new DBOperations();
    assertFalse(dbo.requestHoliday(
      20, 18,
      5, 5,
      2015, 2015,
      2018
    ));    
  }

  @Test
  public void outdated_date () {
    System.out.println(
      "Testing trying to book a holiday in the past");
    DBOperations dbo = new DBOperations();
    assertFalse(dbo.requestHoliday(
      20, 21,
      5, 5,
      2010, 2011,
      2018
    ));    
  }

  @Test
  public void book_holiday () {
    System.out.println(
      "Testing trying to book a valid holiday!");
    DBOperations dbo = new DBOperations();
    assertTrue(dbo.requestHoliday(
      22, 23,
      12, 12,
      2014, 2014,
      2032
    ));    
  }


}