

package Authentication;

import Services.IExternalAccountService;
import Entites.AccountType;
import Entites.User;
import Repositories.IUserRepository;
import Repositories.InMemoryUserRepository;

public abstract class UserAuthenticationService {
    private IUserRepository userRepository;
    protected IExternalAccountService accountService;
    public ValidationService validationService;

    protected UserAuthenticationService() {
        this.userRepository = InMemoryUserRepository.getRepository();
    }

    private boolean isRegisterableUser(String userName, String password) {
        User user = this.userRepository.getByUsername(userName);

        return this.validationService.validatedMobileNumber ||
                this.validationService.confirmedOtp ||
                user != null;
    }

    public boolean registerUser(User user) {
        if (!this.isRegisterableUser(user.getUserName(), user.getPassword())) {
            return false;
        } else {
            this.userRepository.addUser(new User(user.getUserName(), user.getPassword(), user.getMobileNumber(), this.accountService.GetBalance(user.getMobileNumber()), this.getAccountType()));
            return true;
        }
    }

    public abstract AccountType getAccountType();
}
