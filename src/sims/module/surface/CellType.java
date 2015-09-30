/**
 *
 */
package sims.module.surface;

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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CellType)) {
			return false;
		} else if (obj == this) {
			return true;
		}

		CellType temp = (CellType) obj;
		return (this.isDoor == temp.isDoor) && (this.isStepable == temp.isStepable);
	}

	/**
	 * @return the isDoor
	 */
	public boolean isDoor() {
		return this.isDoor;
	}

	/**
	 * @return the isStepable
	 */
	public boolean isStepable() {
		return this.isStepable;
	}

	/**
	 * @param isDoor
	 *            the isDoor to set
	 */
	public void setDoor(boolean isDoor) {
		this.isDoor = isDoor;
	}

	/**
	 * @param isStepable
	 *            the isStepable to set
	 */
	public void setStepable(boolean isStepable) {
		this.isStepable = isStepable;
	}
}