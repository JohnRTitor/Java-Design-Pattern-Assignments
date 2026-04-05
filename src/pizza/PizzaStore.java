package pizza;

public class PizzaStore {

    // Order by type only - style defaults to American
    public Pizza orderPizza(String type) {
        return orderPizza(type, "American");
    }

    // Order by type and style
    public Pizza orderPizza(String type, String style) {
        Pizza pizza = null;

        switch (type.toLowerCase()) {
            case "chicken": pizza = new ChickenPizza(style); break;
            case "paneer":  pizza = new PaneerPizza(style);  break;
            case "corn":    pizza = new CornPizza(style);    break;
            default:
                System.out.println("Unknown pizza type: " + type);
                return null;
        }

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        System.out.println(pizza.getName() + " is ready!");
        return pizza;
    }
}
