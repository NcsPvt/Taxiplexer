package itcurves.ncs;

import itcurves.ncs.TaxiPlexer.Trip;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class TripArrayList extends ArrayList<Trip> {

	/**
	 * Returns the position of the specified item in the array.
	 * 
	 * 
	 * @param item The item to retrieve the position of.
	 * 
	 * @return The position of the specified item, matching the Trip Number.
	 * 
	 * @author Salman Khalid
	 */
	public int indexOf(Trip item) {
		// int index;
		// synchronized (TaxiPlexer.arrayListOfTreatedTrips) {
		// for (int i = 0; i < this.size(); i++)
		// if (TaxiPlexer.arrayListOfTreatedTrips.get(i).tripNumber.equals(item.tripNumber)) {
		// if (TaxiPlexer.arrayListOfTreatedTrips.get(i).nodeType.equals(item.nodeType))
		// index = i;
		// else if (!TaxiPlexer.arrayListOfTreatedTrips.get(i).SharedKey.equalsIgnoreCase(item.SharedKey)) {
		// TaxiPlexer.arrayListOfTreatedTrips.remove(TaxiPlexer.arrayListOfTreatedTrips.get(i));
		// if (TaxiPlexer.arrayListOfTreatedTrips.get(i).SharedKey.equalsIgnoreCase("1")) {
		// for (int j = 0; j < TaxiPlexer.arrayListOfTreatedTrips.size(); j++)
		// if (TaxiPlexer.arrayListOfTreatedTrips.get(j).tripNumber.equals(item.tripNumber)) {
		// TaxiPlexer.arrayListOfTreatedTrips.remove(TaxiPlexer.arrayListOfTreatedTrips.get(j));
		// }
		// }
		// TaxiPlexer.arrayListOfTreatedTrips.notifyAll();
		// index = -1;
		// }
		// }
		// index = -1;
		// }

		// if (index == -1) {
		synchronized (this) {
			for (int i = 0; i < this.size(); i++)
				if (this.get(i).tripNumber.equals(item.tripNumber)) {
					if (this.get(i).nodeType.equals(item.nodeType))
						return i;
					else if (!this.get(i).SharedKey.equalsIgnoreCase(item.SharedKey)) {
						String lSharedKey = this.get(i).SharedKey;
						this.remove(this.get(i));
						if (lSharedKey.equalsIgnoreCase("1")) {
							for (int j = 0; j < this.size(); j++)
								if (this.get(j).tripNumber.equals(item.tripNumber)) {
									this.remove(this.get(j));
								}
						}
						this.notifyAll();
						return -1;
					}
				} else if (this.get(i).ConfirmNumber.equals(item.ConfirmNumber)) {
					if (this.get(i).nodeType.equals(item.nodeType))
						return i;
					else if (!this.get(i).SharedKey.equalsIgnoreCase(item.SharedKey)) {
						String lSharedKey = this.get(i).SharedKey;
						this.remove(this.get(i));
						if (lSharedKey.equalsIgnoreCase("1")) {
							for (int j = 0; j < this.size(); j++)
								if (this.get(j).ConfirmNumber.equals(item.ConfirmNumber)) {
									this.remove(this.get(j));
								}
						}
						this.notifyAll();
						return -1;
					}
				}

			return -1;
		}
		// } else
		// return index;

	}

	public int indexOfTripfromTripNumber(String trimNum) {

		synchronized (this) {
			for (int i = 0; i < this.size(); i++)
				if (this.get(i).tripNumber.equalsIgnoreCase(trimNum)) {
					this.notifyAll();
					return i;

				}
			return -1;
		}
		// } else
		// return index;

	}

}
