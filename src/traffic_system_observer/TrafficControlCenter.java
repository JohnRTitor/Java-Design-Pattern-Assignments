package traffic_system_observer;

/**
 * Observer Pattern - Concrete Observer. The Traffic Control Center receives
 * notifications from all signals whenever their state changes and updates the
 * GUI directly.
 */
class TrafficControlCenter implements TrafficObserver {

	private final TrafficSystemGUI gui;

	public TrafficControlCenter(TrafficSystemGUI gui) {
		this.gui = gui;
	}

	@Override
	public void onStateChange(String signalName, SignalState newState) {
		// Update the status bar on the EDT whenever any signal changes
		javax.swing.SwingUtilities.invokeLater(
				() -> gui.statusLabel.setText("Control Center: " + signalName + " changed to " + newState));
	}
}
