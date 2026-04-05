package pizza;

public abstract class Pizza {

    protected String name;
    protected String type;
    protected String style;

    public void prepare() {
        System.out.println("Preparing " + name);
    }

    public void bake() {
        System.out.println("Baking " + name);
    }

    public void cut() {
        System.out.println("Cutting " + name);
    }

    public void box() {
        System.out.println("Boxing " + name);
    }

    public String getName()  { return name;  }
    public String getType()  { return type;  }
    public String getStyle() { return style; }

    public String toString() {
        return name + " (Type: " + type + ", Style: " + style + ")";
    }
}
