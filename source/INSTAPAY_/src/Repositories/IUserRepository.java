

package Repositories;

import Entites.User;

public interface IUserRepository {
    User getByUsername(String var1);

    void addUser(User var1);
}
