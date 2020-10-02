/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   2 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

@SuppressWarnings("ALL")
public class Door extends AreaEntity{
	private String destination;
	private DiscreteCoordinates coordinatesAtArrival;
	private List<DiscreteCoordinates> currentCells;
	public boolean isClosed;
	private Sprite doorOpen; 
	private Sprite doorClosed; 
	private int timesUsed;

	/**
	 * Initialize particular door with given String
	 * 
	 * @param titleOn (String) : Title of the open door
	 * @param titleOff (String) : Title of the closed door
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param orientation (Orientation) : Orientation of the door (not useful)
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param isClosed	(boolean) : Indicates if we want the door to be displayed as closed (true) or open (false) inside the game
	 * @param width (float) : width of the door
	 */
	public Door(String titleOn, String titleOff, Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
			Orientation orientation, DiscreteCoordinates position, boolean isClosed,  DiscreteCoordinates[] list, float width) {
		super(area, orientation, position);

		destination = titleOfDestination;
		this.coordinatesAtArrival = coordinatesAtArrival;

		doorClosed = new Sprite(titleOff, width, 1, this);
		doorOpen = new Sprite(titleOn, width, 1, this);

		this.isClosed = isClosed;
		currentCells = new LinkedList<>();

		if (list.length != 0) {
			for (DiscreteCoordinates coordinates : list) {
				currentCells.add(coordinates);
			}
		}
		timesUsed=0;
	}
	
	/**
	 * Initialize a classic default door
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param orientation (Orientation) : Orientation of the door (not useful)
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param isClosed	(boolean) : Indicates if we want the door to be displayed as closed (true) or open (false) inside the game
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param width (float) : width of the door
	 */
	public Door(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
			Orientation orientation, DiscreteCoordinates position, boolean isClosed,  DiscreteCoordinates[] list, float width) {
		this("door.open.1", "door.close.1", area, titleOfDestination, coordinatesAtArrival,
				orientation, position, isClosed, list, width);
	}


	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		if (currentCells.size() == 0) {
			return Collections.singletonList(getCurrentMainCellCoordinates());
		}
		return currentCells;
	}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public void draw(Canvas canvas) {		
		if(isClosed) {
			doorClosed.draw(canvas);
		} else {
			doorOpen.draw(canvas);
		}
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
		++timesUsed;
	}

	/**
	 * Getter for the Area of destination
	 * @return (String) : title of the Area 
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Getter for the coordinates at spawn in Area of destination
	 * @return (DiscreteCoordinates) : coordinates at spawn
	 */
	public DiscreteCoordinates getCoordinatesAtArrival() {
		return coordinatesAtArrival;
	}

	/**
	 * Getter of the Sprite representing the closed Door
	 * @return (Sprite)
	 */
	public Sprite getDoorClosed() {
		return doorClosed;
	}
	
	/**
	 * Setter of the Sprite representing the closed Door
	 * @param (Sprite) : the closed Door
	 */
	public void setDoorClosed(Sprite s) {
		doorClosed = s;
	}

	/**
	 * Getter of the Sprite representing the open Door
	 * @return (Sprite)
	 */
	public Sprite getDoorOpen() {
		return doorOpen;
	}

	/**
	 * Setter of the Sprite representing the open Door
	 * @param (Sprite) : the open Door
	 */
	public void setDoorOpen(Sprite s) {
		doorOpen = s;
	}

	/**
	 * Getter for the number of times the door has been used
	 * @return (int)
	 */
	public int getTimesUsed() {
		return timesUsed;
	}

	@Override
	public Logic getSignal() {
		// This method should not be called
		System.out.println("This is a method that should not be called, check your classes priorities");
		return null;
	}
}
