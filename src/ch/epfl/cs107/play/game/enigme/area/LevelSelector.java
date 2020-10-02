/*
 * Author :   Joseph ABBOUD 
 * Date   :   2 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.enigme.actor.Dialog;
import ch.epfl.cs107.play.game.enigme.actor.SignalDialog;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.game.enigme.actor.Switchable;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class LevelSelector extends EnigmeArea {
    private SignalDoor door1, door2, door3, door4;
    private SignalDoor door5, door6, door7, door8;
    private Dialog dialog;
    private SignalDialog dialog1;
    
    private Switchable player1, player2, player3, player4;


    /**
	 * Constructor for Level0 in the game.
	 */
    public LevelSelector() {
        super("LevelSelector");
    }
   
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        DiscreteCoordinates[] cells = {};
        super.begin(window, fileSystem, this);

        door1 = new SignalDoor(this, "Level1", new DiscreteCoordinates(5, 1), new DiscreteCoordinates(1, 7), cells, Logic.TRUE);
        door2 = new SignalDoor(this, "Level2", new DiscreteCoordinates(5, 1), new DiscreteCoordinates(2, 7), cells, door1);
        door3 = new SignalDoor(this, "Level3", new DiscreteCoordinates(5, 1), new DiscreteCoordinates(3, 7), cells, door2);
        door4 = new SignalDoor(this, "Enigme2", new DiscreteCoordinates(7,1), new DiscreteCoordinates(4, 7), cells, door3);
        door5 = new SignalDoor(this, "Enigme1", new DiscreteCoordinates(6, 31), new DiscreteCoordinates(5, 7), cells, door4);
        door6 = new SignalDoor(this, "", new DiscreteCoordinates(6, 6), new DiscreteCoordinates(6, 7), cells, door5);
        door7 = new SignalDoor(this, "", new DiscreteCoordinates(7, 6), new DiscreteCoordinates(7, 7), cells, Logic.FALSE);
        door8 = new SignalDoor(this, "", new DiscreteCoordinates(8, 6), new DiscreteCoordinates(8, 7), cells, Logic.FALSE);

        dialog = new Dialog("Welcome to Dawn Of MAN !            Use arrows/WASD to move", "dialog.1", this, 1f);
        this.addDialog(dialog);
        
        player1 = new Switchable("girl.1", "girl.1", null, this, new DiscreteCoordinates(1, 3), true,
        								new RegionOfInterest(0, 0, 16, 21), new Vector(0.25f, 0.32f), .6f, .875f);
        
        player2 = new Switchable("old.man.1", "old.man.1", null, this, new DiscreteCoordinates(3, 3), true,
				new RegionOfInterest(0, 0, 16, 21), new Vector(0.25f, 0.32f), .6f, .875f);
        
        player3 = new Switchable("max.ghost", "max.ghost", null, this, new DiscreteCoordinates(5, 3), true,
				new RegionOfInterest(0, 0, 16, 21), new Vector(0.25f, 0.32f), .6f, .875f);
        
        player4 = new Switchable("girl.5", "girl.5", null, this, new DiscreteCoordinates(7, 3), true,
				new RegionOfInterest(0, 0, 16, 21), new Vector(0.25f, 0.32f), .6f, .875f);
        

        return true;
    }
    
    public int updateEntities() {
    	if (player1.getSignal().isOn() ) {
    		
    		return 1;
    		
    	} else if (player2.getSignal().isOn()) {
    		
    		return 2;

    	} else if (player3.getSignal().isOn()) {
    		
    		return 3;

    	} else if (player4.getSignal().isOn()) {
    		
    		return 4;

    	} else {
    		
    		return 0;

    	}
    	 
    }
}
