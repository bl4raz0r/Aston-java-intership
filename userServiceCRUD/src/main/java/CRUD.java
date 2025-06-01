import java.util.List;

public interface CRUD {
    User readUser(long id);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
    List<User> getAllUsers();
}
