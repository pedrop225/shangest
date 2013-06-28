package com.shangest.graphics.forms.util;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JRadioButton;

public class RepresentaSigno extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JRadioButton[][] rb = new JRadioButton[4][2];
	
	/**
	 * Create the panel.
	 */
	public RepresentaSigno() {
		setLayout(new GridLayout(0, 2, 0, 0));
		
		JRadioButton _1 = new JRadioButton("");
		add(_1);
		rb[0][0] = _1;
		
		JRadioButton _2 = new JRadioButton("");
		add(_2);
		rb[0][1] = _2;

		JRadioButton _3 = new JRadioButton("");
		add(_3);
		rb[1][0] = _3;
		
		JRadioButton _4 = new JRadioButton("");
		add(_4);
		rb[1][1] = _4;
		
		JRadioButton _5 = new JRadioButton("");
		add(_5);
		rb[2][0] = _5;
		
		JRadioButton _6 = new JRadioButton("");
		add(_6);
		rb[2][1] = _6;
		
		JRadioButton _7 = new JRadioButton("");
		add(_7);
		rb[3][0] = _7;
		
		JRadioButton _8 = new JRadioButton("");
		add(_8);
		rb[3][1] = _8;
	}
	
	public void setEditable(boolean edit) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 2; j++)
				rb[i][j].setEnabled(edit);
	}
	
	/**
	 * Non Graphic Block
	 * */

	@Override
	public String toString() {
		String toRet = "";
		
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 2; j++)
				toRet += (rb[i][j].isSelected()) ? "1" : "0";
		
		return toRet;
	}
	
	public void setRep(String rep) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 2; j++)
				rb[i][j].setSelected((rep.charAt(2*i + j) == '1') ? true : false);
	}
}
