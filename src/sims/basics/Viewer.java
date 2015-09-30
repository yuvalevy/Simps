package sims.basics;

import java.awt.Choice;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public interface Viewer {

	public void buildViewer(Choice plyrChoice, JButton gamePauser, JButton playerAdder, MouseListener eventControler);

}
