/*
 * Author :   Joseph E. Abboud.
 * Date   :   9 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.enigme.actor.Collectable;
import ch.epfl.cs107.play.game.enigme.actor.Portal;
import ch.epfl.cs107.play.game.enigme.actor.SignalCollectable;
import ch.epfl.cs107.play.game.enigme.actor.SignalDialog;
import ch.epfl.cs107.play.game.enigme.actor.SignalDoor;
import ch.epfl.cs107.play.game.enigme.actor.SignalSwitchable;
import ch.epfl.cs107.play.game.enigme.actor.Switchable;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.signal.logic.MultipleAnd;
import ch.epfl.cs107.play.signal.logic.Not;
import ch.epfl.cs107.play.signal.logic.Or;
import ch.epfl.cs107.play.window.Window;

public class Level5 extends EnigmeArea{
	private SignalDoor door1, door2;

	private Switchable torch1, torch2;
	private Switchable demon;
	private Switchable lever1, lever2, lever3, lever4, lever5, lever6;
	
	private Collectable sword;
	private Collectable bow;
    private Collectable paper;

	private SignalSwitchable rock1, rock2, rock3;
	private SignalSwitchable bush1, bush2, bush3;

	private Portal portal1, portal2, portal3, portal4, portal5, portal6;

	private SignalCollectable explosion1, explosion2;
	private SignalCollectable fence1, fence2, fence3, fence4, fence5;
	private SignalCollectable soldier1, soldier2, soldier3;
	private SignalCollectable grape, heart, oldMan, littleGirl;
	
	private SignalDialog dialog1;

	private List<AreaEntity> entitiesForRock1;


	/**
	 * Constructor for Level5 in the game.
	 */
	public Level5() {
		super("Enigme1");
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		DiscreteCoordinates[] cells = {};

		super.begin(window, fileSystem, this);

		torch1 = new Switchable("torch.wall.on.1", "torch.wall.off", "torch.wall.on.2", this, new DiscreteCoordinates(4,31), true);
		torch2 = new Switchable("torch.wall.on.1", "torch.wall.off", "torch.wall.on.2", this, new DiscreteCoordinates(8,31), true);


		entitiesForRock1 = Arrays.asList(torch1, torch2);
		rock1 = new SignalSwitchable(null,"rock.3", null, this, new DiscreteCoordinates(6, 27), entitiesForRock1, true);

		door1 = new SignalDoor(this, "LevelSelector", new DiscreteCoordinates(5, 6), new DiscreteCoordinates(6,32), cells, Logic.FALSE);
		
		lever1 = new Switchable("lever.big.left", "lever.big.right",null, this, new DiscreteCoordinates(1, 16), true);
		lever2 = new Switchable("lever.big.left", "lever.big.right",null, this, new DiscreteCoordinates(1, 15), true);
		lever3 = new Switchable("lever.big.left", "lever.big.right",null, this, new DiscreteCoordinates(1, 14), true);
		lever4 = new Switchable("lever.big.left", "lever.big.right",null, this, new DiscreteCoordinates(1, 13), true);
		lever5 = new Switchable("lever.big.left", "lever.big.right",null, this, new DiscreteCoordinates(1, 12), true);
		lever6 = new Switchable("lever.big.left", "lever.big.right",null, this, new DiscreteCoordinates(1, 11), true);
		
		bush1 = new SignalSwitchable(null,"berry.1", null, this, new DiscreteCoordinates(11, 11), lever1, true);
		bush2 = new SignalSwitchable(null,"berry.1", null, this, new DiscreteCoordinates(12, 12), lever3, true);
		bush3 = new SignalSwitchable(null,"berry.1", null, this, new DiscreteCoordinates(11, 12), lever5, true);
		
		sword = new Collectable("sword.2", this, new DiscreteCoordinates(23, 30));
		bow = new Collectable("bow.1", this, new DiscreteCoordinates(17, 24));
		soldier3 = new SignalCollectable("soldier.2", this, new DiscreteCoordinates(14, 36), Logic.TRUE);
		
		demon = new Switchable("demon.1", "demon.1","demon.2", this, new DiscreteCoordinates(16, 4), true);
		soldier1 = new SignalCollectable("soldier.2", this, new DiscreteCoordinates(15, 4), Logic.FALSE);
		soldier2 = new SignalCollectable("soldier.2", this, new DiscreteCoordinates(17, 4), Logic.FALSE);
		rock2 = new SignalSwitchable(null,"rock.3", null, this, new DiscreteCoordinates(18, 5), demon, true);
		
		portal1 = new Portal(this, "", new DiscreteCoordinates(6, 16), new DiscreteCoordinates(4, 24), cells, entitiesForRock1);		
		portal4 = new Portal(this, "", new DiscreteCoordinates(6, 21), new DiscreteCoordinates(12, 11), cells, bush3);
		portal5 = new Portal(this, "", new DiscreteCoordinates(6, 21), new DiscreteCoordinates(22, 1), cells, rock2);
		portal6 = new Portal(this, "", new DiscreteCoordinates(16, 2), new DiscreteCoordinates(19, 37), cells, soldier3);
		portal2 = new Portal(this, "", new DiscreteCoordinates(6, 8), new DiscreteCoordinates(6, 24), cells, portal4);
		portal3 = new Portal(this, "", new DiscreteCoordinates(21, 7), new DiscreteCoordinates(8, 24), cells, portal5);


		explosion2 = new SignalCollectable("blue.explosion", this, new DiscreteCoordinates(7, 24), Logic.FALSE);
		explosion1 = new SignalCollectable("blue.explosion", this, new DiscreteCoordinates(5, 24), Logic.FALSE);

		fence1 = new SignalCollectable("fence.west", this, new DiscreteCoordinates(7, 17), Logic.FALSE);
		fence2 = new SignalCollectable("fence.east", this, new DiscreteCoordinates(6, 17), Logic.FALSE);
		fence3 = new SignalCollectable("fence.west", this, new DiscreteCoordinates(7, 9), Logic.FALSE);
		fence4 = new SignalCollectable("fence.east", this, new DiscreteCoordinates(6, 9), Logic.FALSE);
		fence5 = new SignalCollectable("fence", this, new DiscreteCoordinates(21, 6), Logic.FALSE);
		
		paper = new Collectable("paper.3", this, new DiscreteCoordinates(1, 1));
		grape = new SignalCollectable("grape.1", this, new DiscreteCoordinates(12, 8), paper);
		heart = new SignalCollectable("heart.1", this, new DiscreteCoordinates(1, 8), Logic.FALSE);
		oldMan = new SignalCollectable("old.man", this, new DiscreteCoordinates(12, 1), Logic.FALSE);
		littleGirl = new SignalCollectable("little.girl", this, new DiscreteCoordinates(6, 4), Logic.FALSE);
		
		rock3 = new SignalSwitchable(null,"rock.3", null, this, new DiscreteCoordinates(14, 5), grape, true);

		dialog1 = new SignalDialog("They did surgery on a...", "dialog.1", this, 0f, paper);
		this.addDialog(dialog1);

		// cells1 is the set of all cells that can be interacted with
		DiscreteCoordinates[] cells1 = {new DiscreteCoordinates(15,0), new DiscreteCoordinates(16,0), new DiscreteCoordinates(17,0)}; 
		door2 = new SignalDoor(this, "LevelSelector", new DiscreteCoordinates(5, 6), new DiscreteCoordinates(15,0), cells1, Logic.TRUE,3f);

		return true;
	}

	/**
	 * Actualize the signal of compound propositions inside the method
	 */
	public void updateEntities() {
		Logic signal1 = new MultipleAnd(lever1.getSignal(), new Not(lever2.getSignal()), lever3.getSignal(),
										new Not(lever4.getSignal()), lever5.getSignal(), new Not(lever6.getSignal()));
		bush3.setSignal(signal1);
		
		if (grape.getSignal().isOn()) {
			dialog1.resetDialog("...grape");
		}
		
		if (demon.getSignal().isOn()) {
			dialog1.resetDialog("No one shall pass!");
		}
		
		if (portal6.getSignal().isOn()) {
			dialog1.resetDialog("NANI ?!! How did you get there?!   Anyway, do your part and subscribe to Pewdiepie.");
		}
		
		soldier3.setSignal(new Or(sword.getSignal(), bow.getSignal()));
		
	}
	
	@Override
	public float getCameraScaleFactor() {
		return 10;
	}
	
}
