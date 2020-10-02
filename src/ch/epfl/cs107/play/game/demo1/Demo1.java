/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   20 Nov 2018
 */

package ch.epfl.cs107.play.game.demo1;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.GraphicsEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.demo1.actor.MovingRock;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;



public class Demo1 implements Game {
	
	private Actor actor1;
	private MovingRock rock;
	private Window window;
	private FileSystem fileSystem;
	
	@Override
	public String getTitle() {
		return "Demo1";
	}
	
	@Override
	public int getFrameRate() {
		return 44;
	}
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		Transform viewTransform = Transform.I.scaled(1).translated(new Vector(0f, 0f));
		window.setRelativeTransform(viewTransform);
		this.fileSystem = fileSystem;
		this.actor1 = new GraphicsEntity(Vector.ZERO,
				new ShapeGraphics(new Circle(0.2f), null,
				Color.RED, 0.005f));
		this.rock = new MovingRock(new Vector(0.2f, 0.2f), "Hello, I'm a moving rock!");
		return true;
	}
	
	@Override
	public void update(float deltaTime) {
		actor1.draw(window);
		rock.draw(window);
		Keyboard keyboard = window.getKeyboard();
		Button downArrow = keyboard.get(Keyboard.DOWN);

		rock.updateDown(deltaTime, downArrow);
		
		if (rock.getPosition().getLength() <= 0.2f) {
			TextGraphics boom = new TextGraphics("BOOM!!!", 0.08f, Color.RED);
			boom.setAnchor(new Vector(-0.15f, -0.3f));
			boom.draw(window);
		}
	}
	
	@Override
	public void end() {}

}
