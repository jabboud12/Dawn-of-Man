/*
 * Author :   Joseph E. Abboud.
 * Date   :   12 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class AnimatedCoin extends Collectable{
	
	// Array of sprites for the AnimatedCoin
	private final Sprite[] sprites = {new Sprite("coin1", 1f, 1f, this),
									  new Sprite("coin2", 1f, 1f, this),
									  new Sprite("coin3", 1f, 1f, this),
									  new Sprite("coin4", 1f, 1f, this),};
	private int step1;
	private int step2;

	/**
	 * Initializing the AnimatedCoin
	 * @param area (Area) : Owner area of the AnimatedCoin
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 */
	public AnimatedCoin(Area area, DiscreteCoordinates position) {
		super("coin.1", area, position);		
		step1 = 0;
		step2 = 0;
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
		if(getIntensity(timing) == 0f) {
			for (int i=0 ; i<4 ; ++i) {
				if(step2 % 4 == i) {
					sprites[i].draw(canvas);
				}
			}
		}		
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		++step1;
		// this if() serves to slow down the movement of the coin
		if(step1 % 5 == 0) {
			++step2;
		}
	}

}
