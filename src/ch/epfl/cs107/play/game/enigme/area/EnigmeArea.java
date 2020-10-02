/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   2 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.enigme.EnigmeBehavior;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.window.Window;

public class EnigmeArea extends Area{
	
	/**
	 * Set the title of the Area
	 * @param title (String) : title of the current area
	 */
	public EnigmeArea(String title) {
		super.setTitle(title);
	}
	
	/**
	 * Initializes the Enigme game
	 * 
	 * @param window (Window) : window display context, not null.
	 * @param fileSystem (FileSystem) : given FileSystem, not null.
	 * @param area (Area) : First area to be used
	 * @return (boolean) : always true
	 */
	public boolean begin(Window window, FileSystem fileSystem, Area area) {
		super.begin(window, fileSystem);
		setBehavior(new EnigmeBehavior(window, getTitle()));
		registerActor(new Background(area));
		return true;
	}
	
	
	@Override
	public float getCameraScaleFactor() {
		return 20;
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
}
