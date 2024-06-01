

package Authentication;

import Services.BankService;
import Entites.AccountType;

public class BankAuthenticationService extends UserAuthenticationService {
    public BankAuthenticationService() {
        this.validationService = new ValidationService(new BankService());
        this.accountService = new BankService();
    }

    public AccountType getAccountType() {
        return AccountType.BankAccount;
    }
}
