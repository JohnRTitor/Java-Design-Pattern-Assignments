package notification_service_factory;

class EmailNotification implements Notification {
	@Override
	public void notifyUser() {
		System.out.println("Email sent.");
	}
}
