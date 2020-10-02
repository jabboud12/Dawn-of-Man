/*
 * Author :   Joseph E. Abboud.
 * Date   :   9 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class EnigmeAnimatedPlayer extends EnigmePlayer{

	private Sprite[] spritesDOWN;
	private Sprite[] spritesLEFT;
	private Sprite[] spritesUP;
	private Sprite[] spritesRIGHT;
	private Sprite[] currentSprite;
	private int step;
	private DiscreteCoordinates lastCoordinates;


	/**
	 * Create an EnigmeAnimatedPlayer
	 * Separate the different sprites associated with each orientation
	 * 
	 * @param title (String) : Title of the Sprite to use
	 * @param area (Area) : Owner Area of the player
	 * @param orientation (Orientation) : Initial orientation of the player
	 * @param position (DiscreteCoordinates) : Initial coordinates at spawn
	 */
	public EnigmeAnimatedPlayer(String title, Area area, Orientation orientation, DiscreteCoordinates position) {
		super(title, area ,orientation,  position);
		step = 0;
		
		Vector anchor = new Vector(0.25f, 0.32f);
		lastCoordinates = position;

		spritesDOWN = new Sprite[4];
		spritesUP = new Sprite[4];
		spritesLEFT = new Sprite[4];
		spritesRIGHT = new Sprite[4];
		currentSprite = new Sprite[4];

		for (int i = 0; i < 4; ++i) {
			spritesDOWN[i] = new Sprite(title , .5f, .65625f, this,
					new RegionOfInterest(0, i * 21, 16, 21), anchor);            
			spritesLEFT[i] = new Sprite(title, .5f, .65625f, this,
					new RegionOfInterest(16, i * 21, 16, 21), anchor);
			spritesUP[i] = new Sprite(title, .5f, .65625f, this,
					new RegionOfInterest(32, i * 21, 16, 21), anchor);
			spritesRIGHT[i] = new Sprite(title, .5f, .65625f, this,
					new RegionOfInterest(48, i * 21, 16, 21), anchor);

			currentSprite[i] = spritesDOWN[i];
		}
	}

	/**
	 * Create an EnigmePlayer.
	 * Give the default Orientation (Orientation.Down)
	 * 
	 * @param title (String) : Title of the Sprite to use
	 * @param area (Area) : Owner Area of the player
	 * @param position (DiscreteCoordinates) : Initial coordinates at spawn
	 */
	public EnigmeAnimatedPlayer(String title, Area area , DiscreteCoordinates position) {
		this(title, area, Orientation.DOWN, position);
	}

	/**
	 * Create an EnigmePlayer.
	 * Give the default Orientation (Orientation.Down)
	 * Give the default Sprite (max.new.1)
	 * 
	 * @param area (Area) : Owner Area of the player
	 * @param position (DiscreteCoordinates) : Initial coordinates at spawn
	 */
	public EnigmeAnimatedPlayer(Area area , DiscreteCoordinates position) {
		this("max.new.1", area, Orientation.DOWN, position);
	}

	/**
	 * Set the Sprite table to use depending on the Orientation of the player
	 */
	public void setCurrentSprite() {
		if (getOrientation().equals(Orientation.DOWN)) {
			currentSprite = spritesDOWN;
		}

		if (getOrientation().equals(Orientation.LEFT)) {
			currentSprite = spritesLEFT;
		}

		if (getOrientation().equals(Orientation.UP)) {
			currentSprite = spritesUP;
		}

		if (getOrientation().equals(Orientation.RIGHT)) {
			currentSprite = spritesRIGHT;
		}

	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (!getCurrentMainCellCoordinates().equals(lastCoordinates)) {
			++step;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		if (!getCurrentMainCellCoordinates().equals(lastCoordinates)) {
			lastCoordinates = getCurrentMainCellCoordinates();
		}
		setCurrentSprite();

		// Here we use this way with the step instead of modulo because it takes less operations 
		// And prevents overflow
		if (step == 4) {
			step = 0;
		}
		currentSprite[step].draw(canvas);
	}
}
