package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util;
    private Transaction transaction = null;
    public UserDaoHibernateImpl() {
        this.util = new Util();
    }

    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS mydbtest.users (id INT PRIMARY KEY AUTO_INCREMENT," +
                                    "name VARCHAR(40)," +
                                    "lastName VARCHAR(40)," +
                                    "age INT)");
        query.executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("Таблица создана");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS mydbtest.users;");
            query.executeUpdate();
            transaction.commit();
            session.close();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().openSession()) {
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("Юсер сохранен");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            System.out.println("Юзер удален");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            Query query = session.createQuery(cq);
            List<User> userList = query.getResultList();
            return userList;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery("Delete User");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
