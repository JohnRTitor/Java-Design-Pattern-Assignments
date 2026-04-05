package knife_factory;

// ── Steel Knives ──────────────────────────────────────────────────────────────

class SteelCurvedKnife extends Knife {
	public SteelCurvedKnife() {
		material = "Steel";
		shape = "Curved";
	}

	public void sharpen() {
		System.out.println("Sharpening Steel Curved Knife on whetstone.");
	}

	public void polish() {
		System.out.println("Polishing Steel Curved Knife to mirror finish.");
	}

	public void pack() {
		System.out.println("Packing Steel Curved Knife in leather sheath.");
	}
}

class SteelStraightKnife extends Knife {
	public SteelStraightKnife() {
		material = "Steel";
		shape = "Straight";
	}

	public void sharpen() {
		System.out.println("Sharpening Steel Straight Knife on whetstone.");
	}

	public void polish() {
		System.out.println("Polishing Steel Straight Knife to mirror finish.");
	}

	public void pack() {
		System.out.println("Packing Steel Straight Knife in leather sheath.");
	}
}

// ── Iron Knives ───────────────────────────────────────────────────────────────

class IronCurvedKnife extends Knife {
	public IronCurvedKnife() {
		material = "Iron";
		shape = "Curved";
	}

	public void sharpen() {
		System.out.println("Sharpening Iron Curved Knife on grinding wheel.");
	}

	public void polish() {
		System.out.println("Polishing Iron Curved Knife with rust guard.");
	}

	public void pack() {
		System.out.println("Packing Iron Curved Knife in oilcloth wrap.");
	}
}

class IronStraightKnife extends Knife {
	public IronStraightKnife() {
		material = "Iron";
		shape = "Straight";
	}

	public void sharpen() {
		System.out.println("Sharpening Iron Straight Knife on grinding wheel.");
	}

	public void polish() {
		System.out.println("Polishing Iron Straight Knife with rust guard.");
	}

	public void pack() {
		System.out.println("Packing Iron Straight Knife in oilcloth wrap.");
	}
}

// ── Titanium Knives ───────────────────────────────────────────────────────────

class TitaniumCurvedKnife extends Knife {
	public TitaniumCurvedKnife() {
		material = "Titanium";
		shape = "Curved";
	}

	public void sharpen() {
		System.out.println("Sharpening Titanium Curved Knife with diamond file.");
	}

	public void polish() {
		System.out.println("Polishing Titanium Curved Knife with nano coat.");
	}

	public void pack() {
		System.out.println("Packing Titanium Curved Knife in premium case.");
	}
}

class TitaniumStraightKnife extends Knife {
	public TitaniumStraightKnife() {
		material = "Titanium";
		shape = "Straight";
	}

	public void sharpen() {
		System.out.println("Sharpening Titanium Straight Knife with diamond file.");
	}

	public void polish() {
		System.out.println("Polishing Titanium Straight Knife with nano coat.");
	}

	public void pack() {
		System.out.println("Packing Titanium Straight Knife in premium case.");
	}
}
