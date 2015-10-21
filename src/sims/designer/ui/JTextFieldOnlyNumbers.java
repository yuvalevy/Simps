package sims.designer.ui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldOnlyNumbers extends PlainDocument {

	private static final long serialVersionUID = -7063949794373613950L;

	public JTextFieldOnlyNumbers() {

	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return;
		}

		super.insertString(offs, str, a);
	}
}
