package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import model.UserData;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLUserDataAccess implements UserDAO{

    @Override
    public UserData getUser(String username) throws UnauthorizedException {
        try {
            var statement = "SELECT username FROM UserData WHERE username = ?";
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return readUser(rs);
                }
            }
        } catch (Exception e){
            throw new UnauthorizedException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    private UserData readUser(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        UserData user = new Gson().fromJson(json, UserData.class);
        return user;
    }


    @Override
    public UserData createUser(String username, String password, String email) throws AlreadyTakenException {
        try {
            var statement = "INSERT INTO UserData (username, password, email) VALUES (?, ?, ?)";
            int id = executeUpdate(statement, username, password, email);
            return new UserData(username, password, email);
        } catch (DataAccessException e) {
            throw new AlreadyTakenException(e.getMessage());
        }
    }

    @Override
    public void clearUsers() throws DataAccessException {
        String statement = "TRUNCATE UserData";
        try {
            executeUpdate(statement);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
//                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
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
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  UserData (
              `username` varchar(256) NOT NULL,
              'password' varchar(256) NOT NULL,
              'email' varchar(256) NOT NULL,
              PRIMARY KEY(username),
              INDEX(name)
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
