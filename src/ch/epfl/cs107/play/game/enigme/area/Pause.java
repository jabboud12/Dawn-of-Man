/*
 * Author :   Joseph E. Abboud.
 * Date   :   11 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.area;

import java.util.Random;

import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.enigme.actor.AnimatedCoin;
import ch.epfl.cs107.play.game.enigme.actor.Collectable;
import ch.epfl.cs107.play.game.enigme.actor.Dialog;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class Pause extends EnigmeArea {
	private Dialog dialog;
	private Door door1;
	private AnimatedCoin coin;
    private Sprite[] coins;

    /**
	 * Constructor for Pause in the game.
	 */
	public Pause() {
		super("Pause");
	}
	
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem, this);
		
		dialog = new Dialog("Pause", "dialog.2", this, 0f);
		this.addDialog(dialog);
		
		DiscreteCoordinates[] cells = {};
		door1 = new SignalDoor(this, "", new DiscreteCoordinates(5,1), new DiscreteCoordinates(5,0), cells, Logic.FALSE);
		

        coin = new AnimatedCoin(this, new DiscreteCoordinates(6, 6));
        
		return true;		
	}

	/**
	 * Actualize the signal of compound propositions inside the method
	 */
	public void updateEntities() {
		if (coin.getCollected()) {
			Random rand = new Random();
			int x = 0;
			int y = 0;
	        do {
	        	x = rand.nextInt(10);
		        y = rand.nextInt(6);
	        } while ((new DiscreteCoordinates(x, y)).equals(coin.getCurrentCells().get(0)) 
	        			|| (new DiscreteCoordinates(x, y)).equals(new DiscreteCoordinates(1, 8)));
			coin.leaveArea();
	        coin = new AnimatedCoin(this, new DiscreteCoordinates(x+1, y+3));
		}
	}
	
}
