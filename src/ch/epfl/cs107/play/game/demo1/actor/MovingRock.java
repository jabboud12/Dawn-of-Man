/*
 * Author :   Joseph ABBOUD 
 * Date   :   20 Nov 2018
 */

package ch.epfl.cs107.play.game.demo1.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.GraphicsEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


public class MovingRock extends GraphicsEntity {
	public final TextGraphics text_;
	
	/**
	 * Constructor for the class MovingROCK
	 * @param position (Vector) : Rock's vectorial position in the graph
	 * @param text (String) : Text that will be displayed
	 */
	public MovingRock(Vector position, String text) {
		super(position, new ImageGraphics(ResourcePath.getSprite("rock.3"),
						0.1f,0.1f, null, Vector.ZERO, 1.0f, -Float.MAX_VALUE));
		text_ = new TextGraphics(text, 0.04f, Color.BLUE);
		text_.setParent(this);
		text_.setAnchor(new Vector(-0.3f, 0.1f));
	}

	@Override
	public void draw (Canvas canvas) {
		super.draw(canvas);
		text_.draw(canvas);
	}
	
	/**
	 * 
	 * @param deltaTime (float) : this method is called every deltaTime;
	 * @param button (Button) : The button that needs to be pressed for the movement to happen
	 */
	public void updateDown(float deltaTime, Button button) {
		if (button.isDown()) {
			setCurrentPosition(getPosition().sub(0.005f, 0.005f));
		}
	}
}
