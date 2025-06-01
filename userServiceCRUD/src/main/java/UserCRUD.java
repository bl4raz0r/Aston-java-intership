import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class UserCRUD implements CRUD {

    private static final Logger logger = LoggerFactory.getLogger(UserCRUD.class);
    private final SessionFactory sessionFactory;

    public UserCRUD(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User readUser(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(User.class, id);
        } catch (Exception e) {
            logger.error("Ошибка при чтении пользователя с ID: {}", id, e);
            return null;
        }
    }

    @Override
    public void createUser(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Ошибка при создании пользователя: {}", user, e);
        }
    }

    @Override
    public void updateUser(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Ошибка при обновлении пользователя: {}", user, e);
        }
    }

    @Override
    public void deleteUser(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Ошибка при удалении пользователя с ID: {}", id, e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User";
            return session.createQuery(hql, User.class).getResultList();
        } catch (Exception e) {
            logger.error("Ошибка при получении списка пользователей", e);
            throw new RuntimeException("Ошибка при получении списка пользователей", e);
        }
    }
}