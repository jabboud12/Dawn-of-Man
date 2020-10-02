/*
 * Author :   Joseph E. Abboud.
 * Date   :   12 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Portal extends SignalDoor{

	/**
	 * Initialize a default Portal
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param areaEntities (List<AreaEntity>) : List of all AreaEntities that will define the signal of this SignalDoor
	 */
	public Portal(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
			DiscreteCoordinates position, DiscreteCoordinates[] list, List<AreaEntity> areaEntities) {
		super(area, titleOfDestination, coordinatesAtArrival, position, list, areaEntities);
		setDoorOpen(new Sprite("portal.green", 1f, 1f, this));
		setDoorClosed(new Sprite("portal.red", 1f, 1f, this));
	}

	/**
	 * Initialize a default Portal
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param signal (Logic) : Signal that finalizes the signal of the Entity
	 */
	public Portal(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
			DiscreteCoordinates position, DiscreteCoordinates[] list, Logic signal) {
		super(area, titleOfDestination, coordinatesAtArrival, position, list, signal);
		setDoorOpen(new Sprite("portal.green", 1f, 1f, this));
		setDoorClosed(new Sprite("portal.red", 1f, 1f, this));
	}

	/**
	 * Initialize a default Portal
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param areaEntity (AreaEntity) : AreaEntity that will define the signal of this SignalDoor
	 */
	public Portal(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
			DiscreteCoordinates position, DiscreteCoordinates[] list, AreaEntity areaEntity) {
		super(area, titleOfDestination, coordinatesAtArrival, position, list, areaEntity);
		setDoorOpen(new Sprite("portal.green", 1f, 1f, this));
		setDoorClosed(new Sprite("portal.red", 1f, 1f, this));
	}

}
