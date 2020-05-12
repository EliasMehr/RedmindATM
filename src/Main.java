public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();

        atm.startTransaction(1500);
        atm.startTransaction(700);
        atm.startTransaction(400);
        atm.startTransaction(1100);
        atm.startTransaction(1000);
        atm.startTransaction(700);
        atm.startTransaction(300);
    }
}
