/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   4 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.enigme.EnigmeBehavior;
import ch.epfl.cs107.play.game.enigme.actor.Collectable;
import ch.epfl.cs107.play.game.enigme.actor.Door;
import ch.epfl.cs107.play.game.enigme.actor.Switchable;

public interface EnigmeInteractionVisitor extends AreaInteractionVisitor{
	
	/**
	 * Simulate and interaction between a enigme Interactors
		and an enigme Collectable
	 * @param collectable (Collectable), not null
	 */
	default void interactWith(Collectable collectable) {
			System.out.println("Warning: a Specific Interaction involving "+ collectable.toString() 
								+"is not yet implemented or you simply forget a cast");
		}
	
	
	/**
	 * Simulate and interaction between a enigme Interactors
		and an enigme Apple
	 * @param item (item), not null
	 */
	default void interactWith(Switchable item){
        System.out.println("Warning: a Specific Interaction involving "+ item.toString() 
        					+"is not yet implemented or you simply forget a cast");
    }

	
	/**
	 * Simulate and interaction between a enigme Interactors
		and an enigme Door
	 * @param door (Door), not null
	 */
	default void interactWith(Door door){
        System.out.println("Warning: a Specific Interaction involving "+ door.toString() 
        					+"is not yet implemented or you simply forget a cast");
    }


	
	default void interactWith(EnigmeBehavior.EnigmeCell cell){ 
		// by default the interaction is empty
	}
}
