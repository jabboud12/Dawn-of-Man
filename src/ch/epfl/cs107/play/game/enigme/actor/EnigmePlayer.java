/*
 * Author :   Joseph ABBOUD 
 * Date   :   2 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.enigme.handler.EnigmeInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.Not;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class EnigmePlayer extends MovableAreaEntity implements Interactor {

    private class EnigmePlayerHandler implements EnigmeInteractionVisitor {
    	
    	@Override
        public void interactWith(Door door) {
            setIsPassingDoor(door);
        }

    	@Override
        public void interactWith(Collectable item) {
            item.setCollected(true);
        }

    	@Override
        public void interactWith(Switchable item) {
            item.setSwitched(new Not(item.getSignal()).isOn());
        }
    }

    private Door door;
    private Sprite sprite;
    private int health;
    private Sprite heart1;
    private Sprite heart2;
    private Sprite heart3;
    
    private final Sprite firstSprite;

    private final static int ANIMATION_DURATION = 8;

    private final EnigmePlayerHandler handler;

    /**
     * Enter the specified Area
     * @param area (Area) : Area to enter
     * @param position (DiscreteCoordinates) : Initial position at spawn
     */
    public void enterArea(Area area, DiscreteCoordinates position) {
    	area.registerActor(this);
    	setOwnerArea(area);
    	Vector v = new Vector(position.x, position.y);
    	super.setCurrentPosition(v);
    	health = 3;
    	resetMotion();
    }

    /**
     * Leave the specified Area
     * @param area (Area) : Area to leave
     */
    public void leaveArea(Area area) {
    	area.unregisterActor(this);
    }

    /**
     * Create an EnigmePlayer
     * 
     * @param title (String) : Title of the Sprite to use
     * @param area (Area) : Owner Area of the player
     * @param orientation (Orientation) : Initial orientation of the player
     * @param coordinates (DiscreteCoordinates) : Initial coordinates at spawn
     */
    public EnigmePlayer(String title, Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);       
        handler = new EnigmePlayerHandler();
        sprite = new Sprite(title, 1f, 1f, this);
        firstSprite = sprite;
        heart1 = new Sprite("heart.1", 0.5f, 0.5f, this);
        heart1.setAnchor(new Vector(-0.05f, 0.5f));
    	getOwnerArea().setMainPlayer(this);
    }

    
    /**
     * Create an EnigmePlayer.
     * Give the default Orientation (Orientation.Down)
     * 
     * @param title (String) : Title of the Sprite to use
     * @param area (Area) : Owner Area of the player
     * @param coordinates (DiscreteCoordinates) : Initial coordinates at spawn
     */
    public EnigmePlayer(String title, Area area, DiscreteCoordinates coordinates) {
        this(title, area, Orientation.DOWN, coordinates);
    }
    
    /**
     * Create an EnigmePlayer.
     * Give the default Orientation (Orientation.Down)
     * Give the default Sprite (ghost.1)
     * 
     * @param area (Area) : Owner Area of the player
     * @param coordinates (DiscreteCoordinates) : Initial coordinates at spawn
     */
    public EnigmePlayer(Area area, DiscreteCoordinates coordinates) {
        this("ghost.1", area, Orientation.DOWN, coordinates);
    }

    @Override
    public void update(float deltaTime) {

        Keyboard keyboard = getOwnerArea().getKeyboard();
        
        Button upArrow = keyboard.get(Keyboard.UP);
        Button leftArrow = keyboard.get(Keyboard.LEFT);
        Button downArrow = keyboard.get(Keyboard.DOWN);
        Button rightArrow = keyboard.get(Keyboard.RIGHT);
      
        Button wKey = keyboard.get(Keyboard.W);
        Button aKey = keyboard.get(Keyboard.A);
        Button sKey = keyboard.get(Keyboard.S);
        Button dKey = keyboard.get(Keyboard.D);
        
        super.update(deltaTime);
        
        wantsCellInteraction();

        if (leftArrow.isDown() || aKey.isDown()) {

            if (getOrientation() == Orientation.LEFT) {
                move(ANIMATION_DURATION);
            } else {
                setOrientation(Orientation.LEFT);
            }
        }

        if (rightArrow.isDown() || dKey.isDown()) {

            if (getOrientation() == Orientation.RIGHT) {
                move(ANIMATION_DURATION);
            } else {
                setOrientation(Orientation.RIGHT);
            }
        }

        if (upArrow.isDown() || wKey.isDown()) {

            if (getOrientation() == Orientation.UP) {
                move(ANIMATION_DURATION);
            } else {
                setOrientation(Orientation.UP);
            }
        }

        if (downArrow.isDown() || sKey.isDown()) {

            if (getOrientation() == Orientation.DOWN) {
                move(ANIMATION_DURATION);
            } else {
                setOrientation(Orientation.DOWN);
            }
        }

        if (wantsViewInteraction()) {
            getOwnerArea().getBehavior().viewInteractionOf(this);
        }


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
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
        //heart1.draw(canvas);
    }
    
    public void setSprite(Sprite s) {
    	sprite = s;

    }
    
    public void resetSprite() {
    	sprite = firstSprite;
    }
    
    /**
     * Setter for the door the player is passing
     * 
     * @param door (Door) : Door to be passed
     */
    public void setIsPassingDoor(Door door) {
        this.door = door;
    }

    /**
     * Getter for the door the player has passed last
     * 
     * @return (Door)
     */
    public Door passedDoor() {
        return door;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return getEnteringCells();
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        Button spaceKey = keyboard.get(Keyboard.SPACE);
        return spaceKey.isPressed();
    }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(handler);
    }

    
    @Override 
    public Logic getSignal() {
    	// This method stays empty
    	return null;
    }
    /**
     *  Player loses a health point
     */
    public void loseLife() {
    	--health;
    }

}
