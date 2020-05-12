import java.util.HashMap;
import java.util.Map;

public class ATM {

    private final Bank bank;

    public ATM() {
        bank =new Bank();
    }

    private void formatTransaction(Transaction transaction) {
        String dashes = "- ".repeat(30);
        String header = "|                   redmind atm v1.0                      |";
        String formattedTransation = String.format("%s %n%S %n%s%n%s%n%n%s", dashes, header, dashes, transaction, dashes);
        showTransaction(formattedTransation);
    }

    private void showTransaction(String formattedTransation){
        System.out.println(formattedTransation);
    }

    public void startTransaction(int amount){
        Transaction transaction = new Transaction(amount);
        Transaction calculatedTransaction = bank.calculateTransaction(transaction);
        formatTransaction(calculatedTransaction);
    }

}
