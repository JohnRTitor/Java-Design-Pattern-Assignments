package knife_factory;

/**
 * Abstract Factory — declares the Factory Method createKnife(). Subclasses
 * decide which Knife to instantiate based on shape.
 */
abstract class KnifeFactory {

	// Factory Method — subclasses implement this
	public abstract Knife createKnife(String shape);

	// Template method — calls factory method then runs production steps
	public Knife orderKnife(String shape) {
		Knife knife = createKnife(shape);
		if (knife == null) {
			System.out.println("Unknown shape: " + shape);
			return null;
		}
		System.out.println("\n--- Producing: " + knife.getName() + " ---");
		knife.sharpen();
		knife.polish();
		knife.pack();
		System.out.println(knife.getName() + " is ready.\n");
		return knife;
	}
}
