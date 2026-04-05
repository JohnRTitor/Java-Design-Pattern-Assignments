package ghost_singleton_observer_factory;

class Poltergeist implements Ghost {
	public String update(String playerName) {
		return "Poltergeist senses " + playerName + " entering!\n";
	}

	public String scare() {
		return "Poltergeist throws objects violently!\n";
	}
}