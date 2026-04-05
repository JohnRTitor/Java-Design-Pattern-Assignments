package credit_card_factory;

import java.io.*;
import java.util.*;

public class CardFileReader {

    public static class Result {
        public String     raw;
        public CreditCard card;     // null if invalid
        public Result(String raw, CreditCard card) {
            this.raw  = raw;
            this.card = card;
        }
        public boolean isValid() { return card != null; }
    }

    public static List<Result> readFile(String filePath) {
        List<Result> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    CreditCard card = CreditCardFactory.createFromRecord(line);
                    results.add(new Result(line, card));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return results;
    }
}
