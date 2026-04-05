package number_system_observer;

class BinaryObserver extends Observer {
	BinaryObserver(Subject subject) {
		this.subject = subject;
	}

	@Override
	void update() {
		observerState = Integer.toBinaryString(subject.getState());
	}

}
