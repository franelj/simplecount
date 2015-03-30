package com.mysimplecount;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * La classe Button etend la classe JButton et implemente l'interface ActionListener.
 * @author Julie
 *
 */

public class Button extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	private String _name;
	
	private int _gridx;
	private int _gridy;
	private int _gridwidth;
	private int _gridheight;
	
	private Window.eType _type;
	private ObserVal _value;
	
	
	/**
	 * Le constructeur prend le nom et les informations de taille et position du bouton, ainsi que l'objet ObserVal de la Window, afin de le modifier lorsqu'un bouton est clique et ainsi initier les actions necessaires des observateurs.
	 * @param label Le label du bouton
	 * @param toPlace Le tableau d'int contenant les positions x et y, la largeur et la hauteur du bouton
	 * @param val L'objet ObserVal a modifier en cas d'action sur le bouton
	 */
	
	public Button(String label, int[] toPlace, ObserVal val) {
		this.setText(label);
		this.setFont(new Font("Tahoma", Font.BOLD, 11));
		this.setForeground(new Color(0, 0, 0));
		this.setBorderPainted(false);
		this.setPreferredSize(new Dimension(80, 25));
		this.setBackground(new Color(130, 130, 130));
		this._name = label;
		this._gridx = toPlace[0];
		this._gridy = toPlace[1];
		this._gridwidth = toPlace[2];
		this._gridheight = toPlace[3];
		this._type = this.setType(label);
		this._value = val;
		this.addActionListener(this);
	}
	
	
	/**
	 * Cette fonction initialise l'affichage du bouton en remplissant un objet GridBagConstraints ajoute au JPanel passes en parametre.
	 * @param _gbc L'objet GridBagConstraints a remplir
	 * @param _pan Le JPanel auquel ajouter l'objet GridBagConstraints
	 */
	
	public void renderButton(GridBagConstraints _gbc, JPanel _pan) {
		_gbc.gridx = this.getGridx();
		_gbc.gridy = this.getGridy();
		_gbc.gridwidth = this.getGridwidth();
		_gbc.gridheight = this.getGridheight();
		_gbc.weightx = this.getGridwidth();
		_gbc.weighty = this.getGridheight();
		_gbc.insets = new Insets(2, 1, 1, (this.getGridx() == 6 ? 1 : 2));
		_gbc.anchor = GridBagConstraints.WEST;
		_gbc.fill = GridBagConstraints.BOTH;
		_pan.add(this, _gbc);
	}
	
	
	/**
	 * Cette fonction retourne la variable membre name.
	 * @return String
	 */
	
	public String getName() {
		return this._name; 
	}
	
	
	/**
	 * Cette fonction retourne la variable membre gridx.
	 * @return int
	 */
	
	public int getGridx() {
		return this._gridx;
	}
	
	
	/**
	 * Cette fonction retourne la variable membre gridy.
	 * @return int
	 */

	public int getGridy() {
		return this._gridy;
	}
	
	
	/**
	 * Cette fonction retourne la variable membre gridwidth.
	 * @return int.
	 */
	
	public int getGridwidth() {
		return this._gridwidth;
	}
	
	
	/**
	 * Cette fonction retourne la variable membre gridheight.
	 * @return int
	 */
	
	public int getGridheight() {
		return this._gridheight;
	}
	

	/**
	 * Cette fonction retourne le type du bouton en fonction de son nom.
	 * @param name Le label du bouton
	 * @return Window.eType
	 */
	
	private Window.eType setType(String name) {
		char c = name.charAt(0);
		
		if (c == 'M')
			return (Window.eType.BMEMORY);
		else if ((c >= '0' && c <= '9') && name.length() == 1)
			return (Window.eType.BNUMBER);
		else if (c == 'D')
			return (Window.eType.BDEL);
		else if (c == '=')
			return (Window.eType.BEQUAL);
		else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')')
			return (Window.eType.BOPERATOR);
		else if (c == '.')
			return (Window.eType.BPOINT);
		else if (c == 'C' && name.length() == 1)
			return (Window.eType.BC);
		return (Window.eType.BOPERATION);
	}
	
	
	/**
	 * Cette fonction appelle la fonction setObservable de l'objet ObserVal lorsqu'il detecte un clic sur un bouton.
	 */
	
	public void actionPerformed(ActionEvent e) {
		this._value.setObservable(this._name, this._type, IObserver.obsType.WINDOW);
	}
}
