package IBMS;
class BusClass
{
  int arrival_time; int service; boolean cancel; boolean delay; String name;

  public BusClass() {
    this.arrival_time = 0; this.service = 0; this.cancel = false; this.delay = false; this.name = null;
  }

  public BusClass(int time, int service_name, boolean cancelled, boolean delayed, String bus_name) {
    this.arrival_time = time; this.service = service_name; this.cancel = cancelled; this.delay = delayed; this.name = bus_name;
  }
} // Bus
