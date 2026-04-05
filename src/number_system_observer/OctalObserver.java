package number_system_observer;

class OctalObserver extends Observer {
	OctalObserver(Subject subject) {
		this.subject = subject;
	}

	@Override
	void update() {
		observerState = Integer.toOctalString(subject.getState());
	}

}
