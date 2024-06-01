package Services;

import Entites.*;

public class BillGenerator {
    public static Bill generateBill(BillType billType, double amount) {
        switch (billType) {
            case Gas:
                return new GasBill(amount);
            case Electricity:
                return new ElectricityBill(amount);
            case Water:
                return new WaterBill(amount);
            default:
                throw new IllegalArgumentException("Invalid bill type");
        }
    }
}

