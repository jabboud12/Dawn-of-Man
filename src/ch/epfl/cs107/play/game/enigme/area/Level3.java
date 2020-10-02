/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   2 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.enigme.actor.Collectable;
import ch.epfl.cs107.play.game.enigme.actor.PressurePlate;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.game.enigme.actor.SignalSwitchable;
import ch.epfl.cs107.play.game.enigme.actor.Switchable;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.MultipleAnd;
import ch.epfl.cs107.play.signal.logic.Not;
import ch.epfl.cs107.play.signal.logic.Or;
import ch.epfl.cs107.play.window.Window;

public class Level3 extends EnigmeArea {
	private SignalDoor door1;
	
	private Collectable key;
	
	private Switchable torch;
	private Switchable lever1, lever2, lever3;
	private Switchable switch1, switch2, switch3, switch4, switch5, switch6, switch7;
	private Switchable plate;
	
	private SignalSwitchable rock1, rock2, rock3;
	
	private List<AreaEntity> entitiesForRock2;
	private List<AreaEntity> entitiesForDoor1;
	
	/**
	 * Constructor for Level3 in the game.
	 */
	public Level3() {
		super("Level3");
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem, this);
		DiscreteCoordinates[] cells = {new DiscreteCoordinates(5, 9)};
		
		///Interactables in the area
		key = new Collectable("key.1",this, new DiscreteCoordinates(1, 3));
		torch = new Switchable("torch.ground.on.1", "torch.ground.off","torch.ground.on.2", this, new DiscreteCoordinates(7, 5), true);
		
		lever1 = new Switchable("lever.big.left", "lever.big.right",null, this, new DiscreteCoordinates(10, 5), true);
		lever2 = new Switchable("lever.big.left", "lever.big.right",null, this, new DiscreteCoordinates(9, 5), true);
		lever3 = new Switchable("lever.big.left", "lever.big.right",null, this, new DiscreteCoordinates(8, 5), true);
		
		switch1 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(4, 4), false);
		switch2 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(5, 4), false);
		switch3 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(6, 4), false);
		switch4 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(5, 5), false);
		switch5 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(4, 6), false);
		switch6 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(5, 6), false);
		switch7 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(6, 6), false);

		plate = new PressurePlate(this, new DiscreteCoordinates(9, 8));
		rock1 = new SignalSwitchable(null,"rock.3", null, this, new DiscreteCoordinates(6, 8), plate, true);
		
		
        entitiesForRock2 = Arrays.asList(switch1, switch2, switch3, switch4, switch5, switch6, switch7);
		rock2 = new SignalSwitchable(null,"rock.3", null, this, new DiscreteCoordinates(5, 8), entitiesForRock2, true);
		
		/* Here we just add the torch for rock3 to be initialized. 
		 * The signal it will receive is computed in updateRock3Signal()*/ 
		rock3 = new SignalSwitchable(null,"rock.3", null, this, new DiscreteCoordinates(4, 8), torch, true);
		
		/// If you want the door to be opened after getting rid of the 3 rocks, uncomment the following 
		/// and replace the argument key in the door's constructor by entitiesFordoor1
		entitiesForDoor1 = Arrays.asList(key, rock1, rock2, rock3);
		door1 = new SignalDoor(this, "LevelSelector", new DiscreteCoordinates(3,6), new DiscreteCoordinates(5,9), cells, entitiesForDoor1);
		
		return true;
	}
	
	/**
	 * Actualize the signal of compound propositions inside the method
	 */
	public void updateEntities() {
		Logic s1 = new MultipleAnd(lever1.getSignal(), new Not(lever2.getSignal()), lever3.getSignal());
		Logic signal = new Or(s1, torch.getSignal());
		rock3.setSignal(signal);
	}
	
}
