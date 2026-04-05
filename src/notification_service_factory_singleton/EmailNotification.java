package notification_service_factory_singleton;

class EmailNotification implements NotificationService {

	private static EmailNotification instance;

	private EmailNotification() {
		System.out.println("[Singleton] New instance created for EmailNotification");
	}

	public static synchronized EmailNotification getInstance() {
		if (instance == null) {
			instance = new EmailNotification();
		} else {
			System.out.println("[Singleton] Returning existing instance of EmailNotification");
		}
		return instance;
	}

	@Override
	public void notifyUser() {
		System.out.println("Email sent.");
	}
}