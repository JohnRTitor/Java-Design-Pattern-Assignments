package notification_service_factory_singleton;

public class NotificationServiceFactory {
	public static NotificationService getNotification(String type) {
		if (type == "" || type == null) {
			return null;
		}

		if (type.equalsIgnoreCase("Email")) {
			return EmailNotification.getInstance();
		}
		if (type.equalsIgnoreCase("SMS")) {
			return SMSNotification.getInstance();
		}

		if (type.equalsIgnoreCase("Buzzer")) {
			return BuzzerNotification.getInstance();
		}

		return null;
	}
}
