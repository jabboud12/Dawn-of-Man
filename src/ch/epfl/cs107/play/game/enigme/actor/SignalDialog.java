/*
 * Author :   Joseph E. Abboud.
 * Date   :   11 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;

import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.MultipleAnd;
import ch.epfl.cs107.play.window.Canvas;

public class SignalDialog extends Dialog{
	
	private List<AreaEntity> entities;
    private Logic[] signals;
    
    /**
	 * Initialize a SignalDialog
	 * 
	 * @param text (String): string of the dialog, not null
     * @param backgroundName (String): Background file name (i.e only the name, with neither path, nor file extension), may be null
     * @param area (Area): this owner area to compute scale factor ratios, not null
     * @param displayTime (float): determines for how long the dialog will be displayed. Will be displayed indefinitely if == 0
	 * @param areaEntities (List<AreaEntity>) : List of all AreaEntities that will define the signal of this Dialog
	 */
    public SignalDialog(String text, String backgroundName, Area area, float displayTime, List<AreaEntity> areaEntities) {
		super(text, backgroundName, area, displayTime);
		entities = new LinkedList<>();

        if(areaEntities.size() != 0) {
            entities = areaEntities;
        }
        signals = new Logic[entities.size()];       
    }
    
    /**
	 * Initialize a SignalDialog
	 * 
	 * @param text (String): string of the dialog, not null
     * @param backgroundName (String): Background file name (i.e only the name, with neither path, nor file extension), may be null
     * @param area (Area): this owner area to compute scale factor ratios, not null
     * @param displayTime (float): determines for how long the dialog will be displayed. Will be displayed indefinitely if == 0
	 * @param signal (Logic) : Signal that finalizes the signal of the Entity
	 */
    public SignalDialog(String text, String backgroundName, Area area, float displayTime, Logic signal) {
		super(text, backgroundName, area, displayTime);
		entities = new LinkedList<>();

		signals = new Logic[1];
		signals[0] = signal;       
    }
    
    /**
	 * Initialize a SignalDialog
	 * 
	 * @param text (String): string of the dialog, not null
     * @param backgroundName (String): Background file name (i.e only the name, with neither path, nor file extension), may be null
     * @param area (Area): this owner area to compute scale factor ratios, not null
     * @param displayTime (float): determines for how long the dialog will be displayed. Will be displayed indefinitely if == 0
	 * @param areaEntity (AreaEntity) : AreaEntity that will define the signal of this Collectable
	 */
    public SignalDialog(String text, String backgroundName, Area area, float displayTime, AreaEntity areaEntity) {
		super(text, backgroundName, area, displayTime);
		entities = new LinkedList<>();

		entities.add(areaEntity);    	
    	signals = new Logic[entities.size()];
        
    }
    
    /**
     * Getter for the Intensity of this Dialog's signal
     * 
	 * @param t (float) : time at which we want the Intensity
	 * @return (float) : 1.0f if the Entity is pickedUp, else 0.0f 
	 */
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
    	if(getIntensity(2) == 1f) {
    		super.draw(canvas);
    	}
    }
    
    /**
     * Setter of the signal linked to this SignalCollectable
     * @param signal (Logic) : Logic to give to 
     */
    public void setSignal(Logic signal) {
    	signals[0] = signal;
    }
    
    /**
     * Getter of the signal linked to this SignalDialog
     * @return (Logic) 
     */
    public Logic getSignal() {
    	if (getIntensity(2) ==1f) {
            return Logic.TRUE;
        } else {
            return Logic.FALSE;
        }
    }
    
}
