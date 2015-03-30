package com.mysimplecount;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * La classe Label etend la classe JLabel et implemente l'interface IObserver. Elle observe l'objet ObserVal de la Window afin de determiner si elle doit mettre a jour l'affichage du texte.
 * @author Julie
 *
 */

public class Label extends JLabel implements IObserver {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Le constructeur initialise le label avec son texte, son alignement, sa bordure, sa police.
	 */

	public Label() {
		this.setText("0");
		this.setHorizontalAlignment(RIGHT);
		this.setHorizontalTextPosition(RIGHT);
		this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		this.setFont(new Font("Verdana", Font.BOLD, 14));
		this.setForeground(new Color(0, 0, 0));
	}
	
	
	/**
	 * Cette fonction determine l'affichage du texte en remplissant l'objet GridBagConstraints ajoute JPanel passes en parametre.
	 * @param _gbc L'objet GridBagConstraints a remplir
	 * @param _pan Le JPanel auquel ajouter l'objet GridBagConstraints
	 */
	
	public void renderLabel(GridBagConstraints _gbc, JPanel _pan) {
		_gbc.gridx = 0;
		_gbc.gridy = 0;
		_gbc.gridwidth = 7;
		_gbc.gridheight = 1;
		_gbc.weighty = 2;
		_gbc.insets = new Insets(2, 2, 2, 2);
		_gbc.fill = GridBagConstraints.BOTH;
		_pan.add(this, _gbc);
	}
	
	
	/**
	 * Cette fonction est appelee par ObserVal lorsque ce dernier est mis a jour, afin que le Label rafraichisse son affichage.
	 */

	public void update(String _label, Window.eType type) {
		if (_label == "")
			this.setText("0");
		else
			this.setText(_label);
	}
}
