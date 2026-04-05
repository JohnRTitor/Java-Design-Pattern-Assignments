package number_system_observer;

class DecimalSubject extends Subject {

	private int subjectState; // for storing the decimal number

	@Override
	void attach(Observer ob) {
		if (!observers.contains(ob)) {
			observers.add(ob);
		}
	}

	@Override
	void detach(Observer ob) {
		if (observers.contains(ob)) {
			observers.remove(ob);
		}
	}

	void setState(int subjectState) {
		this.subjectState = subjectState;
	}

	@Override
	int getState() {
		return subjectState;
	}
}
