/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   30 Nov 2018
 */

package ch.epfl.cs107.play.game.enigme.actor.demo2;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.area.demo2.Demo2Behavior.Demo2CellType;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

@SuppressWarnings("ALL")
public class Demo2Player extends MovableAreaEntity{
	private boolean isPassingDoor;
	private Sprite sprite;
	private final static int ANIMATION_DURATION = 8;

	/**
	 * Enter the current Area
	 * @param area (Area) : The Current Area where the player will enter
	 * @param position (DiscreteCoordinates) : Spawn coordinates of the player
	 */
	public void enterArea(Area area, DiscreteCoordinates position) {
		area.registerActor(this);
		setOwnerArea(area);
		Vector v = new Vector(position.x, position.y);
		super.setCurrentPosition(v);
		resetMotion();
	}
	
	/**
	 * Leave the current Area
	 * @param area (Area) : The Current Area from where the player will leave
	 */
	public void leaveArea(Area area) {
		area.unregisterActor(this);
	}
	
	/**
	 * Creates a new Demo2Player
	 * 
	 * @param area (Area) : Owner Area of the player
	 * @param orientation (Orientation) : Orientation of the player
	 * @param coordinates (DiscreteCoordinates) : Starting coordinates of the player 
	 */
	public Demo2Player(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
		super(area, orientation, coordinates);
		sprite = new Sprite("ghost.1", 1f, 1f, this);
	}
	
	/**
	 * Creates a new Demo2Player
	 * 
	 * @param area (Area) : Owner Area of the player
	 * @param orientation (Orientation) : Orientation of the player
	 * @param coordinates (DiscreteCoordinates) : Starting coordinates of the player 
	 */
	@SuppressWarnings("JavadocReference")
	public Demo2Player(Area area, DiscreteCoordinates coordinates) {
		this(area, Orientation.DOWN, coordinates);
	}
	
	@Override
	public void update(float deltaTime) {
		Keyboard keyboard = getOwnerArea().getKeyboard();
		Button leftArrow = keyboard.get(Keyboard.LEFT);
		Button rightArrow = keyboard.get(Keyboard.RIGHT);
		Button upArrow = keyboard.get(Keyboard.UP);
		Button downArrow = keyboard.get(Keyboard.DOWN);
		
		super.update(deltaTime);
		if (leftArrow.isDown()) {
			if (getOrientation() == Orientation.LEFT) {
				move(ANIMATION_DURATION);
				
			} else {
				setOrientation(Orientation.LEFT);
			}
		}
		if (rightArrow.isDown()) {
			if (getOrientation() == Orientation.RIGHT) {
				move(ANIMATION_DURATION);
			} else {
				setOrientation(Orientation.RIGHT);
			}
		}
		if (upArrow.isDown()) {
			if (getOrientation() == Orientation.UP) {
				move(ANIMATION_DURATION);
			} else {
				setOrientation(Orientation.UP);
			}
		}
		if (downArrow.isDown()) {
			if (getOrientation() == Orientation.DOWN) {
				move(ANIMATION_DURATION);
			} else {
				setOrientation(Orientation.DOWN);
			}
		}
	}
	
	@Override
	protected boolean move(int framesForMove) {

		isPassingDoor = (getOwnerArea().getBehavior().getDemo2Cells()[getCurrentMainCellCoordinates().x]
				[getCurrentMainCellCoordinates().y].getType()).equals(Demo2CellType.DOOR); 				
		super.move(framesForMove);
		return true;		
	}
	
	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
		}

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		sprite.draw(canvas);
	}
	
	/** @return (boolean) : true if the player is passing a door, else false */
	public boolean getIsPassingDoor() {
		return isPassingDoor;
	}
	
	/**
	 * Setter for the door the player is passing;
	 * @param b
	 */
	public void setIsPassingDoor(boolean b) {
		isPassingDoor = b;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// This method stays empty		
	}
	
	@Override
	public Logic getSignal() {
		//This method stays empty. it is not used in the game Demo2
		return null;
	}
}
