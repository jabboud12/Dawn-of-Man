/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   27 Nov 2018
 */

package ch.epfl.cs107.play.game.enigme.area.demo2;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;

public class Demo2Behavior extends AreaBehavior {
	
	public enum Demo2CellType {
		NULL(0),
		WALL(-16777216),	// RGB code for black
		DOOR(-65536), 		// RGB code for red
		WATER(-16776961), 	// RGB code for blue
		INDOOR_WALKABLE(-1), 
		OUTDOOR_WALKABLE(-14112955),
    	CONDITIONABLE (-256); // RGB code for yellow

		final int type;
		
		/**
	     * Getter for the Demo2CellType corresponding to the given code
	     * @param type (int): the given code
	     * @return (Demo2CellType): type of the cell for the given code
	     */
		Demo2CellType(int type) {
			this.type = type;
		}
		
		static Demo2CellType toType(int type) {
			switch(type) {
			case (-16777216):
				return WALL;
			case (-65536):
				return DOOR;
			case (-16776961):
				return WATER;
			case (-1):
				return INDOOR_WALKABLE;
			case (-14112955):
				return OUTDOOR_WALKABLE;
			case(-256):
            	return CONDITIONABLE;
			default :
				return NULL;
			}
		}
	}
	
	public class Demo2Cell extends Cell {
		public Demo2CellType type;
		
		/**
		 * Private constructor 
		 * @param x (int) : Horizontal coordinates of the cell
		 * @param y (int) : Vertical coordinates of the cell
		 * @param type (Demo2CellType) : Type of the cell
		 */
		private Demo2Cell(int x, int y, Demo2CellType type) {
			super(x,y);
			this.type = type;
		}
		
		@Override
		public boolean isViewInteractable() {
			return true;
		}
		@Override
		public boolean isCellInteractable() {
			return true;
		}
		@Override
		protected boolean canEnter(Interactable entity) {
			switch(type) {
			case WALL :
				return false; 
			case WATER :
				return false;
			case NULL :
				return false;
			case INDOOR_WALKABLE :
				return true;
			case OUTDOOR_WALKABLE :
				return true;
			case DOOR :
				return true;
			case CONDITIONABLE:
            	return true;
			default :
				return false;
			}
		}
		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}
		/**
		 * Getter for the type of the Demo2Cell
		 * @return (Demo2CellType)
		 */
		public Demo2CellType getType(){
			return type;
		}

		@Override
		public void acceptInteraction(AreaInteractionVisitor v) {
			// This method is not used by Demo2Cell
		}

		@Override
		public boolean takeCellSpace() {
			// This method is not used by Demo2Cell
			return false;
		}
	}
	
	/**
	 * Associate to each cell of a behavior map its type
	 * 
	 * @param window (Window) : window display context, not null.
	 * @param filename (String) : Name of the file containing the behavior map
	 */
	public Demo2Behavior(Window window, String filename) {
		super(window, filename);
		for (int x=0 ; x<getWidth() ; ++x) {
			for (int y=0 ; y<getHeight() ; ++y) {
				Demo2CellType cellType =
						Demo2CellType.toType(getBehaviorMap().getRGB(getHeight()-1-y, x));
				cells[x][y] = new Demo2Cell(x, y, cellType);
			}
		}
	}
}
