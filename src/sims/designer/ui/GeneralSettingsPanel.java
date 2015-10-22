package sims.designer.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import sims.basics.Log;
import sims.designer.demos.DemoSettings;

public class GeneralSettingsPanel extends JPanel implements PropertiesComponent {

	private static final long serialVersionUID = 8085548354970837234L;

	private JTextField txtXCellSize;
	private JTextField txtYCellSize;
	private JTextField txtXScreenSize;
	private JTextField txtYScreenSize;
	private JTextField txtGlobalToysCount;

	private final DemoSettings settings;

	public GeneralSettingsPanel(DemoSettings settings) {
		super();

		setName("General Settings");
		this.settings = settings;

		buildGrid();
		setFieldsValues();

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {

		Log.WriteLineLog("GeneralSettingsPanel.paint");
	}

	protected void restoreConfiguration() {

		this.settings.setConfigurationValues();
		setFieldsValues();
	}

	@Override
	public DefaultMutableTreeNode getTreeNodes() {

		return null;
	}

	public void saveChanges() {

		int x, y;

		x = getInt(this.txtXScreenSize.getText());
		y = getInt(this.txtYScreenSize.getText());

		this.settings.setScreenSize(x, y);

		x = getInt(this.txtXCellSize.getText());
		y = getInt(this.txtYCellSize.getText());

		this.settings.setCellSize(x, y);

	}

	@Override
	public void setVisible(boolean aFlag) {

		if (aFlag == true) {
			setFieldsValues();

		} else {
			saveChanges();

		}
		super.setVisible(aFlag);
	}

	private void buildGrid() {

		GridBagLayout gbl_pGeneral = new GridBagLayout();
		gbl_pGeneral.columnWidths = new int[] { 30, 100, 70, 0, 0, 0, 0, 70, 78, 127, 0 };
		gbl_pGeneral.rowHeights = new int[] { 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_pGeneral.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_pGeneral.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gbl_pGeneral);

		JLabel lblNewLabel = new JLabel("General Settings");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblNewLabel.setForeground(Color.BLUE);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 6;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 2;
		gbc_verticalStrut_1.gridy = 3;
		add(verticalStrut_1, gbc_verticalStrut_1);

		JLabel lblScreenSize = new JLabel("Screen Size");
		lblScreenSize.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		GridBagConstraints gbc_lblScreenSize = new GridBagConstraints();
		gbc_lblScreenSize.anchor = GridBagConstraints.WEST;
		gbc_lblScreenSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblScreenSize.gridx = 1;
		gbc_lblScreenSize.gridy = 4;
		add(lblScreenSize, gbc_lblScreenSize);

		this.txtXScreenSize = new JTextField();
		this.txtXScreenSize.setColumns(10);
		this.txtXScreenSize.setDocument(new JTextFieldOnlyNumbers());
		GridBagConstraints gbc_txtXScreenSize = new GridBagConstraints();
		gbc_txtXScreenSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtXScreenSize.anchor = GridBagConstraints.NORTH;
		gbc_txtXScreenSize.insets = new Insets(0, 0, 5, 5);
		gbc_txtXScreenSize.gridx = 2;
		gbc_txtXScreenSize.gridy = 4;
		add(this.txtXScreenSize, gbc_txtXScreenSize);

		JLabel lblX = new JLabel("x");
		GridBagConstraints gbc_lblX = new GridBagConstraints();
		gbc_lblX.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblX.insets = new Insets(0, 0, 5, 5);
		gbc_lblX.gridx = 4;
		gbc_lblX.gridy = 4;
		add(lblX, gbc_lblX);

		this.txtYScreenSize = new JTextField();
		this.txtYScreenSize.setColumns(10);
		this.txtYScreenSize.setDocument(new JTextFieldOnlyNumbers());
		GridBagConstraints gbc_txtYScreenSize = new GridBagConstraints();
		gbc_txtYScreenSize.anchor = GridBagConstraints.NORTH;
		gbc_txtYScreenSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtYScreenSize.insets = new Insets(0, 0, 5, 5);
		gbc_txtYScreenSize.gridx = 5;
		gbc_txtYScreenSize.gridy = 4;
		add(this.txtYScreenSize, gbc_txtYScreenSize);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 2;
		gbc_verticalStrut.gridy = 5;
		add(verticalStrut, gbc_verticalStrut);

		JLabel lblCellSize = new JLabel("Cell Size");
		GridBagConstraints gbc_lblCellSize = new GridBagConstraints();
		gbc_lblCellSize.anchor = GridBagConstraints.WEST;
		gbc_lblCellSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblCellSize.gridx = 1;
		gbc_lblCellSize.gridy = 6;
		add(lblCellSize, gbc_lblCellSize);

		this.txtXCellSize = new JTextField();
		this.txtXCellSize.setColumns(10);
		this.txtXCellSize.setDocument(new JTextFieldOnlyNumbers());
		GridBagConstraints gbc_txtXCellSize = new GridBagConstraints();
		gbc_txtXCellSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtXCellSize.insets = new Insets(0, 0, 5, 5);
		gbc_txtXCellSize.anchor = GridBagConstraints.NORTH;
		gbc_txtXCellSize.gridx = 2;
		gbc_txtXCellSize.gridy = 6;
		add(this.txtXCellSize, gbc_txtXCellSize);

		JLabel label = new JLabel("x");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 4;
		gbc_label.gridy = 6;
		add(label, gbc_label);

		this.txtYCellSize = new JTextField();
		this.txtYCellSize.setColumns(10);
		this.txtYCellSize.setDocument(new JTextFieldOnlyNumbers());
		GridBagConstraints gbc_txtYCellSize = new GridBagConstraints();
		gbc_txtYCellSize.anchor = GridBagConstraints.NORTH;
		gbc_txtYCellSize.insets = new Insets(0, 0, 5, 5);
		gbc_txtYCellSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtYCellSize.gridx = 5;
		gbc_txtYCellSize.gridy = 6;
		add(this.txtYCellSize, gbc_txtYCellSize);

		this.txtGlobalToysCount = new JTextField();
		this.txtGlobalToysCount.setColumns(10);
		this.txtGlobalToysCount.setDocument(new JTextFieldOnlyNumbers());
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 8;
		add(this.txtGlobalToysCount, gbc_textField);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 2;
		gbc_verticalStrut_2.gridy = 7;
		add(verticalStrut_2, gbc_verticalStrut_2);

		JLabel lblGlobalToysCount = new JLabel("Global Toys Count");
		GridBagConstraints gbc_lblGlobalToysCount = new GridBagConstraints();
		gbc_lblGlobalToysCount.anchor = GridBagConstraints.WEST;
		gbc_lblGlobalToysCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblGlobalToysCount.gridx = 1;
		gbc_lblGlobalToysCount.gridy = 8;
		add(lblGlobalToysCount, gbc_lblGlobalToysCount);

		JButton btnRestoreConfiguration = new JButton("Restore Configuration");
		btnRestoreConfiguration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				restoreConfiguration();
			}
		});

		GridBagConstraints gbc_btnRestoreConfiguration = new GridBagConstraints();
		gbc_btnRestoreConfiguration.insets = new Insets(0, 0, 5, 5);
		gbc_btnRestoreConfiguration.gridx = 6;
		gbc_btnRestoreConfiguration.gridy = 10;
		add(btnRestoreConfiguration, gbc_btnRestoreConfiguration);

	}

	private int getInt(String str) {
		if (str == null) {
			return 0;
		}

		if (str.equals("")) {
			return 0;
		}

		return Integer.parseInt(str);
	}

	private void setFieldsValues() {

		Dimension dmnsn = this.settings.getScreenSize();
		int x = (int) dmnsn.getWidth(), y = (int) dmnsn.getHeight();
		this.txtXScreenSize.setText(x + "");
		this.txtYScreenSize.setText(y + "");

		Rectangle rctngl = this.settings.getCellSize();
		x = (int) rctngl.getWidth();
		y = (int) rctngl.getHeight();

		this.txtXCellSize.setText(x + "");
		this.txtYCellSize.setText(y + "");

		this.txtGlobalToysCount.setText(this.settings.getToysGlobalCount() + "");
	}
}
