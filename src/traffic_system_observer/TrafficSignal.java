package traffic_system_observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern - Subject (Observable). Maintains a list of observers and
 * notifies them on every state change.
 */
public class TrafficSignal extends Thread {

	String name;
	SignalState currentState;
	volatile boolean running;
	volatile boolean paused;

	private final Object pauseLock = new Object();

	// Observer Pattern: list of registered observers
	private final List<TrafficObserver> observers = new ArrayList<>();

	public TrafficSignal(String name) {
		this.name = name;
		this.currentState = SignalState.RED;
		this.running = false;
		this.paused = false;
	}

	// Observer Pattern: register an observer
	public void addObserver(TrafficObserver observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}

	// Observer Pattern: remove an observer
	public void removeObserver(TrafficObserver observer) {
		synchronized (observers) {
			observers.remove(observer);
		}
	}

	// Observer Pattern: notify all observers of a state change
	private void notifyObservers(SignalState state) {
		synchronized (observers) {
			for (TrafficObserver obs : observers) {
				obs.onStateChange(name, state);
			}
		}
	}

	private void changeState(SignalState state) {
		currentState = state;
		notifyObservers(state); // <- notify every time state changes
	}

	public void stopSignal() {
		running = false;
		resumeSignal();
		interrupt();
	}

	public void pauseSignal() {
		paused = true;
	}

	public void resumeSignal() {
		synchronized (pauseLock) {
			paused = false;
			pauseLock.notifyAll();
		}
	}

	public boolean isPaused() {
		return paused;
	}

	private void checkPause() throws InterruptedException {
		synchronized (pauseLock) {
			while (paused && running) {
				pauseLock.wait();
			}
		}
	}

	private void sleepWithPause(long ms) throws InterruptedException {
		long end = System.currentTimeMillis() + ms;
		while (System.currentTimeMillis() < end && running) {
			checkPause();
			long remaining = end - System.currentTimeMillis();
			if (remaining > 0)
				Thread.sleep(Math.min(remaining, 100));
		}
	}

	@Override
	public void run() {
		running = true;
		try {
			while (running) {
				changeState(SignalState.GREEN);
				sleepWithPause(5000);

				changeState(SignalState.YELLOW);
				sleepWithPause(2000);

				changeState(SignalState.RED);
				sleepWithPause(5000);
			}
		} catch (InterruptedException e) {
			// signal stopped
		}
	}
}
