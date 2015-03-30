package com.mysimplecount;

/**
 * Coeur du programme : cree les objets necessaires et lance la calculatrice.
 * @author Julie
 *
 */

public class Program {
	
	public static void main(String[] args) {
		ObserVal val = new ObserVal();
		Window win = new Window("Calculatrice", 450, 350, val);
		Calc c = new Calc(val);

		val.addObserver(win.getLabel());
		val.addObserver(c);
	}
}
