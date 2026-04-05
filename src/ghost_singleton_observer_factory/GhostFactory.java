package ghost_singleton_observer_factory;

class GhostFactory {

	public static Ghost createGhost(String type) {

		if (type.equalsIgnoreCase("Poltergeist"))
			return new Poltergeist();
		else if (type.equalsIgnoreCase("Wraith"))
			return new Wraith();
		else if (type.equalsIgnoreCase("Phantom"))
			return new Phantom();

		return null;
	}
}