package ch.epfl.cs107.play.game.enigme.actor;

import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.Signal;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.MultipleAnd;
import ch.epfl.cs107.play.window.Canvas;

public class SignalDoor extends Door implements Signal {
    private List<AreaEntity> entities;
    private Logic[] signals;
    
    /**
	 * Initialize a default SignalDoor
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param width (float) : width of the door
	 * @param areaEntities (List<AreaEntity>) : List of all AreaEntities that will define the signal of this SignalDoor
	 */
    public SignalDoor(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
                      DiscreteCoordinates position, DiscreteCoordinates[] list, List<AreaEntity> areaEntities, float width) {

        super(area, titleOfDestination, coordinatesAtArrival, Orientation.DOWN, position, false, list, width);
        entities = new LinkedList<>();

        if(areaEntities.size() != 0) {
            entities = areaEntities;
        }
        signals = new Logic[entities.size()];
    }
    
    /**
	 * Initialize a default SignalDoor of width 1f
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param areaEntities (List<AreaEntity>) : List of all AreaEntities that will define the signal of this SignalDoor
	 */
    public SignalDoor(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
            DiscreteCoordinates position, DiscreteCoordinates[] list, List<AreaEntity> areaEntities) {
    	this(area, titleOfDestination, coordinatesAtArrival, position, list, areaEntities, 1f);
    }
    
    /**
	 * Initialize a default SignalDoor
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param width (float) : width of the door
	 * @param signal (Logic) : Signal that finalizes the signal of the Entity
	 */
    public SignalDoor(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
            DiscreteCoordinates position, DiscreteCoordinates[] list, Logic signal, float width) {

		super(area, titleOfDestination, coordinatesAtArrival, Orientation.DOWN, position, false, list, width);
		entities = new LinkedList<>();
		
		signals = new Logic[1];
		signals[0] = signal;
	}    

    /**
	 * Initialize a default SignalDoor of width 1f
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param signal (Logic) : Signal that finalizes the signal of the Entity
	 */
    public SignalDoor(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
            DiscreteCoordinates position, DiscreteCoordinates[] list, Logic signal) {
    	this(area, titleOfDestination, coordinatesAtArrival, position, list, signal, 1f);
    }
    
    /**
	 * Initialize a default SignalDoor
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param width (float) : width of the door
	 * @param areaEntity (AreaEntity) : AreaEntity that will define the signal of this SignalDoor
	 */
    public SignalDoor(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
            DiscreteCoordinates position, DiscreteCoordinates[] list, AreaEntity areaEntity, float width) {

    	super(area, titleOfDestination, coordinatesAtArrival, Orientation.DOWN, position, false, list, width);
    	entities = new LinkedList<>();
    	entities.add(areaEntity);
    	
    	signals = new Logic[entities.size()];
    	
    }
    
    /**
	 * Initialize a default SignalDoor of width 1f
	 * 
	 * @param area	(Area) : Current area where the door is placed
	 * @param titleOfDestination (String) : Area where the door will get you
	 * @param coordinatesAtArrival (DiscreteCoordinates) : Position of the player when getting to the target area
	 * @param position	(DiscreteCoordinates) : Position in the ownerArea
	 * @param list (DiscreteCoordinates[]) : All main cell coordinates of the door
	 * @param areaEntity (AreaEntity) : AreaEntity that will define the signal of this SignalDoor
	 */
    public SignalDoor(Area area, String titleOfDestination, DiscreteCoordinates coordinatesAtArrival,
            DiscreteCoordinates position, DiscreteCoordinates[] list, AreaEntity areaEntity) {
    	this(area, titleOfDestination, coordinatesAtArrival, position, list, areaEntity, 1f);

    }    

    @Override
    public float getIntensity(float t) {
    	if (entities.size() != 0) {
    		for (int i=0 ; i<entities.size() ; ++i) {
        		signals[i] = entities.get(i).getSignal();
        	}
            if (new MultipleAnd(signals).isOn()) {
                return 1f;
            }
    	
    	} else {
    		if(signals[0].isOn()) {
    			return 1f;
    		}
    	}
    	
        return 0f;
    }

    @Override
    public void draw(Canvas canvas) {
    	
    	if(getIntensity(timing) == 1f) {
    		getDoorOpen().draw(canvas);
    	} else {
    		getDoorClosed().draw(canvas);  	
    	}
    }
 
    @Override
    public boolean takeCellSpace() {
        return getIntensity(timing) == 0f;
    }
    
    @Override
    public boolean isCellInteractable() {
        // return true if signal has 1.0 as intensity
        // return false if signal has 0.0 as intensity
        return getIntensity(timing) == 1.0f;
    }
    
    /**
     * Setter of the signal linked to this SignalDoor
     * @param signal (Logic) : Logic to give to 
     */
    public void setSignal(Logic signal) {
    	signals[0] = signal;
    }
 
    @Override
    public Logic getSignal() {
    	if (getIntensity(timing) ==1f && getTimesUsed() != 0) {
            return Logic.TRUE;
        } else {
            return Logic.FALSE;
        }
    }
}
