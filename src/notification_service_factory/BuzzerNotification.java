package notification_service_factory;

class BuzzerNotification implements Notification {
	@Override
	public void notifyUser() {
		System.out.println("Buzzer is ringing..");
	}
}
