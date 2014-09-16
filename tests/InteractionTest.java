package IBMS.tests;
import IBMS.*;
import IBMS.database.*;

import org.junit.* ;
import static org.junit.Assert.* ;

public class InteractionTest {

  @Test
  public void test_tk1 () {
    System.out.println("Getting Timetable Kind");
    assertEquals(Interaction.getTk("Wednesday"), TimetableInfo.timetableKind.weekday);
  }

  @Test
  public void test_tk2 () {
    System.out.println("Getting Timetable Kind");
    assertEquals(Interaction.getTk("Saturday"), TimetableInfo.timetableKind.saturday);
  }

  @Test
  public void test_condition () {
    System.out.println("Testing Service Condition");

    ServiceStop testStops[] = new ServiceStop[2];
    testStops[0] = new ServiceStop(400, 811);
    testStops[1] = new ServiceStop(500, 814);


    Service testService = new Service(testStops, 61000, 68);

    int intArr[] = new int[2];
    intArr[0] = 811;
    intArr[1] = 814;
    assertTrue(Interaction.getCondition(testService, 0, intArr));
  }

  @Test
  public void test_day_of_week () {
    System.out.println("Testing day getter");
    assertEquals(Interaction.getDayWeek(6), "Friday");
  }

}