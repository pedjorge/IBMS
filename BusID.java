package IBMS;

class BusID
{
	int[] routes;
	int[] services;
	BusClass[] buses;

	public BusID() {

	}

	public BusID(int[] currentRoutes, int[] currentServices, BusClass[] currentBuses) {
		this.routes = currentRoutes;
		this.services = currentServices;
		this.buses = currentBuses;
	}

} // Bus