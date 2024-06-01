

package Authentication;

import Services.WalletService;
import Entites.AccountType;

public class WalletAuthenticationService extends UserAuthenticationService {
    public WalletAuthenticationService() {
        this.validationService = new ValidationService(new WalletService());
        this.accountService = new WalletService();
    }

    public AccountType getAccountType() {
        return AccountType.WalletAccount;
    }
}
