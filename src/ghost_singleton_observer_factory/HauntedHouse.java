package ghost_singleton_observer_factory;

import java.util.ArrayList;
import java.util.List;

class HauntedHouse {

	private static HauntedHouse instance;
	private List<Ghost> ghosts = new ArrayList<>();

	private HauntedHouse() {
	}

	public static HauntedHouse getInstance() {
		if (instance == null) {
			instance = new HauntedHouse();
		}
		return instance;
	}

	public void addGhost(Ghost ghost) {
		ghosts.add(ghost);
	}

	public String notifyGhosts(String playerName) {

		String result = "Player " + playerName + " entered the Haunted House!\n\n";

		for (Ghost ghost : ghosts) {
			result += ghost.update(playerName);
			result += ghost.scare();
			result += "\n";
		}

		return result;
	}
}
