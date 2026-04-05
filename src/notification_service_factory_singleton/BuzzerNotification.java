package notification_service_factory_singleton;

class BuzzerNotification implements NotificationService {

	private static BuzzerNotification instance;

	private BuzzerNotification() {
		System.out.println("[Singleton] New instance created for BuzzerNotification");
	}

	public static synchronized BuzzerNotification getInstance() {
		if (instance == null) {
			instance = new BuzzerNotification();
		} else {
			System.out.println("[Singleton] Returning existing instance of BuzzerNotification");
		}
		return instance;
	}

	@Override
	public void notifyUser() {
		System.out.println("Buzzer is ringing..");
	}
}