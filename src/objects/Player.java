package objects;

import java.awt.Point;

public class Player {

	// TODO: Add configuration for point location
	private static final Point PLAYER_STARTING_POINT = new Point(50, 50);

	private Point currentLocation;

	public Player() {

		this.setCurrentLocation(PLAYER_STARTING_POINT);

	}

	/**
	 * Gets Player current location on map
	 * 
	 * @return the currentLocation
	 */
	public Point getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Sets a new location for Player on map
	 * 
	 * @param newLocation
	 *            the newLocation to set
	 */
	public void setCurrentLocation(Point newLocation) {
		this.currentLocation = newLocation;
	}

	public void bending(){
		//TODO: Adding bending action
	}
	
}
