package ch.epfl.cs107.play.game.enigme.actor;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;



/**
 * Author : Joseph ABBOUD & Zad ABI FADEL
 * Date : 2018-12-10
 */
public class Dice extends Switchable   {
	private Sprite[] sprites;
	private int n;

	/**
	 * Initializing a randomly generated die
	 * 
	 * @param area (Area) : Owner Area of the die
	 * @param position (DiscreteCoordinates) : position at spawn
	 */
	public Dice(Area area, DiscreteCoordinates position) {
		super(null, null, null,area, Orientation.DOWN, position, true);
		Random rand = new Random();

		sprites = new Sprite[6];
		sprites[0] = new Sprite("dieRed1", 1f, 1f, this);
		sprites[1] = new Sprite("dieRed2", 1f, 1f, this);
		sprites[2] = new Sprite("dieRed3", 1f, 1f, this);
		sprites[3] = new Sprite("dieRed4", 1f, 1f, this);
		sprites[4] = new Sprite("dieRed5", 1f, 1f, this);
		sprites[5] = new Sprite("dieRed6", 1f, 1f, this);
		
		n = rand.nextInt(6);
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
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

	@Override
	public void draw(Canvas canvas) {
		Keyboard keyboard = getOwnerArea().getKeyboard();
		Button lKey = keyboard.get(Keyboard.L);
		if (lKey.isDown()) {
			Random rand = new Random();
			n = rand.nextInt(6) ;
		}
		sprites[n].draw(canvas);
	}
	/**
	 * Getter for the index of the Array of sprites
	 * @return (int) : the index
	 */
	public int getN(){
		return n;
	}
}
