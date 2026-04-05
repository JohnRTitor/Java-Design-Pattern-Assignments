package pizza;

public class ChickenPizza extends Pizza {

    public ChickenPizza(String style) {
        this.type  = "Chicken";
        this.style = style;
        this.name  = style + " Chicken Pizza";
    }
}
