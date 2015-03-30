package com.mysimplecount;

/**
 * Interface de type "observer" pour mettre en place le pattern Observer.
 * @author Julie
 *
 */

public interface IObserver {
	
	/**
	 * Enum afin d'identifier le type de l'observer.
	 * @author Julie
	 *
	 */
	public enum obsType {
		CALC,
		WINDOW
	}
	
	public void update(String name, Window.eType type);
}
