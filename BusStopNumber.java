package IBMS;
import IBMS.*;

public class BusStopNumber
{
  public static int[] getBusStopID (String area, String name) throws Exception
  {
    int[] bus_stop_IDs = null;


    if (area == "Stockport") {
            if (name.equals("Bus Station"))
              bus_stop_IDs = new int[] {770, 780, 781, 789, 803, 804}; 
            else if (name.equals("Intermediate Road"))
              bus_stop_IDs = new int[] {771};
            else if (name.equals("Dialstone Lane/Hillcrest Road"))
              bus_stop_IDs = new int[] {772, 788};
            else if (name.equals("Asda/Sainsbury's"))
              bus_stop_IDs = new int[] {782};
            else if (name.equals("Lower Bents Lane/Stockport Road"))
              bus_stop_IDs = new int[] {779, 783};
            

    } else if (area == "Romiley") {
          if (name.equals("Corcoran Drive"))
            bus_stop_IDs = new int[] {776, 785}; 
          else if (name.equals("Train Station"))
            bus_stop_IDs = new int[] {777, 784};
          else if (name.equals("Frog and Diver Arms"))
            bus_stop_IDs = new int[] {778};
          

    } else if (area == "Marple") {
          if (name.equals("Navigation Hotel"))
            bus_stop_IDs = new int[] {773, 800, 806, 807, 801, 787}; 
          else if (name.equals("Offerton Fold"))
            bus_stop_IDs = new int[] {802, 805};
          else if (name.equals("Norfolk Arms"))
            bus_stop_IDs = new int[] {775, 786};
          else if (name.equals("Back of Beyond"))
            bus_stop_IDs = new int[] {774};
          

    } else if (area == "Strines") {
          if (name.equals("Royal Oak"))
            bus_stop_IDs = new int[] {799, 808}; 
          

   } else if (area == "New Mills") {
          if (name.equals("Bus Station"))
            bus_stop_IDs = new int[] {798, 809}; 
           

    } else if (area == "Birch Vale") {
          if (name.equals("Grouse Hotel"))
            bus_stop_IDs = new int[] {794, 813}; 
          

    } else if (area == "Hayfield") {
          if (name.equals("Bus Station"))
             bus_stop_IDs = new int[] {793, 814}; 
          

    } else if (area == "Newtown") {
          if (name.equals("Train Station"))
            bus_stop_IDs = new int[] {797, 810}; 
          

    } else if (area == "Low Leighton") {
          if (name.equals("Ollerset View Hospital"))
            bus_stop_IDs = new int[] {796, 811}; 
          

    } else if (area == "Thornsett") {
          if (name.equals("Printers Arms"))
            bus_stop_IDs = new int[] {795, 812}; 
          

    } else if (area == "Glossop") {
          if (name.equals("Grouse Inn"))
            bus_stop_IDs = new int[] {791, 816}; 
          else if (name.equals("Little Hayfield"))
            bus_stop_IDs = new int[] {792, 815};
          else if (name.equals("Henry Street"))
            bus_stop_IDs = new int[] {790, 817};
          

     } else {
          throw new IllegalArgumentException("Invalid area: " + area);
     }
  return bus_stop_IDs;
  }
}