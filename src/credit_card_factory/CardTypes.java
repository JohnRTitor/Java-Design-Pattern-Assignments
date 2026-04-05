package credit_card_factory;

class MasterCard extends CreditCard {
    public MasterCard(String number, String expiry, String holder) {
        super(number, expiry, holder, "MasterCard");
    }
}

class Visa extends CreditCard {
    public Visa(String number, String expiry, String holder) {
        super(number, expiry, holder, "Visa");
    }
}

class AmericanExpress extends CreditCard {
    public AmericanExpress(String number, String expiry, String holder) {
        super(number, expiry, holder, "American Express");
    }
}

class Discover extends CreditCard {
    public Discover(String number, String expiry, String holder) {
        super(number, expiry, holder, "Discover");
    }
}
