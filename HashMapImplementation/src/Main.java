import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<User, BankAccount> users = new HashMap<>();

        User firstUser = new User("Иван","Иванов","WO5N441KM3");
        User secondUser = new User("Петр","Петров","42NJO985VI");

        BankAccount fisrtUserBankAccount = new BankAccount("R13JWF31G3", 0);
        BankAccount secondUserBankAccount = new BankAccount("YH9Q8831VF", 0);

        users.put(firstUser, fisrtUserBankAccount);
        users.put(secondUser, secondUserBankAccount);

        User currentUser = firstUser;

        BankAccount bankAccountInformation = users.get(currentUser);

        if (bankAccountInformation != null) {
            System.out.println("Пользовательская информация: \n" + currentUser + "\n" + bankAccountInformation);

            users.remove(currentUser);
            if (users.get(currentUser) == null) {
                System.out.println("Пользователь c идентификационным номером \"" + currentUser.getUserID() + "\" удален");
            } else {
                System.out.println("Ошибка при удалении пользователя");
            }
        } else {
            System.out.println("Пользователь " + currentUser + " не найден");
        }
    }
}