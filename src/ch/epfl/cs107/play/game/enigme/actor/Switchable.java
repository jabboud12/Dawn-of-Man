/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   7 Dec 2018
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
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Switchable extends AreaEntity {

	// private Logic signal;
	private boolean switched;
	private Sprite spriteOn1;
	private Sprite spriteOn2;
	private Sprite spriteOff;
	private boolean takeCellSpace;
	private float animationTime;

	/**
	 * Initialize a Switchable
	 * 
	 * @param titleOn1 (String) : Title of the SpriteOn1 to use, can be null
	 * @param titleOff (String) : Title of the SpriteOff to use, can be null
	 * @param titleOn2 (String) : Title of the SpriteOn2 to use if wanting an animation, can be null
	 * @param area (Area) : Owner Area of the Switchable
	 * @param orientation (Orientation) : orientation at spawn
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 * @param b (boolean) : true if the Entity is not traversable, else false
	 */
	public Switchable(String titleOn1, String titleOff, String titleOn2, Area area, Orientation orientation, DiscreteCoordinates position, boolean b) {
		super(area, orientation, position);
		if (titleOn1 != null) {
			spriteOn1 = new Sprite(titleOn1, 1f, 1f, this);
		}
		if (titleOff != null) {
			spriteOff = new Sprite(titleOff, 1f, 1f, this);
		}
		if (titleOn2 != null) {
			spriteOn2 = new Sprite(titleOn2, 1f, 1f, this);
		}        
		switched = false;
		takeCellSpace = b;
		animationTime = 0;
	}

	/**
	 * Initialize a Switchable with Orientation at DOWN
	 * 
	 * @param titleOn1 (String) : Title of the SpriteOn1 to use, can be null
	 * @param titleOff (String) : Title of the SpriteOff to use, can be null
	 * @param titleOn2 (String) : Title of the SpriteOn2 to use if wanting an animation, can be null
	 * @param area (Area) : Owner Area of the Switchable
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 * @param b (boolean) : true if the Entity is not traversable, else false
	 */
	public Switchable(String titleOn1, String titleOff, String titleOn2, Area area, DiscreteCoordinates position, boolean b) {
		this(titleOn1, titleOff, titleOn2, area, Orientation.DOWN, position, b);
	}

	public Switchable(String titleOn1, String titleOff, String titleOn2, Area area, DiscreteCoordinates position, boolean b,
					RegionOfInterest roi, Vector anchor, float width, float height) {
		this(titleOn1, titleOff, titleOn2, area, Orientation.DOWN, position, b);
		
		if (titleOn1 != null) {
			spriteOn1 = new Sprite(titleOn1, width, height, this, roi, anchor);
		}
		if (titleOff != null) {
			spriteOff = new Sprite(titleOff, width, height, this, roi, anchor);
		}
		if (titleOn2 != null) {
			spriteOn2 = new Sprite(titleOn2, width, height, this, roi, anchor);
		} 
	}
	@Override
	public void draw(Canvas canvas) {
		if(spriteOn2 == null) {
			if (getIntensity(timing) == 1f && spriteOn1 != null) {
				spriteOn1.draw(canvas);		
			} else if(getIntensity(timing) == 0f && spriteOff != null){
				spriteOff.draw(canvas);		
			}
		} else {
			animationTime += .05f;
			if (getIntensity(1) == 1f) {
				if (animationTime <= 1f && spriteOn1 != null) {
					spriteOn1.draw(canvas);
				}else if (animationTime > 1 && animationTime < 2){
					spriteOn2.draw(canvas);
				}else{
					spriteOn2.draw(canvas);
					animationTime = 0;
				}
			}else if (getIntensity(timing) == 0f && spriteOff != null){
				spriteOff.draw(canvas);
			}
		}
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());

	}

	@Override
	public boolean takeCellSpace() {
		return takeCellSpace;
	}

	@Override
	public boolean isViewInteractable() {
		if (timing >= .1f && takeCellSpace) {
			timing = 0;
			return true;
		}
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		if (timing >= .5f && !takeCellSpace) {
			timing = 0;
			return true;
		}
		return false;
	}

	@Override
	public Logic getSignal() {
		if (switched) {
			return Logic.TRUE;
		}
		return Logic.FALSE;

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		timing += deltaTime;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((EnigmeInteractionVisitor) v).interactWith(this);

	}

	/**
	 * @param t (float) : time at which we want the Intensity
	 * @return (float) : 1.0f if the Entity is switched on, else 0.0f 
	 */
	public float getIntensity(float t) {
		if(getSignal().isOn()) {
			return 1f;
		}
		return 0f;
	}

	/**
	 * Setter for the boolean determining if the Entity is switched on or not
	 * @param b (boolean) : true if want to switch on, else false
	 */
	public void setSwitched(boolean b) {
		switched = b;
	}

	/**
	 * Getter of the Sprite representing the first switched on Entity
	 * @return (Sprite)
	 */
	public Sprite getSpriteOn1() {
		return spriteOn1;
	}

	/**
	 * Getter of the Sprite representing the second switched on Entity
	 * @return (Sprite)
	 */
	protected Sprite getSpriteOn2() {
		return spriteOn2;
	}

	/**
	 * Getter of the Sprite representing the switched off Entity
	 * @return (Sprite)
	 */
	protected Sprite getSpriteOff() {
		return spriteOff;
	}
}
