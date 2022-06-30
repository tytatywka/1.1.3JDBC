package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.Optional;

public class Util {

    public static Optional<Connection> getConnection() {
        try {
            return Optional.of(DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root1", "root1"));

        } catch (SQLException e) {
            System.err.println("Не удалось загрузить класс драйвера");
        }
        return Optional.empty();
    }
}
