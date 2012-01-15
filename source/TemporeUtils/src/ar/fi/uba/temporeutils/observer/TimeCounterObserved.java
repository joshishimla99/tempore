package ar.fi.uba.temporeutils.observer;

public interface TimeCounterObserved {
	public void addObserver (TimeCounterObserver observer);
	public void removeObserver(TimeCounterObserver observer);
	public void notifyObservers();
}
