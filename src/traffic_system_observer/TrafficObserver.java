package traffic_system_observer;

/**
 * Observer Pattern - Observer interface. Any class that wants to receive
 * traffic signal updates must implement this.
 */
interface TrafficObserver {
	void onStateChange(String signalName, SignalState newState);
}
