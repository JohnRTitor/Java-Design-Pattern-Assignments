package credit_card_factory;

public abstract class CreditCard {

    protected String cardNumber;
    protected String expiryDate;
    protected String holderName;
    protected String cardType;

    public CreditCard(String cardNumber, String expiryDate, String holderName, String cardType) {
        this.cardNumber  = cardNumber;
        this.expiryDate  = expiryDate;
        this.holderName  = holderName;
        this.cardType    = cardType;
    }

    public String getCardNumber()  { return cardNumber;  }
    public String getExpiryDate()  { return expiryDate;  }
    public String getHolderName()  { return holderName;  }
    public String getCardType()    { return cardType;     }

    // Mask card number for display e.g. ****-****-****-1234
    public String getMasked() {
        int len = cardNumber.length();
        return "*".repeat(len - 4) + cardNumber.substring(len - 4);
    }

    public String toString() {
        return String.format("%-18s | %-16s | Expiry: %s | Holder: %s",
                cardType, getMasked(), expiryDate, holderName);
    }
}
