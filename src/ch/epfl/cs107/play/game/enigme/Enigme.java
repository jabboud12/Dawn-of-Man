package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.enigme.actor.EnigmeAnimatedPlayer;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.game.enigme.area.*;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;



/**
 * Enigme Game is a concept of Game derived for AreaGame. It introduces the notion of Player
 * When initializing the player is added to the current area
 */
public class Enigme extends AreaGame {
	private Area levelSelector;
	private Area level1;
	private Area level2;
	private Area level3;
	private Area level4;
	private Area level5;
	private Area pause;
	private Area previousArea;
	private DiscreteCoordinates previousCoordinates;

	private EnigmePlayer playerMain;

	private int counter0 = 0;
	private int counter1 = 0;
	private int counter2 = 0;
	private int counter3 = 0;
	private int counter4 = 0;




	/// Enigme implements Playable

	@Override
	public String getTitle() {
		return "Dawn Of MAN";
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);

		levelSelector = new LevelSelector();
		level1 = new Level1();
		level2 = new Level2();
		level3 = new Level3();
		level4 = new Level4();
		level5 = new Level5();
		pause = new Pause();

		addArea(levelSelector);
		addArea(level1);
		addArea(level2);
		addArea(level3);
		addArea(level4);
		addArea(level5);
		addArea(pause);




		playerMain = new EnigmeAnimatedPlayer("max.new.1", getCurrentArea(), new DiscreteCoordinates(5,5));

		getCurrentArea().setViewCandidate(playerMain);

		return true;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		if (playerMain.passedDoor() != (null)) {
			playerMain.leaveArea(getCurrentArea());

			setCurrentArea(playerMain.passedDoor().getDestination(), false);
			playerMain.enterArea(getCurrentArea(), playerMain.passedDoor().getCoordinatesAtArrival());

			getCurrentArea().setViewCandidate(playerMain);

			playerMain.setIsPassingDoor(null);		
		}

		Keyboard keyboard = getCurrentArea().getKeyboard();

		Button enterKey = keyboard.get(Keyboard.ENTER);
		Button zKey = keyboard.get(Keyboard.Z);
		Button xKey = keyboard.get(Keyboard.X);
		if (!(getCurrentArea().equals(level5))) {
			if (xKey.isDown()) {
				getCurrentArea().changeCameraScaleFactor(0.3f);
			}

			if (zKey.isDown()) {
				getCurrentArea().changeCameraScaleFactor(-0.3f);
			}
		}

		if (enterKey.isPressed()) {
			if(getCurrentArea().equals(pause)) {
				changeArea(playerMain, previousArea , pause, previousCoordinates);
			} else {
				changeArea(playerMain, pause, getCurrentArea(), new DiscreteCoordinates(5, 1));
			}
		}

		if (getCurrentArea().equals(levelSelector)){
			int i = ((LevelSelector)levelSelector).updateEntities();
			
			if (i == 0) {
				counter1 = 0;
				counter2 = 0;
				counter3 = 0;
				counter4 = 0;
				if (counter0 == 0) {
					playerMain.leaveArea(levelSelector);
					playerMain = new EnigmeAnimatedPlayer("max.new.3", getCurrentArea(), playerMain.getCurrentCells().get(0));
					++counter0;
				}
			} else if(i == 1) {
				counter0 = 0;
				counter2 = 0;
				counter3 = 0;
				counter4 = 0;
				if (counter1 == 0) {
					playerMain.leaveArea(levelSelector);
					playerMain = new EnigmeAnimatedPlayer("girl.1", getCurrentArea(), playerMain.getCurrentCells().get(0));
					++counter1;
				}
			} else if (i ==2) {
				counter0 = 0;
				counter1 = 0;
				counter3 = 0;
				counter4 = 0;
				if (counter2 == 0) {
					playerMain.leaveArea(levelSelector);
					playerMain = new EnigmeAnimatedPlayer("old.man.1", getCurrentArea(), playerMain.getCurrentCells().get(0));
					++counter2;
				}
			} else if(i == 3) {
				counter0 = 0;
				counter1 = 0;
				counter2 = 0;
				counter4 = 0;
				if (counter3 == 0) {
					playerMain.leaveArea(levelSelector);
					playerMain = new EnigmeAnimatedPlayer("max.ghost", getCurrentArea(), playerMain.getCurrentCells().get(0));
					++counter3;
				}
			} else if (i == 4) {
				counter0 = 0;
				counter1 = 0;
				counter2 = 0;
				counter3 = 0;
				if (counter4 == 0) {
					playerMain.leaveArea(levelSelector);
					playerMain = new EnigmeAnimatedPlayer("girl.5", getCurrentArea(), playerMain.getCurrentCells().get(0));
					++counter4;
				}
			}
		
				
			levelSelector.setViewCandidate(playerMain);
			
		}

		if (getCurrentArea().equals(level2)){
			((Level2)level2).updateEntities();
		}

		if (getCurrentArea().equals(level3)) {
			((Level3) level3).updateEntities();
		}

		if (getCurrentArea().equals(level4)){
			((Level4)level4).updateEntities();
		}

		if (getCurrentArea().equals(level5)){
			((Level5)level5).updateEntities();
		}

		if (getCurrentArea().equals(pause)){
			((Pause)pause).updateEntities();
		}
	}
	/**
	 * Switch between two Areas
	 * 
	 * @param player (EnigmePlayer) : Current main player
	 * @param destination (Area) : Area of destination
	 * @param origin (Area) : Previous area
	 * @param arrival (DiscreteCoordinates) : Coordinates at spawn
	 */
	private void changeArea(EnigmePlayer player, Area destination, Area origin, DiscreteCoordinates arrival) {
		previousArea = origin;
		previousCoordinates = playerMain.getCurrentCells().get(0);

		player.leaveArea(origin);

		setCurrentArea(destination.getTitle(), false);
		playerMain.enterArea(destination, arrival);

		getCurrentArea().setViewCandidate(player);

	}


	@Override
	public int getFrameRate() {
		return 40;
	}

	@Override
	public void end() {
		super.end();
	}
}
