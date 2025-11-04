package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySLQAuthDataAccess implements AuthDAO {

    public MySLQAuthDataAccess() {
        try {
            configureDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String createAuth(String username) throws UnauthorizedException {
        try {
            var statement = "INSERT INTO AuthData (authToken, username) VALUES (?, ?)";
            String authToken = UUID.randomUUID().toString();
            executeUpdate(statement, authToken, username);
            return authToken;
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @Override
    public String getAuthToken(String authToken) throws UnauthorizedException {
        try {
            var statement = "SELECT authToken FROM AuthData WHERE authToken = ?";
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, authToken);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return (rs.getString("authToken"));
                }
            }
        } catch (Exception e) {
            throw new UnauthorizedException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void deleteAuthToken(String authToken) throws UnauthorizedException {
        try {
            var statement = "DELETE FROM AuthData WHERE authToken = ?";
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, authToken);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new UnauthorizedException(String.format("Unable to read data: %s", e.getMessage()));
        }

    }

    @Override
    public void clearAuthTokens() {
        String statement = "TRUNCATE AuthData";
        try {
            executeUpdate(statement);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getUsername(String authToken) {
        return "";
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS AuthData (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY(`authToken`),
              INDEX(`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };


    private int executeUpdate(String statement, Object... params) throws SQLException, DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof ChessGame g) ps.setString(i + 1, g.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new SQLException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

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
