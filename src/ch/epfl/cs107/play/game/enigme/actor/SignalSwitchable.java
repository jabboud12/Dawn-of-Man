/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   10 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.Signal;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.MultipleAnd;

public class SignalSwitchable extends Switchable implements Signal {
    private List<AreaEntity> entities;
    private Logic[] signals;
    
    /**
	 * Initialize a SignalSwitchable
	 * 
	 * @param titleOn1 (String) : Title of the SpriteOn1 to use, can be null
	 * @param titleOff (String) : Title of the SpriteOff to use, can be null
	 * @param titleOn2 (String) : Title of the SpriteOn2 to use if wanting an animation, can be null
	 * @param area (Area) : Owner Area of the Switchable
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 * @param b (boolean) : true if the Entity is not traversable, else false
	 * @param areaEntities (List<AreaEntity>) : List of all AreaEntities that will define the signal of this SignalSwitchable
	 */
    public SignalSwitchable(String titleOn1, String titleOff, String titleOn2, Area area, DiscreteCoordinates position,
							List<AreaEntity> areaEntities, boolean b) {
        super(titleOn1,titleOff, titleOn2, area, Orientation.DOWN, position, b);
        entities = new LinkedList<>();
        if(areaEntities.size() != 0) {
            entities = areaEntities;
        }
        signals = new Logic[entities.size()];
    }
    
    /**
	 * Initialize a SignalSwitchable
	 * 
	 * @param titleOn1 (String) : Title of the SpriteOn1 to use, can be null
	 * @param titleOff (String) : Title of the SpriteOff to use, can be null
	 * @param titleOn2 (String) : Title of the SpriteOn2 to use if wanting an animation, can be null
	 * @param area (Area) : Owner Area of the Switchable
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 * @param b (boolean) : true if the Entity is not traversable, else false
	 * @param signal (Logic) : Signal that finalizes the signal of the Entity
	 */
    public SignalSwitchable(String titleOn1, String titleOff, String titleOn2, Area area, DiscreteCoordinates position,
							Logic signal, boolean b) {
        super(titleOn1,titleOff, titleOn2, area, Orientation.DOWN, position, b);
		entities = new LinkedList<>();

		signals = new Logic[1];
		signals[0] = signal;
	}
    
    /**
	 * Initialize a SignalSwitchable
	 * 
	 * @param titleOn1 (String) : Title of the SpriteOn1 to use, can be null
	 * @param titleOff (String) : Title of the SpriteOff to use, can be null
	 * @param titleOn2 (String) : Title of the SpriteOn2 to use if wanting an animation, can be null
	 * @param area (Area) : Owner Area of the Switchable
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 * @param b (boolean) : true if the Entity is not traversable, else false
	 * @param areaEntity (AreaEntity) : AreaEntity that will define the signal of this SignalSwitchable
	 */
    public SignalSwitchable(String titleOn1, String titleOff, String titleOn2, Area area, DiscreteCoordinates position,
							AreaEntity areaEntity, boolean b) {
        super(titleOn1,titleOff, titleOn2, area, Orientation.DOWN, position, b);
        entities = new LinkedList<>();
        entities.add(areaEntity);
        signals = new Logic[entities.size()];
    }
    
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
		return getIntensity(timing) != 1f;
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
    
    public Logic getSignal() {
    	if(getIntensity(timing) == 1f) {
    		return Logic.TRUE;
    	}
    	return Logic.FALSE;
    }
    
    public void setSignal(Logic signal) {
    	entities.clear();
    	signals[0] = signal;
    }
}
