package pizza;

import java.util.Scanner;

public class ModifiedClient {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PizzaStore store = new PizzaStore();

        System.out.println("Types : chicken, paneer, corn");
        System.out.println("Styles: SouthIndian, NorthIndian, American");

        System.out.print("Enter pizza type : ");
        String type = sc.nextLine();

        System.out.print("Enter pizza style: ");
        String style = sc.nextLine();

        store.orderPizza(type, style);
        sc.close();
    }
}
