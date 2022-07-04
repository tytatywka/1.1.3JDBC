package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
     //   Util.getConnection();

       // UserDao userDao = new UserDaoJDBCImpl();
        Util.getSessionFactory();
        UserDao userDao = new UserDaoHibernateImpl();
      //  userDao.createUsersTable();
       userDao.saveUser("Name1", "LastName1", (byte) 20);
       userDao.saveUser("Name2", "LastName2", (byte) 25);
       userDao.saveUser("Name3", "LastName3", (byte) 31);
       userDao.saveUser("Name4", "LastName4", (byte) 38);
       userDao.removeUserById(1);

        userDao.getAllUsers();
        for (User user: userDao.getAllUsers()
        ) {
            System.out.println(userDao.getAllUsers().toString());
        }
        userDao.cleanUsersTable();
        //userDao.dropUsersTable();


    }
}
