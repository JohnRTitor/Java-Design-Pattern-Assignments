package pizza;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PizzaStore store = new PizzaStore();

        System.out.println("Types: chicken, paneer, corn");
        System.out.print("Enter pizza type: ");
        String type = sc.nextLine();

        store.orderPizza(type);
        sc.close();
    }
}
