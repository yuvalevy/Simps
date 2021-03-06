package sims.contolers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import sims.basics.Log;

public class MouseHandler implements MouseListener {

	private final GuiControler controler;
	private final int startingPaintWidth;

	public MouseHandler(GuiControler controler, int width) {
		this.controler = controler;
		this.startingPaintWidth = width;

	}

	@Override
	public void mouseClicked(MouseEvent event) {

		Point pointClicked = event.getPoint();
		Log.WriteLineLog("Point clicked: " + pointClicked);

		if (pointClicked.x > this.startingPaintWidth) {

			pointClicked.x = pointClicked.x - this.startingPaintWidth;
			Log.WriteLineLog("Point clicked: " + pointClicked);

			switch (event.getButton()) {

			// left click
			case 1:
				this.controler.guiLeftClick(pointClicked, event.isControlDown());
				break;

			// right click
			case 3:
				this.controler.guiRightClick(pointClicked);
				break;

			}
		} else {
			this.controler.initialFeeling(pointClicked);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent event) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
