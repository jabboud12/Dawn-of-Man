/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   27 Nov 2018
 */

package ch.epfl.cs107.play.game.enigme.area.demo2;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.enigme.actor.demo2.Demo2Player;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class Demo2 extends AreaGame {
	private Area room0;
	private Area room1;
	private Demo2Player player;
	
	@Override
	public String getTitle() {
		return "Demo2";
	}
	
	@Override
	public int getFrameRate() {
		return 40;
	}
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);

		room0 = new Room0();
		room1 = new Room1();
		
		addArea(room0);
		addArea(room1);
		
		setCurrentArea(room0.getTitle(), false);
		player = new Demo2Player(getCurrentArea(), new DiscreteCoordinates(5,5));
		getCurrentArea().setViewCandidate(player);
		
		return true;
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		
		if(player.getIsPassingDoor()) {
			player.leaveArea(getCurrentArea());

			if (getCurrentArea().equals(room0)) {	

				setCurrentArea(room1.getTitle(), false);
				player.enterArea(getCurrentArea(), new DiscreteCoordinates(5,1));
				
			} else if(getCurrentArea().equals(room1)) {

				setCurrentArea(room0.getTitle(), false);
				player.enterArea(getCurrentArea(), new DiscreteCoordinates(5,5));

			}
			getCurrentArea().setViewCandidate(player);
			player.setIsPassingDoor(false);
		}		
	}
	
	@Override
	public void end() {
		super.end();
	}
}
