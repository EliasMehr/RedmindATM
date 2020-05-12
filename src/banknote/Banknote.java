package banknote;

public enum Banknote {
    THOUSAND(1000),
    FIVE_HUNDRED(500),
    HUNDRED(100);

    private final int value;

    Banknote(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
