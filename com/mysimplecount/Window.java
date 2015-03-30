package com.mysimplecount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mysimplecount.Button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


/**
 * La classe Window est la partie principale de la GUI de la calculatrice. Elle etend la classe JFrame. Represente la View du pattern MVC.
 * @author Julie
 *
 */

public class Window extends JFrame { 
	private static final long serialVersionUID = 1L;
	
	private JPanel _pan = new JPanel();
	private Label _text = new Label();
	private List<Button> _buttons = new ArrayList<Button>();
	private GridBagConstraints _gbc = new GridBagConstraints();

	Map<String, int[]> _b = new HashMap<String, int[]>();
	
	private ObserVal _value;
		
	/**
	 * Enum afin d'identifier les types de boutons.
	 * @author Julie
	 *
	 */
	
	public enum eType {
		BNUMBER,
		BOPERATOR,
		BOPERATION,
		BMEMORY,
		BC,
		BPOINT,
		BEQUAL,
		BDEL
	}
	
	
	/**
	 * Le constructeur appelle la fonction d'initialisation de la fenetre, son contenu, et une hashmap contenant les informations des boutons a afficher : leur label, leur position sur la grille et leur taille. Elle prend en parametre la taille de la fenetre, son titre et l'objet ObserVal a observer.
	 * @param title Le titre de la fenetre
	 * @param width La largeur de la fenetre
	 * @param height La hauteur de la fenetre
	 * @param val L'objet ObserVal a stocker
	 */
	
	public Window(String title, int width, int height, ObserVal val) {
		this._value = val;
		this._b.put("MC", new int[]{0, 1, 1, 1});
		this._b.put("MR", new int[]{1, 1, 1, 1});
		this._b.put("MS", new int[]{2, 1, 1, 1});
		this._b.put("M+", new int[]{3, 1, 1, 1});
		this._b.put("M-", new int[]{4, 1, 1, 1});
		this._b.put("Del", new int[]{5, 1, 1, 1});
		this._b.put("C", new int[]{6, 1, 1, 1});
		this._b.put("Log", new int[]{0, 2, 1, 1});
		this._b.put("1/x", new int[]{1, 2, 1, 1});
		this._b.put("7", new int[]{2, 2, 1, 1});
		this._b.put("8", new int[]{3, 2, 1, 1});
		this._b.put("9", new int[]{4, 2, 1, 1});
		this._b.put("/", new int[]{5, 2, 1, 1});
		this._b.put("%", new int[]{6, 2, 1, 1});
		this._b.put("Cos", new int[]{0, 3, 1, 1});
		this._b.put("x2", new int[]{1, 3, 1, 1});
		this._b.put("4", new int[]{2, 3, 1, 1});
		this._b.put("5", new int[]{3, 3, 1, 1});
		this._b.put("6", new int[]{4, 3, 1, 1});
		this._b.put("*", new int[]{5, 3, 1, 1});
		this._b.put("Sqrt", new int[]{6, 3, 1, 1});
		this._b.put("Sin", new int[]{0, 4, 1, 1});
		this._b.put("(", new int[]{1, 4, 1, 1});
		this._b.put("1", new int[]{2, 4, 1, 1});
		this._b.put("2", new int[]{3, 4, 1, 1});
		this._b.put("3", new int[]{4, 4, 1, 1});
		this._b.put("-", new int[]{5, 4, 1, 1});
		this._b.put("=", new int[]{6, 4, 1, 2});
		this._b.put("Tan", new int[]{0, 5, 1, 1});
		this._b.put(")", new int[]{1, 5, 1, 1});
		this._b.put("0", new int[]{2, 5, 2, 1});
		this._b.put(".", new int[]{4, 5, 1, 1});
		this._b.put("+", new int[]{5, 5, 1, 1});		
		this.setWin(title, width, height);
	}
	
	
	/**
	 * Cette fonction initialise la fenetre : titre, taille, suppression du changement de taille, position, contenu.
	 * @param title Le titre de la fenetre
	 * @param width La largeur de la fenetre
	 * @param height La hauteur de la fenetre
	 */
	
	private void setWin(String title, int width, int height) {
		this.setTitle(title);
		this.setSize(width, height);
		this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setPanel();
		this.setFields();
		this.setContentPane(this._pan);
		this.setVisible(true);
	}
	
	
	/**
	 * Cette fonction initialise le label et les boutons.
	 */
	
	private void setFields() {
		this._text.renderLabel(this._gbc, this._pan);
		for (Map.Entry<String, int[]> entry : this._b.entrySet()) {
			Button b = new Button(entry.getKey(), entry.getValue(), this.getObserVal());
			b.renderButton(this._gbc, this._pan);
			this._buttons.add(b);			
		}
	}
	
	
	/**
	 * Cette fonction initialise le panel contenu dans la Window : taille, fond, grille a utiliser, position.
	 */
	
	private void setPanel() {
		this._pan.setPreferredSize(new Dimension(400, 300));
		this._pan.setBackground(new Color(223, 223, 223));
		this._pan.setLayout(new GridBagLayout());
		this._pan.setLocation(0, 0);
	}
	
	
	/**
	 * Cette fonction retourne l'objet ObserVal contenu dans la Window.
	 * @return ObserVal
	 */
	
	public ObserVal getObserVal() {
		return this._value;
	}
	
	
	/**
	 * Cette fonction retourne l'objet Label contenu dans la Window.
	 * @return Label
	 */
	
	public Label getLabel() {
		return this._text;
	}
}
