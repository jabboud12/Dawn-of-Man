/*
 * Author :   Joseph ABBOUD & Zad ABI FADEL
 * Date   :   2 Dec 2018
 */

package ch.epfl.cs107.play.game.enigme;

import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.window.Window;


public class EnigmeBehavior extends AreaBehavior {

    public enum EnigmeCellType {
        NULL(0),
        WALL(-16777216),    // RGB code for black
        DOOR(-65536),        // RGB code for red
        WATER(-16776961),    // RGB code for blue
        INDOOR_WALKABLE(-1),
        OUTDOOR_WALKABLE(-14112955),
    	CONDITIONABLE (-256); // RGB code for yellow
        final int type;

        /**
	     * Getter for the EnigmeCellType corresponding to the given code
	     * @param type (int): the given code
	     * @return (EnigmeCellType): type of the cell for the given code
	     */
        EnigmeCellType(int type) {
            this.type = type;
        }

        static EnigmeCellType toType(int type) {
            switch (type) {
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
                default:
                    return NULL;
            }
        }
    }

    public class EnigmeCell extends Cell {

        public EnigmeCellType type;

        /**
		 * Private constructor 
		 * @param x (int) : Horizontal coordinates of the cell
		 * @param y (int) : Vertical coordinates of the cell
		 * @param type (EnigmeCellType) : Type of the cell
		 */
        private EnigmeCell(int x, int y, EnigmeCellType type) {
            super(x, y);
            this.type = type;
        }

        @Override
        public boolean takeCellSpace() {
            return false;
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

            switch (type) {
                case WALL:
                    return false;
                case WATER:
                    return false;
                case NULL:
                    return false;
                case INDOOR_WALKABLE:
                    return true;
                case OUTDOOR_WALKABLE:
                    return true;
                case DOOR:
                    return true;
                case CONDITIONABLE:
                	return true;
                default:            
                    return false;
            }
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        /**
		 * Getter for the type of the EnigmeCell
		 * @return (EnigmeCellType)
		 */
        public EnigmeCellType getType() {
            return type;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v) {
			// This method is not used by EnigmeCell
        }
    }

    /**
	 * Associate to each cell of a behavior map its type
	 * 
	 * @param window (Window) : window display context, not null.
	 * @param filename (String) : Name of the file containing the behavior map
	 */
    public EnigmeBehavior(Window window, String filename) {
        super(window, filename);
        for (int x = 0; x < getWidth(); ++x) {
            for (int y = 0; y < getHeight(); ++y) {
                EnigmeCellType cellType =
                        EnigmeCellType.toType(getBehaviorMap().getRGB(getHeight() - 1 - y, x));
                cells[x][y] = new EnigmeCell(x, y, cellType);
            }
        }
    }


}