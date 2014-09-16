package IBMS.tests;
import IBMS.*;
import java.util.*;

import org.junit.* ;
import static org.junit.Assert.* ;

public class GoodDBTest {

  @Test
  public void test_version () {

    GoodDB gdb = new GoodDB();

    System.out.println("Testing mysql version");
    assertEquals(
      gdb.getVersion(),
      "5.5.35-0ubuntu0.12.04.2"
    );    

  }


  @Test
  public void test_array_conversion () {
    
    GoodDB gdb = new GoodDB();
    
    ArrayList<Integer> arr1 = new ArrayList<Integer>();
    arr1.add(4);
    arr1.add(5);
    arr1.add(7);

    int arr2[] = new int[3];
    arr2[0] = 4;
    arr2[1] = 5;
    arr2[2] = 7;

    System.out.println("Testing array conversion");
    assertArrayEquals(
      gdb.toIntArr(arr1),
      arr2
    );

  }



}