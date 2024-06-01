
package Services;

import Authentication.IMobileNumberValidator;

public class WalletService implements IMobileNumberValidator, IExternalAccountService {
    public WalletService() {
    }

    public boolean ValidateMobileNumber(String mobileNumber) {
        return true;
    }

    public double GetBalance(String mobileNumber) {
        return 1000.0;
    }

    public Account GetAccount(String mobileNumber) {
        return new Account("Wallet User", 240.0);
    }
}
