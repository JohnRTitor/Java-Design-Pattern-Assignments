package credit_card_factory;

import java.util.List;

public class DemoClass {

    public static void main(String[] args) {
        String filePath = "credit_card_factory/cards.txt";

        System.out.println("=== Credit Card Validation System ===\n");

        List<CardFileReader.Result> results = CardFileReader.readFile(filePath);

        int valid = 0, invalid = 0;
        for (CardFileReader.Result r : results) {
            if (r.isValid()) {
                System.out.println("[VALID]   " + r.card);
                valid++;
            } else {
                System.out.println("[INVALID] " + r.raw);
                invalid++;
            }
        }

        System.out.println("\nTotal: " + results.size()
            + "  Valid: " + valid + "  Invalid: " + invalid);
    }
}
