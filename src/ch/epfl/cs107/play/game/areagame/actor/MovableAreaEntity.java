package ch.epfl.cs107.play.game.areagame.actor;

import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior.Cell;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;


/**
 * MovableAreaEntity are AreaEntity able to move on a grid
 */
public abstract class MovableAreaEntity extends AreaEntity {
	
	/// Indicate if the actor is moving
	private boolean isMoving;
	/// Indicate how many frames the current move is supposed to take
	private int framesForCurrentMove;
	/// The target cell (i.e. where the mainCell will be after the motion)
	private DiscreteCoordinates targetMainCellCoordinates;
	
	/**  @return (List<DiscreteCoordinates>) : Entity's current cells */
	protected final List<DiscreteCoordinates> getLeavingCells(){
		return getCurrentCells();
	}
	
	/**  @return (List<DiscreteCoordinates>) : Entity's objective cells */
	protected final List<DiscreteCoordinates> getEnteringCells(){
		List<DiscreteCoordinates> list = new LinkedList<>();
		for (DiscreteCoordinates coordinates : getCurrentCells()) {
			list.add(coordinates.jump(getOrientation().toVector()));
		}
		return list;
	}
    /**
     * Default MovableAreaEntity constructor
     * @param area (Area): Owner area. Not null
     * @param position (Coordinate): Initial position of the entity. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     */
    public MovableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        resetMotion();
    }

    /**
     * Initialize or reset the current motion information
     */
    protected void resetMotion(){
    	isMoving = false;
    	framesForCurrentMove = 0;
    	targetMainCellCoordinates = getCurrentMainCellCoordinates();
    }
    
    public void setOrientation (Orientation o) {
    	if (!isMoving) {
    		super.setOrientation(o);
    	}
    }
    
    /**
     * 
     * @param framesForMove (int): number of frames used for simulating motion
     * @return (boolean): returns true if motion can occur
     */
    protected  boolean move(int framesForMove){
    	
    	if (!isMoving && (getCurrentMainCellCoordinates().equals(targetMainCellCoordinates))) {
    		if(!areaConditions()) {
    			return false;
    		} else {
    			framesForCurrentMove = framesForMove;
        		if (framesForCurrentMove < 1) {
        			framesForCurrentMove = 1;
        		}
        		Vector orientation = getOrientation().toVector();
        		targetMainCellCoordinates = getCurrentMainCellCoordinates().jump(orientation);
        		isMoving = true;

        		return true;
    		}
    	} else {
    		return false;
    	}
    }
    	
    public boolean areaConditions(){
    	boolean test = getOwnerArea().enterAreaCells(this, getEnteringCells()) 
    					&& getOwnerArea().leaveAreaCells(this, getLeavingCells());
    	return test;
    }

    /// MovableAreaEntity implements Actor

    @Override
    public void update(float deltaTime) {
    	if (isMoving && (!getCurrentMainCellCoordinates().equals(targetMainCellCoordinates))) {
    		Vector distance = getOrientation().toVector();
    		distance = (distance.mul(1.0f / framesForCurrentMove));
    		setCurrentPosition(getPosition().add(distance));
    	} else {
    		resetMotion();
    	}
    }

    /// Implements Positionable

    @Override
    public Vector getVelocity() {
    	return getOrientation().toVector().mul(framesForCurrentMove);
    }
    
}
