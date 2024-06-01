

package Services;

import Authentication.SignInManager;
import Entites.User;

public class TransferToWalletStrategy implements ITransferStratgey {
    private IExternalAccountService externalAccountService = new WalletService();

    public TransferToWalletStrategy() {
    }

    public boolean TransferAmount(String toMobileNumber, double amount) {
        User sourceUser = SignInManager.CurrentLoggedInUser;
        Account targetUser = this.externalAccountService.GetAccount(toMobileNumber);
        sourceUser.setBalance(sourceUser.getBalance() - amount);
        targetUser.balance += amount;
        return true;
    }
}
