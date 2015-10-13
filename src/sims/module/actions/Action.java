package sims.module.actions;

import javax.swing.ImageIcon;

import sims.module.surface.GameLocation;

public class Action {

	protected ImageIcon[] images;
	protected int imgIndex;
	protected final Action preAction;
	protected boolean isActive;

	private final ActionIdentifier identifier;

	protected Action(ActionIdentifier identifier, Action preAction, ImageIcon... images) {

		this.images = images;
		this.imgIndex = 0;

		if ((images == null) || (images.length == 0)) {
			this.images = new ImageIcon[1];
			this.images[0] = null;
		}

		this.preAction = preAction;
		this.identifier = identifier;
		this.isActive = false;

	}

	protected Action(ActionIdentifier identifier, ImageIcon... images) {
		this(identifier, null, images);
	}

	/**
	 *
	 * @return default: returns true
	 */
	public boolean canInterrupt() {
		return true;
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

				return this.preAction.getNextImage();
			}
		}

		ImageIcon $ = this.images[this.imgIndex];

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
	 * @return default: returns false
	 */
	public boolean isOver() {
		return false;
	}

	/**
	 * default: starts pre-action if it's not null and activates this
	 */
	public void start() {

		this.isActive = true;

		if (this.preAction != null) {

			this.preAction.start();

		}

	}

	/**
	 * default: stops pre-action if it's not null and deactivates this
	 */
	public void stop() {

		this.isActive = false;

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
		return null;
	}

	@Override
	public String toString() {
		return super.toString() + this.identifier;
	}
}