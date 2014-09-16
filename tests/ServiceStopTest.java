package IBMS.tests;
import IBMS.*;
import java.util.*;

import org.junit.* ;
import static org.junit.Assert.* ;

public class ServiceStopTest {

  @Test
  public void compare_service_stops () {


    ServiceStop ss1 = new ServiceStop(400, 812);
    ServiceStop ss2 = new ServiceStop(500, 813);

    System.out.println("Testing the ordering of service stops");

    assertTrue(ss1.compareTo(ss2) < 0);    

  }

}