package knife_factory;

abstract class Knife {

	protected String material; // Steel, Iron, Titanium
	protected String shape; // Curved, Straight

	public abstract void sharpen();

	public abstract void polish();

	public abstract void pack();

	public String getMaterial() {
		return material;
	}

	public String getShape() {
		return shape;
	}

	public String getName() {
		return shape + " " + material + " Knife";
	}

	public String toString() {
		return "Knife [Material=" + material + ", Shape=" + shape + "]";
	}
}
