

package Services;

import Authentication.IMobileNumberValidator;

public class BankService implements IMobileNumberValidator, IExternalAccountService {
    public BankService() {
    }

    public boolean ValidateMobileNumber(String mobileNumber) {
        return true;
    }

    public  double GetBalance(String mobileNumber) {
        return 1000.0;
    }

    public Account GetAccount(String accountNo) {
        return new Account("Bank User", 240.0);
    }
}
