package notification_service_factory_singleton;

class SMSNotification implements NotificationService {

	private static SMSNotification instance;

	private SMSNotification() {
		System.out.println("[Singleton] New instance created for SMSNotification");
	}

	public static synchronized SMSNotification getInstance() {
		if (instance == null) {
			instance = new SMSNotification();
		} else {
			System.out.println("[Singleton] Returning existing instance of SMSNotification");
		}
		return instance;
	}

	@Override
	public void notifyUser() {
		System.out.println("SMS sent.");
	}
}