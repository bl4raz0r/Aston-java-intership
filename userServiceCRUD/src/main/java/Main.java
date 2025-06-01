import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = null;
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            logger.error("Ошибка при создании SessionFactory", e);
            System.exit(1);
        }

        UserCRUD userCRUD = new UserCRUD(sessionFactory);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Прочитать пользователя по ID");
            System.out.println("3. Обновить пользователя");
            System.out.println("4. Удалить пользователя по ID");
            System.out.println("5. Посмотреть список пользователей");
            System.out.println("6. Выход");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Введите число от 1 до 6.");
                scanner.next();
                continue;
            }
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createUser(scanner, userCRUD);
                    break;
                case 2:
                    readUser(scanner, userCRUD);
                    break;
                case 3:
                    updateUser(scanner, userCRUD);
                    break;
                case 4:
                    deleteUser(scanner, userCRUD);
                    break;
                case 5:
                    listUsers(userCRUD);
                    break;
                case 6:
                    System.out.println("Выход из программы.");
                    if (sessionFactory != null) {
                        sessionFactory.close();
                    }
                    return;
                default:
                    System.out.println("Некорректный выбор. Введите число от 1 до 6.");
            }
        }
    }

    private static void createUser(Scanner scanner, UserCRUD userCRUD) {
        try {
            System.out.println("Введите имя:");
            String name = scanner.nextLine();
            System.out.println("Введите email:");
            String email = scanner.nextLine();
            System.out.println("Введите возраст:");
            int age = scanner.nextInt();
            scanner.nextLine();

            User newUser = new User(name, email, age, LocalDateTime.now());
            userCRUD.createUser(newUser);
            System.out.println("Пользователь успешно создан.");
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод возраста.");
            scanner.next();
        } catch (Exception e) {
            logger.error("Ошибка при создании пользователя через консоль", e);
            System.out.println("Ошибка при создании пользователя.");
        }
    }

    private static void readUser(Scanner scanner, UserCRUD userCRUD) {
        try {
            System.out.println("Введите ID пользователя для чтения:");
            long id = scanner.nextLong();
            scanner.nextLine();

            User user = userCRUD.readUser(id);
            if (user != null) {
                System.out.println(user);
            } else {
                System.out.println("Пользователь с ID " + id + " не найден.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод ID.");
            scanner.next();
        } catch (Exception e) {
            logger.error("Ошибка при чтении пользователя через консоль", e);
            System.out.println("Ошибка при чтении пользователя.");
        }
    }

    private static void updateUser(Scanner scanner, UserCRUD userCRUD) {
        try {
            System.out.println("Введите ID пользователя для обновления:");
            long id = scanner.nextLong();
            scanner.nextLine();

            User existingUser = userCRUD.readUser(id);
            if (existingUser == null) {
                System.out.println("Пользователь с ID " + id + " не найден.");
                return;
            }

            System.out.println("Введите новое имя (оставьте пустым, чтобы не менять):");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                existingUser.setName(name);
            }

            System.out.println("Введите новый email (оставьте пустым, чтобы не менять):");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                existingUser.setEmail(email);
            }

            System.out.println("Введите новый возраст (оставьте пустым, чтобы не менять):");
            String ageString = scanner.nextLine();
            if (!ageString.isEmpty()) {
                existingUser.setAge(Integer.parseInt(ageString));
            }

            userCRUD.updateUser(existingUser);
            System.out.println("Пользователь успешно обновлен.");
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод ID или возраста.");
            scanner.next();
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат возраста.");
        } catch (Exception e) {
            logger.error("Ошибка при обновлении пользователя через консоль", e);
            System.out.println("Ошибка при обновлении пользователя.");
        }
    }

    private static void deleteUser(Scanner scanner, UserCRUD userCRUD) {
        try {
            System.out.println("Введите ID пользователя для удаления:");
            long id = scanner.nextLong();
            scanner.nextLine();

            userCRUD.deleteUser(id);
            System.out.println("Пользователь успешно удален.");
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод ID.");
            scanner.next();
        } catch (Exception e) {
            logger.error("Ошибка при удалении пользователя через консоль", e);
            System.out.println("Ошибка при удалении пользователя.");
        }
    }

    private static void listUsers(UserCRUD userCRUD) {
        try {
            userCRUD.getAllUsers().forEach(user -> {
                System.out.println("Пользовательская информация: ");
                System.out.println("Идентификационный номер: " + user.getId());
                System.out.println("Имя: " + user.getName());
                System.out.println("Почтовый адрес: " + user.getEmail());
                System.out.println("Возраст: " + user.getAge());
                System.out.println("Время создания: " + user.getFormattedCreationTime());
                System.out.println();

            });
        } catch (Exception e) {
            logger.error("Ошибка при получении списка пользователей через консоль", e);
            System.out.println("Ошибка при получении списка пользователей.");
        }
    }
}
