package IBMS.tests;
import IBMS.*;
import java.util.*;

import org.junit.* ;
import static org.junit.Assert.* ;

public class BusStopClassTest {

  @Test
  public void newBusStop () {

    int expected[] = new int[] {775, 786};

    System.out.println("Making new bus stop");

    BusStop busStop = new BusStop(
      "Mark's super test bus stop",
      42,
      "Manchester"
    );

    assertEquals(
      busStop.key(),
      "Mark's super test bus stop - Manchester"
    );    

  }

}