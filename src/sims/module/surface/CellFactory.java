package sims.module.surface;

import java.awt.Point;
import java.util.ArrayList;

public class CellFactory {

	private static ArrayList<CellType> ALL_CELL_TYPES = new ArrayList<CellType>();

	public static Cell getCell(boolean isStepable, boolean isDoor, Point coordinate) {

		CellType typ = getCellType(isStepable, isDoor);

		return new Cell(typ, coordinate);
	}

	public static CellType getCellType(boolean isStepable, boolean isDoor) {

		CellType type = new CellType(isStepable, isDoor);

		if (!ALL_CELL_TYPES.contains(type)) {

			ALL_CELL_TYPES.add(type);

		}

		return ALL_CELL_TYPES.get(ALL_CELL_TYPES.indexOf(type));

	}

}