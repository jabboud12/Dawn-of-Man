package ch.epfl.cs107.play.game.areagame;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.epfl.cs107.play.game.Playable;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.enigme.actor.Dialog;
import ch.epfl.cs107.play.game.enigme.actor.EnigmePlayer;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;


/**
 * Area is a "Part" of the AreaGame. It is characterized by its AreaBehavior and a List of Actors
 */
public abstract class Area implements Playable {

	// Context objects
	private Window window;
	private FileSystem fileSystem;


	/// List of Actors inside the area
	private List<Actor> actors;
	private List<Actor> registeredActors;
	private List<Actor> unregisteredActors;
	private List<Interactor> interactors;

	/// Camera Parameter
	// actor on which the view is centered  
	private Actor viewCandidate; 
	// effective center of the view 
	private Vector viewCenter;

	/// The behavior Map
	private AreaBehavior areaBehavior;

	/// Area title
	private String title;

	private Map<Interactable, List<DiscreteCoordinates>> interactablesToEnter;
	private Map<Interactable, List<DiscreteCoordinates>> interactablesToLeave;

	/// Dialog associated to the Area
	private Dialog dialog;

	/// Time of reference 
	private float timing;

	private float cameraScaleFactor;

	private EnigmePlayer mainPlayer;

	/** @return (float): camera scale factor, assume it is the same in x and y direction */
	public abstract float getCameraScaleFactor();

	/**
	 * Adds the float f to the current scale factor
	 * 
	 * @param f (float) : the float to add, can be negative
	 */
	public void changeCameraScaleFactor(float f) {
		if ((cameraScaleFactor + f) <= 40) {
			cameraScaleFactor = Math.abs(cameraScaleFactor + f);

		}
	}

	/**
	 * Sets the scale factor to a specific value
	 * 
	 * @param f (float) the value the scale factor takes
	 */
	public void setCameraScaleFactor(float f) {
		if (f <= 40) {
			cameraScaleFactor = Math.abs(f);
		}
	}
	/**
	 * Focuses the camera on an actor
	 * 
	 * @param a (Actor) the Actor to set the camera on
	 */
	public final void setViewCandidate(Actor a) {
		this.viewCandidate = a;
	}

	/**
	 * Set the current Area's title.
	 * 
	 * @param title (String) the title to take
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** @return (String) : title of the Area */
	public String getTitle() {
		return title;
	}

	/**
	 * Register a dialog in the area to be drawn next
	 */
	public void addDialog(Dialog d) {
		dialog = d;
	}

	/**
	 * Add an actor to the actors list
	 * @param a (Actor): the actor to add, not null
	 * @param forced (Boolean): if true, the method ends
	 */
	private void addActor(Actor a, boolean forced) {
		// Here decisions at the area level to decide if an actor 
		// must be added or not
		boolean errorOccured = !actors.add(a);

		if(a instanceof Interactable) {
			errorOccured = errorOccured || !enterAreaCells(((Interactable) a),
					((Interactable) a).getCurrentCells());
		}	

		if(a instanceof Interactor) {
			errorOccured = errorOccured || !interactors.add((Interactor) a);
		}	

		if (errorOccured && !forced) {
			System.out.println("Actor " + a + " cannot be completely added, "
					+ "so remove it from where "
					+ "it was.");
			removeActor(a, true);
		}

	}

	/**
	 * Remove an actor form the actor list
	 * @param a (Actor): the actor to remove, not null
	 * @param forced (Boolean): if true, the method ends
	 */
	private void removeActor(Actor a, boolean forced){
		boolean errorOccured = !actors.remove(a);

		if(a instanceof Interactable) {
			errorOccured = errorOccured || !leaveAreaCells(((Interactable) a),
					((Interactable) a).getCurrentCells());
		}

		if(a instanceof Interactor) {
			errorOccured = errorOccured || !interactors.remove(a);
		}	

		if (errorOccured && !forced) {
			System.out.println("Interactor " + a + " cannot be completely removed,"
					+ " so add it to where "
					+ "it was.");
			addActor(a, true);
		}
	}

	/**
	 * Register an actor : will be added at next update
	 * @param a (Actor): the actor to register, not null
	 * @return (boolean): true if the actor is correctly registered
	 */
	public final boolean registerActor(Actor a){
		if (a == null) {
			return false;
		} else {
			registeredActors.add(a);
			return true;
		}
	}

	/**
	 * Unregister an actor : will be removed at next update
	 * @param a (Actor): the actor to unregister, not null
	 * @return (boolean): true if the actor is correctly unregistered
	 */
	public final boolean unregisterActor(Actor a){
		if (a == null) {
			return false;
		} else {
			unregisteredActors.add(a);
			return true;
		}
	}
	/**
	 * Know if an area has already been suspended or not
	 * 
	 * @return (boolean) : if suspended once true, else false
	 */
	public boolean actorsInitialised() {
		return actors != null;
	}

	/**
	 * Getter for the area width
	 * 
	 * @return (int) : the width in number of cols
	 */
	public final int getWidth(){
		return areaBehavior.getWidth();
	}

	/**
	 * Getter for the area height
	 * 
	 * @return (int) : the height in number of rows
	 */
	public final int getHeight(){
		return areaBehavior.getHeight();
	}

	/**
	 * Set the behavior that corresponds to the Area
	 * 
	 * @param behavior (AreaBehavior) 
	 */
	protected void setBehavior(AreaBehavior behavior) {
		areaBehavior = behavior;
	}
	/** @return (AreaBehavior) : the behvior map associated to the area */
	public AreaBehavior getBehavior() {
		return areaBehavior;
	}

	/** @return the Window Keyboard for inputs */
	public final Keyboard getKeyboard () {
		Keyboard keyboard = window.getKeyboard();
		return keyboard;
	}

	/// Area implements Playable

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		this.fileSystem = fileSystem;

		interactablesToEnter = new HashMap<>();
		interactablesToLeave = new HashMap<>();

		actors = new LinkedList<>();
		registeredActors = new LinkedList<>();
		unregisteredActors = new LinkedList<>();
		interactors = new LinkedList<>();

		this.viewCenter = Vector.ZERO;
		this.viewCandidate = null;

		timing = 0f;
		cameraScaleFactor = getCameraScaleFactor();

		return true;
	}

	/**
	 * Adds an Interactable to a list in order to be removed from its current cells
	 * @param entity (Interactable) : Item to remove 
	 * @param coordinates (List<DiscreteCoordinates>) : list of the Interactable's current cells
	 * @return
	 */
	public final boolean leaveAreaCells(Interactable entity, List<DiscreteCoordinates> coordinates) {
		if (areaBehavior.canLeave(entity, coordinates)) {
			interactablesToLeave.put(entity, coordinates);
			return true;
		}else {
			return false;	
		}
	}

	/**
	 * Adds an Interactable to a list in order to be added to its specified cells
	 * @param entity (Interactable) : Item to add 
	 * @param coordinates (List<DiscreteCoordinates>) : list of the Interactable's objective cells
	 * @return
	 */
	public final boolean enterAreaCells(Interactable entity, List<DiscreteCoordinates> coordinates) {
		if (areaBehavior.canEnter(entity, coordinates)) {
			interactablesToEnter.put(entity, coordinates);
			return true;
		}else {
			return false;		
		}
	}

	/**
	 * Resume method: Can be overridden
	 * @param window (Window): display context, not null
	 * @param fileSystem (FileSystem): given file system, not null
	 * @return (boolean) : if the resume succeed, true by default
	 */
	public boolean resume(Window window, FileSystem fileSystem){
		return true;
	}

	@Override
	public void update(float deltaTime) {
		purgeRegistration();
		updateCamera();
		for (Actor actor : actors) {   		
			actor.draw(window);
			actor.update(deltaTime);   		
		}

		timing += 0.01f;

		if (dialog != null) {
			if (dialog.getDisplayTime() != 0f) {
				if(timing <= dialog.getDisplayTime()) {
					dialog.draw(window);
				}
			} else {
				dialog.draw(window);
			}
		}

		for (Interactor interactor : interactors) {
			if (interactor.wantsCellInteraction()) {
				/* demander au gestionnaire de la grille de 
    		   mettre en place les interactions de contact */

				getBehavior().cellInteractionOf(interactor);
			}

			if (interactor.wantsViewInteraction()) {
				/* demander au gestionnaire de la grille 
    	       de mettre en place les interaction distantes */

				getBehavior().viewInteractionOf(interactor);
			} 
		}
	}

	/**
	 * Clears or adds Actors to be cleared or added, as well as for Interactables
	 */
	private void purgeRegistration() {

		for (Actor actor : registeredActors) {
			addActor(actor , false);
		}


		for (Actor actor : unregisteredActors) {       
			removeActor(actor , false);            	
		}

		for(Entry<Interactable, List<DiscreteCoordinates> > pair : interactablesToEnter.entrySet()){
			areaBehavior.enter(pair.getKey(), pair.getValue());
		}

		for(Entry<Interactable, List<DiscreteCoordinates> > pair : interactablesToLeave.entrySet()){
			areaBehavior.leave(pair.getKey(), pair.getValue());
		}

		registeredActors.clear();  	
		unregisteredActors.clear();
		interactablesToEnter.clear();
		interactablesToLeave.clear();
	}

	/**
	 * Change the camera center of focus depending on its candidate's position
	 */
	private void updateCamera () {
		if (viewCandidate != null) {
			viewCenter = viewCandidate.getPosition();
		}
		Transform viewTransform = Transform.I.scaled(cameraScaleFactor).translated(viewCenter);
		window.setRelativeTransform(viewTransform);
	}

	public void setMainPlayer(EnigmePlayer s) {
		mainPlayer = s;
	}
	
	public EnigmePlayer getMainPlayer() {
		return mainPlayer;
	}

	/**
	 * Suspend method: Can be overridden, called before resume other
	 */
	public void suspend(){
		purgeRegistration();
	}


	@Override
	public void end() {
		actors.clear();
	}

}