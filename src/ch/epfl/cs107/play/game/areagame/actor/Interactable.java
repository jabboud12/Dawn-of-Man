package ch.epfl.cs107.play.game.areagame.actor;

import java.util.List;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Models objects receptive to interaction (i.e. Interactor can interact with them)
 * @see Interactor
 * This interface makes sense only in the "AreaGame" context with Actor contained into Area Cell
 */
public interface Interactable {
	
	
	List<DiscreteCoordinates> getCurrentCells();
	
	/** Returns true if the Interactable cannot be overtaken via its own cell
	 * 	and false if it can.
	 */
    boolean takeCellSpace();
	
	/**	Returns true if the Interactable accepts distant interactions and false if it does not */
    boolean isViewInteractable();
	
	/** Returns true if the Interactable accepts contact interactions and false if it does not */
    boolean isCellInteractable();
	
	
	default void acceptInteraction(AreaInteractionVisitor v) {
		// Does not do anything by default
	}
	
}
