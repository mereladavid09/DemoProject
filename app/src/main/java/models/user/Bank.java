package models.user;

public class Bank {
    private String cardExpire;
    private String cardnumber;
    private String cardType;
    private String currency;
    private String iban;

    public Bank(String cardExpire, String cardnumber, String cardType, String currency, String iban) {
        this.cardExpire = cardExpire;
        this.cardnumber = cardnumber;
        this.cardType = cardType;
        this.currency = currency;
        this.iban = iban;
    }

    public String getCardExpire() {
        return cardExpire;
    }

    public void setCardExpire(String cardExpire) {
        this.cardExpire = cardExpire;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
