package knife_factory;

// Produces Steel knives
class SteelKnifeFactory extends KnifeFactory {
	public Knife createKnife(String shape) {
		switch (shape.toLowerCase()) {
		case "curved":
			return new SteelCurvedKnife();
		case "straight":
			return new SteelStraightKnife();
		default:
			return null;
		}
	}
}

// Produces Iron knives
class IronKnifeFactory extends KnifeFactory {
	public Knife createKnife(String shape) {
		switch (shape.toLowerCase()) {
		case "curved":
			return new IronCurvedKnife();
		case "straight":
			return new IronStraightKnife();
		default:
			return null;
		}
	}
}

// Produces Titanium knives
class TitaniumKnifeFactory extends KnifeFactory {
	public Knife createKnife(String shape) {
		switch (shape.toLowerCase()) {
		case "curved":
			return new TitaniumCurvedKnife();
		case "straight":
			return new TitaniumStraightKnife();
		default:
			return null;
		}
	}
}
