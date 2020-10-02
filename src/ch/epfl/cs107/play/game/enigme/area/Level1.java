/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   2 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.SignalDialog;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.game.enigme.actor.Switchable;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class Level1 extends EnigmeArea{
	private Door door1;
	private SignalDialog dialog;
	private Switchable ghost;

	/**
	 * Constructor for Level1 in the game.
	 */
	public Level1() {
		super("Level1");
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem, this);

		DiscreteCoordinates[] cells = {};

		door1 = new SignalDoor(this, "LevelSelector", new DiscreteCoordinates(1,6), 
				new DiscreteCoordinates(5,0), cells, Logic.TRUE);

		ghost = new Switchable("ghost.1", "ghost.1",null, this, 
				new DiscreteCoordinates(6, 6), false);

		dialog = new SignalDialog("Use ENTER to pause and Z/X to zoom in/out",
				"dialog.1", this, 0f, ghost);
		this.addDialog(dialog);

		return true;
	}

}
