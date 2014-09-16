package IBMS.tests;
import IBMS.*;

import org.junit.* ;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.JUnitCore;

import static org.junit.Assert.* ;

public class RunTests {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(TestSuite.class);
      for (Failure failure : result.getFailures()) {
         System.out.println("Failure: " + failure.toString());
      }
      System.out.println();
      if (result.wasSuccessful())
        System.out.println("Tests Successful");
      else 
        System.out.println("Tests Unsuccessful");
   }
}