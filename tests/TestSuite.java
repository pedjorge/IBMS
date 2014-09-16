package IBMS.tests;
import IBMS.*;

import org.junit.* ;
import static org.junit.Assert.* ;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
   HolidayTest.class,
   GraphTest.class,
   BusStopTest.class,
   InteractionTest.class,
   BusStopClassTest.class,
   ServiceStopTest.class,
   GoodDBTest.class
})

public class TestSuite {   

}
