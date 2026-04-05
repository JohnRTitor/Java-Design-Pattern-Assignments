package notification_service_factory;

class NotificationFactory {
	public static Notification getNotification(String type) {
		if (type == "" || type == null) {
			return null;
		}

		if (type.equalsIgnoreCase("Email")) {
			return new EmailNotification();
		}
		if (type.equalsIgnoreCase("SMS")) {
			return new SMSNotification();
		}

		if (type.equalsIgnoreCase("Buzzer")) {
			return new BuzzerNotification();
		}

		return null;
	}
}
