import banknote.Banknote;

public class Bill {

    private final Banknote bankNote;
    private int quantity;

    public Bill(Banknote bankNote, int quantity) {
        this.bankNote = bankNote;
        this.quantity = quantity;
    }

    public Banknote getBankNote() {
        return bankNote;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("Withdrawal %nBanknote: %s %nQuantity: %s %n ", bankNote, quantity);
    }

}
