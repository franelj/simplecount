package com.mysimplecount;

/**
 * La partie logique du programme, qui contient les objets Memory et Pile. Implemente l'interface IObserver. Represente le Model du pattern MVC.
 * @author Julie
 * @see IObserver
 *
 */

public class Calc implements IObserver {
	Pile _pile = new Pile();
	Memory _mem = new Memory();
	public ObserVal _value;
	
	
	/**
	 * Le constructeur prend l'objet ObserVal cree dans le main en parametre, afin de pouvoir le modifier et notifier l'objet Window qu'il doit mettre a jour son affichage.
	 * @param val L'objet ObserVal a stocker dans la variable membre value
	 */
	
	public Calc(ObserVal val) {
		this._value = val;
	}
	
	
	/**
	 * Retourne l'objet membre _value.
	 * @return ObserVal
	 */
	
	public ObserVal getObserVal() {
		return (this._value);
	}
	
	
	/**
	 * Fonction de mise a jour de l'objet Calc, appele par ObserVal quand il est modifie. C'est la que Calc regarde s'il doit faire des calculs, modifier sa memoire, ou ne rien faire.
	 */
	
	public void update(String name, Window.eType type) {
		if (type == Window.eType.BEQUAL) {
		    try {
			this._pile.npi(this._value.getLastExpr());
			String res = this._pile.doCalc();
			this._pile.clear();
			this._value.setObservable(res, Window.eType.BNUMBER, IObserver.obsType.CALC);
		    }
		    catch (NumberFormatException e) {
			this._pile.clear();
			System.out.println("\033[1;33mUne exception est survenue. Merci d'entrer des expressions valides.\033[0m");
			this._value.setObservable("0", Window.eType.BNUMBER, IObserver.obsType.CALC);
		    }
		    catch (ArrayIndexOutOfBoundsException e) {
			this._pile.clear();
			System.out.println("\033[1;33mUne exception est survenue. Merci d'entrer des expressions valides.\033[0m");
			this._value.setObservable("0", Window.eType.BNUMBER, IObserver.obsType.CALC);
		    }
		    catch (Exception e) {
			
		    }
		}
		else if (type == Window.eType.BMEMORY) {
		    try {
			this._value.setObservable(this._mem.handleMem(name, this._value), Window.eType.BMEMORY, IObserver.obsType.CALC);
		    }
		    catch (NumberFormatException e) {
			System.out.println("\033[1;33mUne exception est survenue. Merci d'entrer des expressions valides.\033[0m");
			this._value.setObservable("0", Window.eType.BNUMBER, IObserver.obsType.CALC);
		    }
		    catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("\033[1;33mUne exception est survenue. Merci d'entrer des expressions valides.\033[0m");
			this._value.setObservable("0", Window.eType.BNUMBER, IObserver.obsType.CALC);
		    }
		    catch (Exception e) {
			
		    }
		}
	}
};
