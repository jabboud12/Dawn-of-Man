package ch.epfl.cs107.play.game.areagame;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.enigme.EnigmeBehavior.EnigmeCell;
import ch.epfl.cs107.play.game.enigme.actor.Dice;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.area.demo2.Demo2Behavior.Demo2Cell;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Window;
/**
 * AreaBehavior manages a map of Cells.
 */
public abstract class AreaBehavior {

	/**
	 * Each game will have its own cell extension
	 */
	public abstract class Cell implements Interactable{
		
		private DiscreteCoordinates coordinates;
		private Set<Interactable> entities;
		
		/**
		 * Constructor for the class Cell 
		 * @param x (int) : Horizontal coordinates of the cell
		 * @param y (int) : Vertical coordinates of the cell
		 */
		public Cell(int x, int y) {
			assert(cells!= null && x>=0 && y>=0);
			coordinates = new DiscreteCoordinates(x,y);
			entities = new HashSet<>();
		}
		
		/**
		 * Add an Interactable to a cell
		 * @param entity  (Interactable) : Item to add
		 */
		private void enter(Interactable entity) {
			if(canEnter(entity)) {
				entities.add(entity);
			}
		}
		
		/**
		 * Removes an Interactable from a cell
		 * @param entity  (Interactable) : Item to remove
		 */
		private void leave(Interactable entity) {
			if(canLeave(entity)) {
				entities.remove(entity);
			}
		}
		
		/**@return(boolean) : true if can enter the cell, else false */
		protected abstract boolean canEnter(Interactable entity);
		
		/**@return(boolean) : true if can leave the cell, else false */
		protected abstract boolean canLeave(Interactable entity);

		@Override
		public List<DiscreteCoordinates> getCurrentCells(){
			List<DiscreteCoordinates> list = new LinkedList<>();
			list.add(coordinates);
			return list;
		}
		
		/**
		 * Cell interaction of an Interactor to an Interactable
		 * @param interactor (Interactor) : Entity to interact with the Interactable
		 */
		private void cellInteractionOf(Interactor interactor) { 
			for(Interactable interactable : entities){
				if(interactable.isCellInteractable()) {
					interactor.interactWith(interactable);
				}	
			}
		}
		
		/**
		 * View interaction of an Interactor to an Interactable
		 * @param interactor (Interactor) : Entity to interact with the Interactable
		 */
		private void viewInteractionOf(Interactor interactor) { 
			for(Interactable interactable : entities){
				if(interactable.isViewInteractable()) {
					interactor.interactWith(interactable);
				}	
			}
		}



		
	}
	
	

    /// The behavior is an Image of size width x height
	private final Image behaviorMap;
	private final int height, width;
	
	/// We will convert the image into an array of cells
	protected final Cell[][] cells;
	/** @return (int) : width of the AreaBehavior map */
	public int getWidth(){
		return width;
	}
	
	/** @return (int) : height of the AreaBehavior map */
	public int getHeight() {
		return height;
	}
	
    /**
     * Default AreaBehavior Constructor
     * @param window (Window): graphic context, not null
     * @param fileName (String): name of the file containing the behavior image, not null
     */
    public AreaBehavior(Window window, String fileName){
    	behaviorMap = window.getImage(ResourcePath.getBehaviors(fileName), null, false);
    	height = behaviorMap.getHeight();
    	width = behaviorMap.getWidth();
    	cells = new Cell[width][height];
    }
    
    /** Getter for the cells of the AreaBehavior */
    public Cell[][] getCells() {
    	return cells;
    }
    
    /**
     * Cast all cells to Demo2Cells
     * @return (Demo2Cell[][]) :  Table of Cells that have been casted    
     */
    public Demo2Cell[][] getDemo2Cells(){
    	Demo2Cell[][] tab = new Demo2Cell[width][height];
    	for (int x=0 ; x<width ; ++x) {
    		for (int y=0 ; y<height ; ++y) {
    			tab[x][y] = (Demo2Cell) cells[x][y];
    		}
    	}
    	return tab;
    }
    
    /** @return (Image) : Image associated to the BehaviorMap*/
    public Image getBehaviorMap() {
    	return behaviorMap;
    }
    
	/**@return(boolean) : true if can leave the cell, else false */
    public boolean canLeave(Interactable entity, List<DiscreteCoordinates> coordinates) {
    	
    	for (DiscreteCoordinates coord : coordinates) {    		
    		if (!cells[coord.x][coord.y].canLeave(entity)) {
    			return false;
    		}
    		
    	}
    	return true;
    }
	/**@return(boolean) : true if can enter the cell, else false */
    public boolean canEnter(Interactable entity, List<DiscreteCoordinates> coordinates) {
    	
    	for (DiscreteCoordinates coord : coordinates) {
    		if (coord.x < 0 || coord.y < 0) {
				return false;
			}

			if (coord.x > getWidth() || coord.y > getHeight()) {
				return false;
			}
			
			if (!cells[coord.x][coord.y].canEnter(entity)) {
    			return false;
    		}
			
			for (Interactable interactable : cells[coord.x][coord.y].entities) {
				if(interactable.takeCellSpace()) {
					return false;
				}
			}
    		
    	}
    	return true;
    }
    
    /**
     * Removes an Interactable from a cell of certain coordinates
	 * @param entity  (Interactable) : Item to remove
     * @param coordinates (List<DiscreteCoordinates>) : coordinates of the Interactable
     */
    protected void leave(Interactable entity, List<DiscreteCoordinates> coordinates) {
    	for (DiscreteCoordinates coord : coordinates) {
    		if(!(entity instanceof Door)) {
        		cells[coord.x][coord.y].leave(entity);
    		}
    	}
    }
    
    /**
     * Adds an Interactable from a cell of certain coordinates
	 * @param entity  (Interactable) : Item to add
     * @param coordinates (List<DiscreteCoordinates>) : coordinates of the Interactable
     */
    protected void enter(Interactable entity, List<DiscreteCoordinates> coordinates) {
    	for (DiscreteCoordinates coord : coordinates) {
    		cells[coord.x][coord.y].enter(entity);
    	}
    }
    
    /**
	 * Cell interaction of an Interactor to an Interactable contained in a cell
	 * @param interactor (Interactor) : Entity to interact with the Interactable
	 */
    public void cellInteractionOf(Interactor interactor) {
    	for (DiscreteCoordinates coordinates : interactor.getCurrentCells()) {
    		cells[coordinates.x][coordinates.y].cellInteractionOf(interactor);
    	}
    }
    
    /**
	 * View interaction of an Interactor to an Interactable contained in a cell
	 * @param interactor (Interactor) : Entity to interact with the Interactable
	 */
    public void viewInteractionOf(Interactor interactor) {
    	for (DiscreteCoordinates coordinates : interactor.getFieldOfViewCells()) {
    		cells[coordinates.x][coordinates.y].viewInteractionOf(interactor);
    	}
    }
}
