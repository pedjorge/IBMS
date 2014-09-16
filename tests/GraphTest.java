package IBMS.tests;
import IBMS.*;
import java.util.*;

import org.junit.* ;
import static org.junit.Assert.* ;

public class GraphTest {

  @Test
  public void test_unpopulated () {

    BusGraph BG = new BusGraph();
    String emptyArr[] = new String[0];
    System.out.println("Testing uninitialised graph");
    assertEquals(BG.findRoute("a", "b"), emptyArr);

  }

  @Test
  public void test_array_reverse () {

    ArrayList<String> testList = new ArrayList<String>();
    testList.add("one");
    testList.add("two");
    testList.add("three");

    String expected[] = new String[3];
    expected[0] = "#1 three";
    expected[1] = "#2 two";
    expected[2] = "#3 one";

    BusGraph BG = new BusGraph();
    System.out.println("Testing reverse array");
    assertEquals(BG.reverseRoute(testList), expected);    
  }


  @Test
  public void new_dist () {

    BusGraph BG = new BusGraph();
    System.out.println("Testing new distance");
    assertEquals(BG.getNewDist(15), 16);

  }

  @Test
  public void get_min_v () {

    HashSet<Node> S = new LinkedHashSet<Node>();
    S.add(new Node("two", 2));
    S.add(new Node("hundred", 100));
    S.add(new Node("three", 3));
    S.add(new Node("one", 1));

    Map<String, Integer> dist = new HashMap<String, Integer>();
    for (Node n : S)
      dist.put(n.key(), n.currDistance);

    BusGraph BG = new BusGraph();
    System.out.println("Selecting V with min value.");

    Node n = BG.findSmallestV(dist, S);

    assertEquals(n.currDistance, 1);

  }

  @Test
  public void service_length () {

    int nums[] = new int[4];
    nums[0] = 6;
    nums[1] = 42;
    nums[2] = 9000;
    nums[3] = 12340;

    BusGraph BG = new BusGraph();
    System.out.println("Testing service length");

    assertEquals(BG.getServiceLength(nums), 12334);

  }




}