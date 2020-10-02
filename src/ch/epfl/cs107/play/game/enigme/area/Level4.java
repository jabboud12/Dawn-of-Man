package ch.epfl.cs107.play.game.enigme.area;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.enigme.actor.Collectable;
import ch.epfl.cs107.play.game.enigme.actor.Dialog;
import ch.epfl.cs107.play.game.enigme.actor.Dice;
import ch.epfl.cs107.play.game.enigme.actor.PressurePlate;
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
import ch.epfl.cs107.play.window.Window;

public class Level4 extends EnigmeArea {

	private SignalSwitchable boy;

	private SignalDoor door1;

	private SignalCollectable palm1, palm2, palm3, palm4;
	private SignalCollectable environment1, environment2, environment3, environment4, environment5;
	private SignalCollectable apple2;
	private SignalCollectable sword2;
	private SignalCollectable ingot2;

	private Switchable lever1, lever2, lever3, lever4;
	private Switchable switch1, switch2, switch3, switch4, switch5, switch6, switch7, switch8, switch9;

	private SignalSwitchable sword1, apple1, ingot1, bush;

	private PressurePlate plate;

	private Collectable axe;


	private Dice die1, die2;

	private SignalCollectable paper;

	private Dialog dialog1;

	private List<AreaEntity> entitiesForBush;

	/**
	 * Constructor for Level4 in the game.
	 */
	 public Level4() {
		 super("Enigme2");
	 }

	 @Override
	 public boolean begin(Window window, FileSystem fileSystem) {
		 DiscreteCoordinates[] cells = {};

		 super.begin(window, fileSystem, this);
		 //Add dialog: Help!! the mob went crazy. It's been ... he's in the water. All he can do bow is imitate your movements, or at least try to. Help him before he becomes even more brain damaged.

		 axe = new Collectable("axe.1", this, new DiscreteCoordinates(10, 9));

		 boy = new SignalSwitchable("drowning.boy.1",null, "drowning.boy.2", this, new DiscreteCoordinates(7, 7),Logic.TRUE ,true);

		 palm1 = new SignalCollectable("palm.tree", this, new DiscreteCoordinates(5, 11), axe);        
		 palm2 = new SignalCollectable("palm.tree", this, new DiscreteCoordinates(2, 3), axe);
		 palm3 = new SignalCollectable("palm.tree", this, new DiscreteCoordinates(2, 4), axe);
		 palm4 = new SignalCollectable("palm.tree", this, new DiscreteCoordinates(1, 4), axe);

		 environment1 = new SignalCollectable("environment_16", this, new DiscreteCoordinates(5, 10), Logic.FALSE);
		 environment2 = new SignalCollectable("environment_16", this, new DiscreteCoordinates(9, 10), Logic.FALSE);
		 environment3 = new SignalCollectable("environment_16", this, new DiscreteCoordinates(9, 11), Logic.FALSE);
		 environment4 = new SignalCollectable("environment_16", this, new DiscreteCoordinates(13, 6), Logic.FALSE);
		 environment5 = new SignalCollectable("environment_16", this, new DiscreteCoordinates(13, 8), Logic.FALSE);


		 lever1 = new Switchable("lever.big.left", "lever.big.right", null, this, new DiscreteCoordinates(2, 6), true);
		 lever2 = new Switchable("lever.big.left", "lever.big.right", null, this, new DiscreteCoordinates(2, 7), true);
		 lever3 = new Switchable("lever.big.left", "lever.big.right", null, this, new DiscreteCoordinates(2, 8), true);       
		 lever4 = new Switchable("lever.big.left", "lever.big.right", null, this, new DiscreteCoordinates(12, 7), true);

		 die1 = new Dice(this, new DiscreteCoordinates(5, 7));
		 die2 = new Dice(this, new DiscreteCoordinates(9, 7));        

		 // For these SignalCollectables we put axe as a signal reference but it won't affect them because their signal is set in the updateEntities.
		 apple2 = new SignalCollectable("apple.1",this, new DiscreteCoordinates(1, 3), axe);        
		 sword2 = new SignalCollectable("sword.1",this, new DiscreteCoordinates(13, 3), axe);        
		 ingot2 = new SignalCollectable("ingot.1",this, new DiscreteCoordinates(7, 4), axe);

		 sword1 = new SignalSwitchable("sword.1","environment_16", null, this, new DiscreteCoordinates(1, 6), sword2, true);
		 apple1 = new SignalSwitchable("apple.1","environment_16", null, this, new DiscreteCoordinates(1, 7), apple2, true);
		 ingot1 = new SignalSwitchable("ingot.1","environment_16", null, this, new DiscreteCoordinates(1, 8), ingot2, true);

		 entitiesForBush = Arrays.asList(sword1, apple1, ingot1);
		 bush = new SignalSwitchable("berry.1","environment_16", null, this, new DiscreteCoordinates(13, 7), entitiesForBush, true);

		 switch1 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(6, 9), false);
		 switch2 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(7, 9), false);
		 switch3 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(8, 9 ), false);
		 switch4 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(6, 10), false);
		 switch5 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(7, 10), false);
		 switch6 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(8, 10), false);
		 switch7 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(6, 11), false);
		 switch8 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(7, 11), false);
		 switch9 = new Switchable("GroundLightOn", "GroundLightOff",null, this, new DiscreteCoordinates(8, 11), false);

		 door1 = new SignalDoor(this, "LevelSelector", new DiscreteCoordinates(4,6), new DiscreteCoordinates(7,0), cells, Logic.TRUE);

		 dialog1 = new Dialog("Roll the dice with L", "dialog.1", this, 1f);
		 this.addDialog(dialog1);

		 paper = new SignalCollectable("paper.3", this, new DiscreteCoordinates(1, 10), axe);



		 return true;
	 }

	 /**
	  * Actualize the signal of compound propositions inside the method
	  */
	 public void updateEntities(){

		 Logic signal1 = new MultipleAnd(new Not(lever1.getSignal()), lever2.getSignal(), new Not(lever3.getSignal()),
				 lever4.getSignal());
		 paper.setSignal(signal1);

		 Logic signal2 = new MultipleAnd(new Not(switch1.getSignal()), new Not(switch2.getSignal()), switch3.getSignal(),
				 new Not(switch4.getSignal()), new Not(switch5.getSignal()), switch6.getSignal(),
				 new Not(switch7.getSignal()), new Not(switch8.getSignal()), switch9.getSignal());
		 door1.setSignal(signal2);

		 if (die1.getN() == die2.getN()){
			 sword2.setSignal(Logic.TRUE);
			 ingot2.setSignal(Logic.TRUE);
			 apple2.setSignal(Logic.TRUE); 

		 }else{
			 sword2.setSignal(Logic.FALSE);
			 ingot2.setSignal(Logic.FALSE);
			 apple2.setSignal(Logic.FALSE);

		 }

		 if (paper.getSignal().isOn()) {
			 dialog1.resetDialog("001001001");
			 setCameraScaleFactor(20);
		 }

		 if (signal2.isOn()) {
			 dialog1.resetDialog("Oh nooo, the boy has drowned !     What a bummer!");
			 boy.setSignal(Logic.FALSE);
		 }
	 }

}
