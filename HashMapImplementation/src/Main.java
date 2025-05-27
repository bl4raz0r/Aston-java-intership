import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, User> users = new HashMap<>();

        User firstUser = new User("Иван","Иванов");
        User secondUser = new User("Петр","Петров");

        String firstUserID = "WO5N441KM3";
        String secondUserID = "42NJO985VI";

        users.put(firstUserID, firstUser);
        users.put(secondUserID, secondUser);

        String currentUserID = firstUserID;
        User currentUser = users.get(currentUserID);

        if (currentUser != null) {
            System.out.println("Пользовательская информация: \n" + currentUser);
            System.out.println("Идентификационный номер: " + currentUserID);

            users.remove(currentUserID);

            if (users.get(currentUserID) == null) {
                System.out.println("Пользователь c идентификационным номером \"" + currentUserID + "\" удален");
            } else {
                System.out.println("Ошибка при удалении пользователя");
            }
        } else {
            System.out.println("Пользователь c идентификационным номером " + currentUserID + " не найден");
        }
    }
}