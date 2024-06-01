

package Repositories;

import Entites.AccountType;
import Entites.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemoryUserRepository implements IUserRepository {
    private List<User> users = new ArrayList();
    private static InMemoryUserRepository instance;

    private InMemoryUserRepository() {
        User user1 = new User();
        user1.setUserName("user1");
        user1.setPassword("#A1234");
        user1.setAccountType(AccountType.WalletAccount);
        user1.setBalance(400.0);
        user1.setMobileNumber("0123456");
        this.users.add(user1);

        User user2 = new User();
        user2.setUserName("user2");
        user2.setPassword("#B1234");
        user2.setAccountType(AccountType.BankAccount);
        user2.setBalance(500.0);
        user2.setMobileNumber("01234567");
        this.users.add(user2);

        User user3 = new User();
        user3.setUserName("user3");
        user3.setPassword("#C1234");
        user3.setAccountType(AccountType.WalletAccount);
        user3.setBalance(600.0);
        user3.setMobileNumber("012345678");
        this.users.add(user3);
    }

    public static InMemoryUserRepository getRepository() {
        if (instance == null) {
            instance = new InMemoryUserRepository();
        }

        return instance;
    }

    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        } else {
            this.users.add(user);
        }
    }

    public User getByUsername(String userName) {
        Iterator var2 = this.users.iterator();

        User user;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            user = (User)var2.next();
        } while(!user.getUserName().equals(userName));

        return user;
    }
}
