package ghost_singleton_observer_factory;

class Phantom implements Ghost {
	public String update(String playerName) {
		return "Phantom watches from the shadows...\n";
	}

	public String scare() {
		return "Phantom passes silently through the player!\n";
	}
}