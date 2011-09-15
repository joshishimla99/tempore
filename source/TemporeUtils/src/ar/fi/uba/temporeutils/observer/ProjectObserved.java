package ar.fi.uba.temporeutils.observer;

public interface ProjectObserved {
	public void addObserver (ProjectObserver observer);
	public void removeObserver(ProjectObserver observer);
	public void notifyObservers();
}
