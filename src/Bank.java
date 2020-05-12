import banknote.Banknote;

import java.util.List;

import static banknote.Banknote.*;

public class Bank {

    private final List<Bill> fundsList;
    private int attemptedWithdraw=0;
    public Bank() {
        fundsList = List.of(new Bill(THOUSAND, 2),
                new Bill(FIVE_HUNDRED, 3),
                new Bill(HUNDRED, 5));
    }

    private int atmBalance() {
        return fundsList.stream()
                .mapToInt(bill -> bill.getBankNote().value() * bill.getQuantity())
                .sum();
    }

    public boolean isFundsAvailable(int withdraw) {
        return withdraw > atmBalance();
    }

    private void updateQuantity(int thousandsBills, int fiveHundredsBills, int hundredsBills) {
        updateQuantity(THOUSAND, thousandsBills);
        updateQuantity(FIVE_HUNDRED, fiveHundredsBills);
        updateQuantity(HUNDRED, hundredsBills);
    }

    public void updateQuantity(Banknote banknote, int quantity) {
        fundsList.stream()
                .filter(bill -> bill.getBankNote().equals(banknote))
                .forEach(bill -> bill.setQuantity(quantity));
    }

    public int getBillQuantity(Banknote banknote){
        return fundsList.stream()
                .filter(bill->bill.getBankNote().equals(banknote))
                .mapToInt(Bill::getQuantity)
                .findAny()
                .getAsInt();
    }

    public Transaction calculateTransaction(Transaction transaction) {
        transaction.setAttemptedWithdrawals(++attemptedWithdraw);
        if (isFundsAvailable(transaction.getWithdrawal())) {
            transaction.setTransactionSuccessful(false);
            return transaction;
        }

        int thousandsBills = getBillQuantity(THOUSAND);
        int fiveHundredsBills = getBillQuantity(FIVE_HUNDRED);
        int hundredsBills = getBillQuantity(HUNDRED);

        WithdrawsHandler withdrawsHandler =
                new WithdrawsHandler(transaction, thousandsBills, fiveHundredsBills, hundredsBills)
                        .invoke();

        int leftToWithdraw = withdrawsHandler.getLeftToWithdraw();
        int thousandsToWithdraw = withdrawsHandler.getThousandsToWithdraw();
        int fiveHundredsToWithdraw = withdrawsHandler.getFiveHundredsToWithdraw();
        int hundredsToWithdraw = withdrawsHandler.getHundredsToWithdraw();

        // if we still have money left to withdraw that means insufficient bills
        // Otherwise we update the ATM bills count
        if (leftToWithdraw != 0) {
            transaction.setTransactionSuccessful(false);
        } else {
            thousandsBills = withdrawsHandler.subtractQuantity(thousandsBills, thousandsToWithdraw);
            fiveHundredsBills = withdrawsHandler.subtractQuantity(fiveHundredsBills, fiveHundredsToWithdraw);
            hundredsBills = withdrawsHandler.subtractQuantity(hundredsBills, hundredsToWithdraw);
            updateQuantity(thousandsBills, fiveHundredsBills, hundredsBills);
            // I got to have that UUID :D
            transaction.setTransactionSuccessful(true);
        }
        return transaction;
    }

}
