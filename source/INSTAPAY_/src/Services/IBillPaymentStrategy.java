package Services;

import Entites.Bill;

public interface IBillPaymentStrategy
{
    boolean PayBill(Bill bill);
}

