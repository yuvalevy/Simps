package sims.module.surface;

import java.awt.Point;
import java.util.ArrayList;

public class CellFactory {

	private static ArrayList<CellType> ALL_CELL_TYPES = new ArrayList<CellType>();

	public static Cell getCell(Point coordinate, CellProperty... properties) {

		CellType typ = getCellType(properties);

		return new Cell(typ, coordinate);
	}

	public static CellType getCellType(CellProperty... properties) {

		CellType type = new CellType(properties);

		if (!ALL_CELL_TYPES.contains(type)) {

			ALL_CELL_TYPES.add(type);

		}

		return ALL_CELL_TYPES.get(ALL_CELL_TYPES.indexOf(type));

	}

}