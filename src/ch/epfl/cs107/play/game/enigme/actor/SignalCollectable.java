/*
 * Author :   Joseph E. Abboud.
 * Date   :   10 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.Signal;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.MultipleAnd;
import ch.epfl.cs107.play.window.Canvas;

public class SignalCollectable extends Collectable implements Signal{
    private List<AreaEntity> entities;
    private Logic[] signals;

    /**
	 * Initialize a SignalCollectable
	 * 
	 * @param title (String) : Title of the Sprite to use
	 * @param area (Area) : Owner Area of the Collectable
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 * @param areaEntities (List<AreaEntity>) : List of all AreaEntities that will define the signal of this SignalCollectable
	 */
    public SignalCollectable(String title, Area area, DiscreteCoordinates position, List<AreaEntity> areaEntities) {
        super(title, area,  position);
        entities = new LinkedList<>();
        if(areaEntities.size() != 0) {
            entities = areaEntities;
        }
        signals = new Logic[entities.size()];
    }

    /**
	 * Initialize a SignalCollectable
	 * 
	 * @param title (String) : Title of the Sprite to use
	 * @param area (Area) : Owner Area of the Collectable
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 * @param signal (Logic) : Signal that finalizes the signal of the Entity
	 */
    public SignalCollectable(String title, Area area, DiscreteCoordinates position, Logic signal) {

        super(title, area, position);
        entities = new LinkedList<>();

        signals = new Logic[1];
        signals[0] = signal;
    }
    
    /**
	 * Initialize a SignalCollectable
	 * 
	 * @param title (String) : Title of the Sprite to use
	 * @param area (Area) : Owner Area of the Collectable
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 * @param areaEntity (AreaEntity) : AreaEntity that will define the signal of this SignalCollectable
	 */
    public SignalCollectable(String title, Area area, DiscreteCoordinates position, AreaEntity areaEntity) {
        super(title, area,  position);
        entities = new LinkedList<>();
        entities.add(areaEntity);
        signals = new Logic[entities.size()];
    }

    @Override
    public void draw(Canvas canvas) {
        if (!getCollected()) {
            getSprite().draw(canvas);
        }
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean isViewInteractable() {
    	if (entities.size() != 0) {
            for (int i=0 ; i<entities.size() ; ++i) {
                signals[i] = entities.get(i).getSignal();
            }
            return new MultipleAnd(signals).isOn();
        } else {
            return signals[0].isOn();
        }

    }

    /**
     * Setter of the signal linked to this SignalCollectable
     * @param signal (Logic) : Logic to give to 
     */
    public void setSignal(Logic signal) {
        entities.clear();
        signals[0] = signal;
    }

}