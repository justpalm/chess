package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

import static dataaccess.MySLQAuthDataAccess.cyclethrough;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLUserDataAccess implements UserDAO{

    public MySQLUserDataAccess() {
        try {
            configureDatabase();
        } catch (Exception e) {
            throw new RuntimeException("Error" + e.getMessage());
        }
    }

    @Override
    public UserData getUser(String username) throws UnauthorizedException, DataAccessException {
        try {
            var statement = "SELECT username, password, email FROM UserData WHERE username = ?";
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserData(rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"));
                }
                else {
                    throw new SQLException("User not found");
                }
            }
        } catch (DataAccessException e) {
            throw new DataAccessException(String.format("Error" + e.getMessage()));

        } catch (SQLException e) {
            throw new UnauthorizedException(String.format("\"Error\" + Unable to read data: %s", e.getMessage()));
        }
    }


    @Override
    public UserData createUser(String username, String password, String email) throws AlreadyTakenException, DataAccessException  {
        try {
            var statement = "INSERT INTO UserData (username, password, email) VALUES (?, ?, ?)";
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            int id = executeUpdate(statement, username, hashedPassword, email);
            return new UserData(username, password, email);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error" + e.getMessage());
        } catch (SQLException e) {
            throw new AlreadyTakenException("Error" + e.getMessage());
        }
    }

    @Override
    public void clearUsers() throws DataAccessException {
        String statement = "TRUNCATE UserData";
        try {
            executeUpdate(statement);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error" + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException("Error" + e.getMessage());
        }

    }


    private int executeUpdate(String statement, Object... params) throws SQLException, DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                return cyclethrough(ps, params);
            }
        } catch (SQLException e) {
            throw new SQLException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  UserData (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY(`username`),
              INDEX(`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}
