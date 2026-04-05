package ghost_singleton_observer_factory;

class Wraith implements Ghost {
	public String update(String playerName) {
		return "Wraith whispers around " + playerName + "...\n";
	}

	public String scare() {
		return "Wraith lets out a chilling scream!\n";
	}
}
