import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class User {
    private String firstName;
    private String lastName;
    private String userID;

    public User(String firstName, String lastName, String userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return  "Имя: " + firstName + '\n' +
                "Фамилия: " + lastName + '\n' +
                "Идентификационный номер пользователя: " + userID;
    }
}