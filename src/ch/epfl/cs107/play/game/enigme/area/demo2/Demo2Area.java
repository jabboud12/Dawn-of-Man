/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   27 Nov 2018
 */

package ch.epfl.cs107.play.game.enigme.area.demo2;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public abstract class Demo2Area extends Area{	
	
	/**
	 * Set the title of the Area
	 * @param title (String) : title of the current area
	 */
	public Demo2Area(String title) {
		setTitle(title);
	}
	
	/**
	 * Initializes the Demo2 game
	 * 
	 * @param window (Window) : window display context, not null.
	 * @param fileSystem (FileSystem) : given FileSystem, not null.
	 * @param area (Area) : First area to be used
	 * @return (boolean) : always true
	 */
	public boolean begin(Window window, FileSystem fileSystem, Area area) {
		super.begin(window, fileSystem);
		setBehavior(new Demo2Behavior(window, getTitle()));
		registerActor(new Background(area));
		return true;
	}
	
	@Override
	public final float getCameraScaleFactor() {
		return 10;
	}
}
