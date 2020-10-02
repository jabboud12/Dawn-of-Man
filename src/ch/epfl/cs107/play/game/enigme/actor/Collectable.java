/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   5 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Collectable extends AreaEntity {

	private boolean pickedUp;
	private Sprite sprite;

	/**
	 * Initialize a Collectable
	 * 
	 * @param title (String) : Title of the Sprite to use
	 * @param area (Area) : Owner Area of the Collectable
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 */
	public Collectable(String title, Area area, DiscreteCoordinates position) {
		super(area, Orientation.DOWN, position);
		sprite = new Sprite(title, 1f, 1f, this);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());

	}

	/**
	 * Setter for pickedUp (in general, true if the Collectable is pickedUp, else false)
	 * @param b (boolean)
	 */
	public void setCollected(boolean b) {
		pickedUp = b;
	}

	/**
	 * Getter for pickedUp
	 * @return (boolean) : true if the Entity is collected, else false
	 */
	public boolean getCollected() {
		return pickedUp;
	}

	/**
	 * Getter for the Sprite representing the Collectable (used in lower classes)
	 * @return (Sprite) : Current Sprite
	 */
	public Sprite getSprite(){
		return sprite;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (pickedUp) {
			leaveArea();
		}
	}

	@Override
	public boolean takeCellSpace() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		return false;
	}

	/**
	 * Leave the Owner Area
	 */
	public final void leaveArea() {
		getOwnerArea().unregisterActor(this);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		if (!getCollected()) {
			sprite.draw(canvas);
		}
	}

	/**
	 *  Getter for the Intensity of this Collectable's signal
	 *  
	 * @param t (float) : time at which we want the Intensity
	 * @return (float) : 1.0f if the Entity is pickedUp, else 0.0f 
	 */
	public float getIntensity(float t) {
		if (getCollected()) {
			return 1.0f;
		}
		return 0.0f;
	}

	@Override
	public Logic getSignal() {
		if (getIntensity(timing) == 1f) {
			return Logic.TRUE;
		}
		return Logic.FALSE;
	}

}
