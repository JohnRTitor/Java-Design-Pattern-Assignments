package number_system_observer;

abstract class Observer {
	protected Subject subject;
	protected String observerState;

	abstract void update();

	String getState() {
		return observerState;
	}
}
