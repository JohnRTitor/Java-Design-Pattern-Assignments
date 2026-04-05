package number_system_observer;

class HexObserver extends Observer {
	HexObserver(Subject subject) {
		this.subject = subject;
	}

	@Override
	void update() {
		observerState = Integer.toHexString(subject.getState()).toUpperCase();
	}

}