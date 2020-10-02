/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   8 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.Signal;
import ch.epfl.cs107.play.window.Canvas;


public class PressurePlate extends Switchable implements Signal {

	private float activationTime ;
	private float time;

	/**
	 * Initializes a PressurePlate with default activationTime
	 * 
	 * @param area (Area) : Owner Area of the PressurePlate
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 */
	public PressurePlate(Area area, DiscreteCoordinates position) {
		super("GroundLightOn","GroundPlateOff", null, area, Orientation.DOWN, position, false);
		activationTime= 0.5f;
		time = 0;
	}

	/**
	 * Initializes a PressurePlate 
	 * 
	 * @param area (Area) : Owner Area of the PressurePlate
	 * @param position (DiscreteCoordinates) : Coordinates at spawn
	 * @param activationTime (float) : time limit for the activation of the PressurePlate
	 */
	public PressurePlate(Area area, DiscreteCoordinates position, float activationTime) {
		super("GroundLightOn", "GroundPlateOff", null, area, Orientation.DOWN, position, false);
		setSwitched(false);
		time = 0;
		this.activationTime=activationTime;
	}

	@Override
	public void draw(Canvas canvas) {
		if (getIntensity(timing) == 1f && time <= activationTime) {
			time += 0.01;
			getSpriteOn1().draw(canvas);
		} else {
			time = 0;
			setSwitched(false);
			getSpriteOff().draw(canvas);
		}
	}

	@Override
	public boolean isCellInteractable() {
		return getIntensity(timing) != 1f;
	}
}