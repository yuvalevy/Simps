package sims.module.surface;

import java.util.ArrayList;

public class CellFactory {

	private static final ArrayList<CellType> arr = new ArrayList<CellType>();

	public static Cell getCell(boolean isStepable, boolean isDoor) {

		CellType type = new CellType(isStepable, isDoor);

		if (!arr.contains(type)) {
			arr.add(type);
		}

		return new Cell(arr.get(arr.indexOf(type)));
	}

}