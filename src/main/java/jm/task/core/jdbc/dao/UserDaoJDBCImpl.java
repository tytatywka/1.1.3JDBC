package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util;
    public UserDaoJDBCImpl() {
        this.util = new Util();
    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = Util.getConnection().get().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS mydbtest.userr (id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(40)," +
                    "lastName VARCHAR(40)," +
                    "age INT)");
        } catch (SQLException e) {
        System.out.println(e.getMessage());
        System.out.println("Статус ошибки - " + e.getSQLState());
    }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().get().createStatement()) {
            statement.execute("DROP TABLE IF EXISTS mydbtest.userr;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Статус ошибки - " + e.getSQLState());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Optional<Connection> connection = Util.getConnection();
        if (connection.isPresent()) {
            String query = "insert into mydbtest.userr (name, lastName, age) values (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.get().prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setLong(3, age);
                if (preparedStatement.executeUpdate() > 0) {
                    System.out.println("User was add");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        Optional<Connection> connection = Util.getConnection();

        if (connection.isPresent()) {
            String query = "DELETE FROM mydbtest.userr WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.get().prepareStatement(query)) {
                preparedStatement.setLong(1, id);
                if (preparedStatement.executeUpdate() > 0) {
                    System.out.println("User was removed");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
        public List<User> getAllUsers() {
            List<User> list = new ArrayList<>();
            Optional<Connection> connection = Util.getConnection();
            try (PreparedStatement preparedStatement = connection.get().prepareStatement("SELECT * FROM mydbtest.userr;")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong(1));
                    user.setName(resultSet.getString(2));
                    user.setLastName(resultSet.getString(3));
                    user.setAge(resultSet.getByte(4));
                    list.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().get().createStatement()) {
            statement.execute("delete from mydbtest.userr;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Статус ошибки - " + e.getSQLState());
        }
    }
}