/**
 * 
 */
package ui;

/**
 * @author Yuval
 *
 */
public class CellType {

	private boolean isStepable;
	private boolean isDoor;

	/**
	 * 
	 */
	public CellType(boolean isStepable, boolean isDoor) {
		this.isDoor = isDoor;
		this.isStepable = isStepable;
	}

	/**
	 * @return the isStepable
	 */
	public boolean isStepable() {
		return isStepable;
	}

	/**
	 * @param isStepable
	 *            the isStepable to set
	 */
	public void setStepable(boolean isStepable) {
		this.isStepable = isStepable;
	}

	/**
	 * @return the isDoor
	 */
	public boolean isDoor() {
		return isDoor;
	}

	/**
	 * @param isDoor
	 *            the isDoor to set
	 */
	public void setDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}

}