package knife_factory;

public class KnifeStore {

	public Knife orderKnife(String material, String shape) {
		KnifeFactory factory = getFactory(material);
		if (factory == null) {
			System.out.println("Unknown material: " + material);
			return null;
		}
		return factory.orderKnife(shape);
	}

	private KnifeFactory getFactory(String material) {
		switch (material.toLowerCase()) {
		case "steel":
			return new SteelKnifeFactory();
		case "iron":
			return new IronKnifeFactory();
		case "titanium":
			return new TitaniumKnifeFactory();
		default:
			return null;
		}
	}
}
