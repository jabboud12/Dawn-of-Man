/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   27 Nov 2018
 */

package ch.epfl.cs107.play.game.enigme.area.demo2;

import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public class Room0 extends Demo2Area{
	
	/**
	 * Constructor for Level0 in the game.
	 */
	public Room0() {
		super("LevelSelector");
	}
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem, this);
		return true;
	}
}