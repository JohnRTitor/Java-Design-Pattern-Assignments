package notification_service_factory;

class SMSNotification implements Notification {
	@Override
	public void notifyUser() {
		System.out.println("SMS sent.");
	}
}
