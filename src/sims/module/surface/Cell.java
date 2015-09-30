/**
 *
 */
package sims.module.surface;

/**
 * @author Yuval
 *
 */
public class Cell {

	private CellType cellType;
	// private Point coordinate;

	/**
	 * @param coordinate
	 * @param cellType
	 */
	public Cell(CellType cellType) {
		this.cellType = cellType;
	}

	/**
	 * @return the cellType
	 */
	public CellType getCellType() {
		return this.cellType;
	}

	/**
	 * @param cellType
	 *            the cellType to set
	 */
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

}