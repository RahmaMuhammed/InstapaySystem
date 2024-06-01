package Services;

import Authentication.SignInManager;
import Entites.Bill;

public class ElectricityBillPaymentStrategy implements IBillPaymentStrategy {
    public boolean PayBill(Bill bill) {
        if (SignInManager.CurrentLoggedInUser.getBalance() < bill.getAmount()) {
            return false;
        }
        SignInManager.CurrentLoggedInUser.setBalance(SignInManager.CurrentLoggedInUser.getBalance() - bill.getAmount());
        return true;
    }
}
