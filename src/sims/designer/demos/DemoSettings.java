package sims.designer.demos;

import java.awt.Dimension;
import java.awt.Rectangle;

import sims.basics.configurations.ConfigurationManager;

public class DemoSettings {

	private Dimension screenSize;
	private Rectangle cellSize;
	private int toysGlobalCount;

	public DemoSettings() {

		setConfigurationValues();

	}

	public Rectangle getCellSize() {
		return this.cellSize;
	}

	public Dimension getScreenSize() {
		return this.screenSize;
	}

	public int getToysGlobalCount() {
		return this.toysGlobalCount;
	}

	public void setCellSize(int width, int height) {

		this.cellSize.setSize(width, height);
	}

	public void setConfigurationValues() {
		this.cellSize = ConfigurationManager.getCellDefaultSize();
		this.screenSize = new Dimension();
		this.toysGlobalCount = ConfigurationManager.getToysLimit();
	}

	public void setScreenSize(int width, int height) {

		this.screenSize.setSize(width, height);
	}

	public void setToysGlobalCount(int toysGlobalCount) {
		this.toysGlobalCount = toysGlobalCount;
	}

}
