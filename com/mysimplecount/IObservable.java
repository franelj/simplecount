package com.mysimplecount;

/**
 * Interface de type "observable" pour mettre en place le pattern Observer.
 * @author Julie
 *
 */

public interface IObservable {
	public void addObserver(IObserver obs);
	public void updateObserver(IObserver.obsType type);
}
