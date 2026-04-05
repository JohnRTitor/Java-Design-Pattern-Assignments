package credit_card_factory;

/**
 * Factory pattern:
 * Reads a raw record, validates the card number rules,
 * and returns the correct CreditCard subclass instance.
 * Returns null if the number is invalid.
 */
public class CreditCardFactory {

    /**
     * Parse one CSV line "number,expiry,name" and return the right card.
     * Returns null if the number doesn't match any valid card type.
     */
    public static CreditCard createFromRecord(String record) {
        if (record == null || record.trim().isEmpty()) return null;

        String[] parts = record.split(",");
        if (parts.length != 3) return null;

        String number = parts[0].trim();
        String expiry = parts[1].trim();
        String holder = parts[2].trim();

        return create(number, expiry, holder);
    }

    /**
     * Validate and create CreditCard from individual fields.
     */
    public static CreditCard create(String number, String expiry, String holder) {
        if (!number.matches("\\d+")) return null;   // must be all digits

        int len = number.length();
        char d1 = number.charAt(0);
        char d2 = len >= 2 ? number.charAt(1) : '?';

        // MasterCard: starts with 5, second digit 1-5, length 16
        if (d1 == '5' && d2 >= '1' && d2 <= '5' && len == 16)
            return new MasterCard(number, expiry, holder);

        // Visa: starts with 4, length 13 or 16
        if (d1 == '4' && (len == 13 || len == 16))
            return new Visa(number, expiry, holder);

        // American Express: starts with 3, second digit 4 or 7, length 15
        if (d1 == '3' && (d2 == '4' || d2 == '7') && len == 15)
            return new AmericanExpress(number, expiry, holder);

        // Discover: starts with 6011, length 16
        if (number.startsWith("6011") && len == 16)
            return new Discover(number, expiry, holder);

        return null;   // invalid
    }
}
