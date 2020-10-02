package ch.epfl.cs107.play.game.areagame.actor;

import java.util.List;

import ch.epfl.cs107.play.math.DiscreteCoordinates;

/**
 * Models objects asking for interaction (i.e. can interact with some Interactable)
 * @see Interactable
 * This interface makes sense only in the "Area Context" with Actor contained into Area Cell
 */
public interface Interactor {
	
	/** @return (List<DiscreteCoordinates>) : Interactor's current cells' coordinates */
	List<DiscreteCoordinates> getCurrentCells();
	
	/** @return (List<DiscreteCoordinates>) : Interactor's field of view's cell coordinates */
	List<DiscreteCoordinates> getFieldOfViewCells();
	
	/** @return (boolean) : true if the Interactor asks for a contact interaction, else false */
	boolean wantsCellInteraction();
	
	/** @return (boolean) : true if the Interactor asks for a distant interaction, else false */
	boolean wantsViewInteraction();
	
	
	void interactWith(Interactable other);
	
}
