package IBMS.tests;
import IBMS.*;
import java.util.*;

import org.junit.* ;
import static org.junit.Assert.* ;

public class BusStopTest {

  @Test
  public void testBusStop1 () {

    int expected[] = new int[] {775, 786};

    System.out.println("Testing bus stop picker");
    try {
      assertArrayEquals(
        BusStopNumber.getBusStopID("Marple", "Norfolk Arms"),
        expected
      );    
    } catch (java.lang.Exception e) {
      System.out.println(e);
    }
  }


  @Test
  public void testBusStop2 () {

    int expected[] = new int[] {791, 816};

    System.out.println("Testing bus stop picker");
    try {
      assertArrayEquals (
        BusStopNumber.getBusStopID("Glossop", "Grouse Inn"), 
        expected);    
    } catch (java.lang.Exception e) {
      System.out.println(e);

    }  
  }



}