import java.util.function.Function;

import static banknote.Banknote.*;

public class WithdrawsHandler {
    private Transaction transaction;
    private int thousandsBills;
    private int fiveHundredsBills;
    private int hundredsBills;
    private int leftToWithdraw;
    private int thousandsToWithdraw;
    private int fiveHundredsToWithdraw;
    private int hundredsToWithdraw;

    public WithdrawsHandler(Transaction transaction, int thousandsBills, int fiveHundredsBills, int hundredsBills) {
        this.transaction = transaction;
        this.thousandsBills = thousandsBills;
        this.fiveHundredsBills = fiveHundredsBills;
        this.hundredsBills = hundredsBills;
    }

    private final Function<Integer, Integer> checkForThousands = amount -> amount / 1000;
    private final Function<Integer, Integer> checkForFiveHundreds = amount -> amount / 500;
    private final Function<Integer, Integer> checkForHundreds = amount -> amount / 100;

    public int subtractQuantity(int thousandsBills, int thousandsToWithdraw) {
        return thousandsBills - thousandsToWithdraw;
    }

    private int calculateAmountWithdraw(int fiveHundredsBills, int leftToWithdraw, Function<Integer, Integer> checkForFiveHundreds) {
        int fiveHundredsToWithdraw = checkForFiveHundreds.apply(leftToWithdraw);
        if (fiveHundredsToWithdraw > fiveHundredsBills) fiveHundredsToWithdraw = fiveHundredsBills;
        return fiveHundredsToWithdraw;
    }

    public WithdrawsHandler invoke() {
        // Tracking the remaining amount to withdraw
        leftToWithdraw = transaction.getWithdrawal();
        // Calculating the amount of thousands that we want to withdraw
        thousandsToWithdraw = calculateAmountWithdraw(thousandsBills, leftToWithdraw, checkForThousands);
        // Updating whats left to withdraw for other bills
        leftToWithdraw = subtractQuantity(leftToWithdraw, thousandsToWithdraw * THOUSAND.value());

        // Repeating the process for 500's
        fiveHundredsToWithdraw = calculateAmountWithdraw(fiveHundredsBills, leftToWithdraw, checkForFiveHundreds);

        leftToWithdraw = subtractQuantity(leftToWithdraw, fiveHundredsToWithdraw * FIVE_HUNDRED.value());

        // Repeating the process for 100's
        hundredsToWithdraw = calculateAmountWithdraw(hundredsBills, leftToWithdraw, checkForHundreds);

        leftToWithdraw = subtractQuantity(leftToWithdraw, hundredsToWithdraw * HUNDRED.value());
        return this;
    }

    public int getLeftToWithdraw() {
        return leftToWithdraw;
    }

    public int getThousandsToWithdraw() {
        return thousandsToWithdraw;
    }

    public int getFiveHundredsToWithdraw() {
        return fiveHundredsToWithdraw;
    }

    public int getHundredsToWithdraw() {
        return hundredsToWithdraw;
    }
}