package sims.contolers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

	private final GuiControler controler;

	public MouseHandler(GuiControler controler) {
		this.controler = controler;

	}

	@Override
	public void mouseClicked(MouseEvent event) {

		Point pointClicked = event.getPoint();

		switch (event.getClickCount()) {

		case 1:
			this.controler.guiClick(pointClicked);
			break;
		case 2:
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
