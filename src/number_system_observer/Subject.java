package number_system_observer;

import java.util.ArrayList;
import java.util.List;

abstract class Subject {
	protected List<Observer> observers = new ArrayList<Observer>();

	abstract void attach(Observer ob);

	abstract void detach(Observer ob);

	abstract int getState();

	List<Observer> getObservers() {
		return observers;
	}

	public void notifyUser() {
		for (Observer ob : observers) {
			ob.update();
		}
	}
}
