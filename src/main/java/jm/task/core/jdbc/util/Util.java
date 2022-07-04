package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static final String url = "jdbc:mysql://localhost:3306/mysql?useSSL=false";
    private static final String user = "root1";
    private static final String password = "root1";
    public static Optional<Connection> getConnection() {
        try {
            return Optional.of(DriverManager.getConnection(url, user, password));

        } catch (SQLException e) {
            System.err.println("Не удалось загрузить класс драйвера");
        }
        return Optional.empty();
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, url);
                settings.put(Environment.USER, user);
                settings.put(Environment.PASS, password);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.DEFAULT_SCHEMA,"mysql");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.err.println("Не удалось загрузить класс драйвера");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
