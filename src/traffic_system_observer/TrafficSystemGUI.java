package traffic_system_observer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

class TrafficSystemGUI extends JFrame {

	// Signals (Subjects)
	TrafficSignal signal1 = new TrafficSignal("North");
	TrafficSignal signal2 = new TrafficSignal("South");
	TrafficSignal signal3 = new TrafficSignal("East");
	TrafficSignal signal4 = new TrafficSignal("West");

	// Observer Pattern: one shared Control Center observing all signals
	TrafficControlCenter controlCenter = new TrafficControlCenter(this);

	// Signal labels
	JLabel label1 = new JLabel("North: RED", SwingConstants.CENTER);
	JLabel label2 = new JLabel("South: RED", SwingConstants.CENTER);
	JLabel label3 = new JLabel("East:  RED", SwingConstants.CENTER);
	JLabel label4 = new JLabel("West:  RED", SwingConstants.CENTER);

	// Per-signal toggle buttons
	JToggleButton toggle1 = new JToggleButton("Pause");
	JToggleButton toggle2 = new JToggleButton("Pause");
	JToggleButton toggle3 = new JToggleButton("Pause");
	JToggleButton toggle4 = new JToggleButton("Pause");

	// Global buttons
	JButton startBtn = new JButton("Start");
	JButton stopBtn = new JButton("Stop All");

	// Status bar — updated by the Control Center observer
	JLabel statusLabel = new JLabel("Press Start to begin", SwingConstants.CENTER);

	// UI refresh timer
	Timer uiTimer;

	public TrafficSystemGUI() {
		setTitle("Smart Traffic Management System");
		setSize(520, 360);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(10, 10));

		// ── Signal Panel ──
		JPanel signalPanel = new JPanel(new GridLayout(4, 2, 5, 5));
		signalPanel.setBorder(BorderFactory.createTitledBorder("Traffic Signals"));

		Font labelFont = new Font("Arial", Font.BOLD, 15);
		JLabel[] labels = { label1, label2, label3, label4 };
		JToggleButton[] toggles = { toggle1, toggle2, toggle3, toggle4 };

		for (JLabel lbl : labels) {
			lbl.setOpaque(true);
			lbl.setFont(labelFont);
			lbl.setBackground(Color.RED);
			lbl.setForeground(Color.WHITE);
		}
		for (JToggleButton btn : toggles) {
			btn.setFont(new Font("Arial", Font.BOLD, 12));
			btn.setEnabled(false);
			btn.setBackground(new Color(255, 193, 7));
			btn.setFocusPainted(false);
		}

		signalPanel.add(label1);
		signalPanel.add(toggle1);
		signalPanel.add(label2);
		signalPanel.add(toggle2);
		signalPanel.add(label3);
		signalPanel.add(toggle3);
		signalPanel.add(label4);
		signalPanel.add(toggle4);
		add(signalPanel, BorderLayout.CENTER);

		// ── Top Button Panel ──
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(startBtn);
		buttonPanel.add(stopBtn);
		stopBtn.setEnabled(false);
		add(buttonPanel, BorderLayout.NORTH);

		// ── Status Bar (driven by Control Center) ──
		statusLabel.setBorder(BorderFactory.createTitledBorder("Traffic Control Center (Observer)"));
		add(statusLabel, BorderLayout.SOUTH);

		// ── Toggle Actions ──
		toggle1.addActionListener(e -> handleToggle(signal1, toggle1, "North"));
		toggle2.addActionListener(e -> handleToggle(signal2, toggle2, "South"));
		toggle3.addActionListener(e -> handleToggle(signal3, toggle3, "East"));
		toggle4.addActionListener(e -> handleToggle(signal4, toggle4, "West"));

		// ── Global Button Actions ──
		startBtn.addActionListener(e -> startAll());
		stopBtn.addActionListener(e -> stopAll());

		// ── UI Timer: refresh signal colours every 300ms ──
		uiTimer = new Timer(300, e -> updateLabels());

		setVisible(true);
	}

	void handleToggle(TrafficSignal sig, JToggleButton btn, String name) {
		if (btn.isSelected()) {
			sig.pauseSignal();
			btn.setText("Resume");
			btn.setBackground(new Color(40, 167, 69));
			btn.setForeground(Color.WHITE);
		} else {
			sig.resumeSignal();
			btn.setText("Pause");
			btn.setBackground(new Color(255, 193, 7));
			btn.setForeground(Color.BLACK);
		}
	}

	void startAll() {
		signal1 = new TrafficSignal("North");
		signal2 = new TrafficSignal("South");
		signal3 = new TrafficSignal("East");
		signal4 = new TrafficSignal("West");

		// Observer Pattern: register control center with every signal
		for (TrafficSignal sig : new TrafficSignal[] { signal1, signal2, signal3, signal4 }) {
			sig.addObserver(controlCenter);
			sig.setDaemon(true);
			sig.start();
		}

		JToggleButton[] toggles = { toggle1, toggle2, toggle3, toggle4 };
		for (JToggleButton btn : toggles) {
			btn.setSelected(false);
			btn.setText("Pause");
			btn.setBackground(new Color(255, 193, 7));
			btn.setForeground(Color.BLACK);
			btn.setEnabled(true);
		}

		uiTimer.start();
		startBtn.setEnabled(false);
		stopBtn.setEnabled(true);
		statusLabel.setText("Signals running...");
	}

	void stopAll() {
		for (TrafficSignal sig : new TrafficSignal[] { signal1, signal2, signal3, signal4 }) {
			sig.stopSignal();
		}
		uiTimer.stop();

		JToggleButton[] toggles = { toggle1, toggle2, toggle3, toggle4 };
		for (JToggleButton btn : toggles)
			btn.setEnabled(false);

		startBtn.setEnabled(true);
		stopBtn.setEnabled(false);
		statusLabel.setText("Signals stopped. Press Start to restart.");

		String[] names = { "North", "South", "East", "West" };
		JLabel[] labels = { label1, label2, label3, label4 };
		for (int i = 0; i < 4; i++) {
			labels[i].setText(names[i] + ": RED");
			labels[i].setBackground(Color.RED);
			labels[i].setForeground(Color.WHITE);
		}
	}

	void updateLabels() {
		updateOne(label1, signal1);
		updateOne(label2, signal2);
		updateOne(label3, signal3);
		updateOne(label4, signal4);
	}

	void updateOne(JLabel label, TrafficSignal sig) {
		SignalState state = sig.currentState;
		String extra = sig.isPaused() ? " [PAUSED]" : "";
		label.setText(sig.name + ": " + state + extra);
		if (state == SignalState.GREEN) {
			label.setBackground(Color.GREEN);
			label.setForeground(Color.BLACK);
		} else if (state == SignalState.YELLOW) {
			label.setBackground(Color.YELLOW);
			label.setForeground(Color.BLACK);
		} else {
			label.setBackground(Color.RED);
			label.setForeground(Color.WHITE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(TrafficSystemGUI::new);
	}
}
