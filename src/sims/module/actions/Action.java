package sims.module.actions;

import javax.swing.ImageIcon;

import sims.basics.Log;
import sims.module.feelings.Feeling;
import sims.module.surface.GameLocation;

public class Action {

	private final ActionIdentifier identifier;
	private final Feeling feeling;
	private final GameLocation actionDestination;

	private boolean isActive;

	private ImageIcon[] images;
	private int imgIndex;

	private final int actionTime;
	private int timer;

	protected final Action preAction;

	protected Action(ActionIdentifier identifier, Action preAction, ImageIcon... images) {
		this(identifier, 0, null, preAction, null, images);
	}

	protected Action(ActionIdentifier identifier, Feeling feeling, ImageIcon... images) {
		this(identifier, 0, null, null, feeling, images);
	}

	protected Action(ActionIdentifier identifier, ImageIcon... images) {
		this(identifier, 0, null, null, null, images);
	}

	protected Action(ActionIdentifier identifier, int playerSearchTime, Action preAction, ImageIcon icon) {
		this(identifier, playerSearchTime, null, preAction, null, icon);
	}

	protected Action(ActionIdentifier identifier, int actionTime, Feeling feel, ImageIcon icon) {
		this(identifier, actionTime, null, null, feel, icon);
	}

	protected Action(ActionIdentifier identifier, int actionTime, GameLocation actionDestination, Action preAction,
			Feeling feeling, ImageIcon... images) {

		this.images = images;
		this.imgIndex = 0;

		if ((images == null) || (images.length == 0)) {
			this.images = new ImageIcon[1];
			this.images[0] = null;
		}

		this.feeling = feeling;
		this.preAction = preAction;
		this.identifier = identifier;
		this.isActive = false;

		this.actionTime = actionTime;
		this.timer = this.actionTime;

		this.actionDestination = actionDestination;

	}

	/**
	 *
	 * @return default: returns true
	 */
	public boolean canInterrupt() {
		return true;
	}

	public void decreaseTimer() {
		this.timer--;
	}

	public GameLocation getActionDestination() {
		return this.actionDestination;
	}

	public Feeling getFeeling() {
		return this.feeling;
	}

	/**
	 * @return this.identifier value
	 */
	public ActionIdentifier getIdentifier() {
		return this.identifier;
	}

	/**
	 *
	 * @return default: if pre-action isn't null and isn't over, returns
	 *         this.preAction.getNextImage(). otherwise, returns images[] at the
	 *         current running index
	 */
	public ImageIcon getNextImage() {

		if (!isActive()) {
			return null;
		}

		if (this.preAction != null) {

			if (!this.preAction.isOver()) {

				Log.WriteLineLog("Image from pre Action");
				return this.preAction.getNextImage();
			}
		}

		ImageIcon $ = this.images[this.imgIndex];
		if (isAction(ActionIdentifier.Walk)) {
			Log.WriteLineLog("Image from Action " + this.imgIndex + "/" + this.images.length);
			Log.WriteLineLog(this.images[this.imgIndex].toString());
		}

		this.imgIndex++;

		if (this.imgIndex == this.images.length) {
			this.imgIndex = 0;
		}

		return $;
	}

	/**
	 * Returns whether this.identifier equals identifier
	 *
	 * @param identifier
	 * @return
	 */
	public boolean isAction(ActionIdentifier identifier) {
		return this.identifier == identifier;
	}

	/**
	 * @return this.isActive value
	 */
	public boolean isActive() {
		return this.isActive;
	}

	/**
	 *
	 * @return default: returns whether timer is equals to 0
	 */
	public boolean isOver() {
		return this.timer == 0;
	}

	/**
	 * default: starts pre-action if it's not null and activates this
	 */
	public void start() {

		this.isActive = true;
		this.timer = this.actionTime;

		if (this.preAction != null) {

			this.preAction.start();

		}

	}

	/**
	 * default: stops pre-action if it's not null and deactivates this
	 */
	public void stop() {

		if (this.feeling != null) {
			Log.WriteLineLog("initialSuffering!!");
			this.feeling.initialSuffering();
		}

		this.isActive = false;
		this.timer = 0;

		if (this.preAction != null) {

			this.preAction.stop();
		}
	}

	/**
	 * Implementation needs to go like this:
	 *
	 * if (isActive()) {
	 *
	 * // If preAction is finished, start if (this.preAction.isOver()) {
	 *
	 * this.preAction.stop();
	 *
	 * ///// ACIONS////// }
	 *
	 * // If preAction is not finished, tick() it and return result else {
	 *
	 * return this.preAction.tick(); }
	 *
	 * }
	 *
	 * @return default: returns null
	 */
	public GameLocation tick() {

		if (isActive()) {

			if (this.preAction != null) {

				// If preAction is finished, start
				if (this.preAction.isOver()) {

					this.preAction.stop();

					innerTick();
				} else {

					// If preAction is not finished, tick() it and return result
					// else {
					return this.preAction.tick();
				}
			} else {

				innerTick();
			}
		}

		return null;

	}

	@Override
	public String toString() {
		return super.toString() + this.identifier;
	}

	private void innerTick() {
		if (!isOver()) {

			decreaseTimer();
		}
	}
}