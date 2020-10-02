package ch.epfl.cs107.play.game.areagame.actor;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;


/**
 * Actors leaving in a grid
 */
public abstract class AreaEntity extends Entity implements Interactable {

	/// An AreaEntity knows its own Area
	private Area ownerArea;
	/// Orientation of the AreaEntity inside the Area
	private Orientation orientation;
	/// Coordinate of the main Cell linked to the Entity
	private DiscreteCoordinates currentMainCellCoordinates;
	
	/// Time of reference to manage interactions
	protected float timing;
	

    /**
     * Default AreaEntity constructor
     * @param area (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity in the Area. Not null
     * @param position (DiscreteCoordinate): Initial position of the entity in the Area. Not null
     */
    public AreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(position.toVector());
        ownerArea = area;
        this.orientation = orientation;
        currentMainCellCoordinates = position;
        area.registerActor(this);
        timing = 0;
    }


    /**
     * Getter for the coordinates of the main cell occupied by the AreaEntity
     * @return (DiscreteCoordinates)
     */
    protected DiscreteCoordinates getCurrentMainCellCoordinates(){
        return currentMainCellCoordinates;
    }
    
    /**
     * Setter for the position of the AreaEntity in the Area
     */
    public void setCurrentPosition(Vector v) {
    	DiscreteCoordinates coord = new DiscreteCoordinates((int) v.x, (int) v.y);
    	if(DiscreteCoordinates.isCoordinates(v)) {
    		currentMainCellCoordinates = new DiscreteCoordinates((int) v.round().x, (int) v.round().y);
    	}
    	super.setCurrentPosition(v);
    }
    /** Getter for the AreaEntity's Orientation
     *	@return (Orientation)
     */
    protected Orientation getOrientation() {
    	return orientation;
    }
    
    /**
     * Setter for the AreaEntity's Orientation
     * @param o (Orientation) : New Orientation
     */
    protected void setOrientation(Orientation o) {
    	this.orientation = o;
    }
    
    /**
  	 * Getter for the AreaEntity's current Area
     * @return (Area)
     */
    protected Area getOwnerArea() {
    	return ownerArea;
    }
    
    /**
  	 * Setter for the AreaEntity's current Area
     * @param a (Area) : new Area
     */
    protected void setOwnerArea(Area a) {
    	this.ownerArea = a;
    }
    
    /**  @return (Logic) : signal emitted by the AreaEntity */
    public abstract Logic getSignal();
    
}
