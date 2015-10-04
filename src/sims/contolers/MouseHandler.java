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

		pointClicked.x = pointClicked.x - this.startingPaintWidth;
		Log.WriteLog("Point clicked: " + pointClicked);

		// switch (event.getClickCount()) {
		switch (event.getButton()) {

		case 1:
			this.controler.guiClick(pointClicked);
			break;
		case 3:
			this.controler.guiDoubleClick(pointClicked);
			break;

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
