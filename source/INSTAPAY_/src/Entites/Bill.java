package Entites;

public abstract class Bill {
    protected double amount;

    protected Bill(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

