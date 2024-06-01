

package Services;

import Authentication.SignInManager;
import Entites.User;
import Repositories.IUserRepository;
import Repositories.InMemoryUserRepository;

public class TransferToInstapayStrategy implements ITransferStratgey {
    private IUserRepository userRepository;

    public TransferToInstapayStrategy() {
        this.userRepository = InMemoryUserRepository.getRepository();
    }

    public boolean TransferAmount(String userName, double amount) {
        User sourceUser = SignInManager.CurrentLoggedInUser;
        User targetUser = this.userRepository.getByUsername(userName);
        sourceUser.setBalance(sourceUser.getBalance() - amount);
        targetUser.setBalance(targetUser.getBalance() + amount);
        return true;
    }
}
