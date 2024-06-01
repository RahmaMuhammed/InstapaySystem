package Services;

import Authentication.SignInManager;
import Entites.Bill;

public class ApplicationService {
    private ITransferStratgey transferStratgey;
    private IBillPaymentStrategy billPaymentStrategy;

    public ApplicationService() {
    }

    public boolean transferAmount(String toMobileNumber, double amount, ITransferStratgey transferStratgey) {
        this.transferStratgey = transferStratgey;
        return this.transferStratgey.TransferAmount(toMobileNumber, amount);
    }
    public boolean payBill(Bill bill, IBillPaymentStrategy paymentStrategy)
    {
        this.billPaymentStrategy = paymentStrategy;
        return billPaymentStrategy.PayBill(bill);
    }
    public double inquireBalance() {
        return SignInManager.CurrentLoggedInUser.getBalance();
    }
}
