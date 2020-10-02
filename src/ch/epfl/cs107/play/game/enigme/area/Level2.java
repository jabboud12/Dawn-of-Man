/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   2 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.enigme.actor.Collectable;
import ch.epfl.cs107.play.game.enigme.actor.Dialog;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class Level2 extends EnigmeArea {
	private SignalDoor door1;
	private Collectable apple;
	private Dialog dialog;

	/**
	 * Constructor for Level2 in the game.
	 */
	public Level2() {
		super("Level2");
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem, this);
		
		DiscreteCoordinates[] cells = {};

		door1 = new SignalDoor(this, "LevelSelector", new DiscreteCoordinates(2,6), new DiscreteCoordinates(5,0), cells, Logic.TRUE);
		apple = new Collectable("apple.1", this, new DiscreteCoordinates(5, 6));
		
		dialog = new Dialog("Use  SPACE  to  interact", "dialog.1", this, 0f);
		this.addDialog(dialog);

		return true;
	}

	/**
	 * Actualize the signal of compound propositions inside the method
	 */
	public void updateEntities() {
		if(apple.getSignal().isOn()) {
			dialog.resetDialog("Well done!");
			setCameraScaleFactor(20);
		}
	}
}
