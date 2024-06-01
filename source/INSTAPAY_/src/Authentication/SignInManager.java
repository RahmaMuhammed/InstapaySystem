

package Authentication;

import Entites.User;
import Repositories.IUserRepository;
import Repositories.InMemoryUserRepository;

public class SignInManager {
    private IUserRepository userRepository;
    public static User CurrentLoggedInUser;

    public SignInManager() {
        this.userRepository = InMemoryUserRepository.getRepository();
    }

    public boolean SignIn(String username, String password) {
        User user = this.userRepository.getByUsername(username);
        if (user == null) {
            return false;
        } else {
            boolean result = user.getPassword().equals(password);
            if (result) {
                CurrentLoggedInUser = user;
                return true;
            } else {
                return false;
            }
        }
    }
}
