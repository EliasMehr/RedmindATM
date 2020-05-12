import java.util.UUID;

public class Transaction {

    private final UUID transactionId;
    private int withdrawal;
    private boolean isTransactionSuccessful;
    private int attemptedWithdrawals=0;

    public Transaction(int withdrawal) {
        this.transactionId = UUID.randomUUID();
        this.withdrawal = withdrawal;
        this.isTransactionSuccessful = false;
    }

    public int getWithdrawal() {
        return withdrawal;
    }

    public void setAttemptedWithdrawals(int attemptedWithdrawals) {
        this.attemptedWithdrawals = attemptedWithdrawals;
    }

    public void setTransactionSuccessful(boolean transactionSuccessful) {
        isTransactionSuccessful = transactionSuccessful;
    }

    @Override
    public String toString() {
        String transactionDetails = isTransactionSuccessful ? "Transaction successful" : "Insufficient funds ";
        return String.format(" Transaction id: %s%n Withdrawal: %s %n Attempted withdraw: %s%n %S", transactionId, withdrawal,attemptedWithdrawals, transactionDetails);
    }
}
