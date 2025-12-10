package dataaccess;

import chess.ChessGame;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import model.GameData;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLGameDataAccess implements GameDAO{

    public MySQLGameDataAccess() {
        try {
            configureDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public String createGame(String gameName) throws DataAccessException {
                String statement = "INSERT INTO GameData ("
                        + "whiteUsername, blackUsername, gameName, game) VALUES(?, ?, ?, ?)";

                // Serialize and store the friend JSON.
                var json = new Gson().toJson(new ChessGame());
               try {
               int id = executeUpdate(statement, null, null, gameName, json);
               return String.valueOf(id);
                } catch (DataAccessException e) {
                throw new DataAccessException(e.getMessage());
                } catch (SQLException e) {
                   throw new RuntimeException(e.getMessage());
               }
        }


    @Override
    public GameData getGame(String gameID) throws UnauthorizedException, DataAccessException {
        try {
            var statement = "SELECT * FROM GameData WHERE gameID = ?";
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, Integer.parseInt(gameID));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return readGame(rs);
                }
                else {
                    throw new UnauthorizedException("Game not found");
                }
            }
        } catch (SQLException e){
            throw new UnauthorizedException(String.format("Unable to read data: %s", e.getMessage()));
        } catch (DataAccessException e ) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var gameID = rs.getString("gameID");
        var gameName = rs.getString("gameName");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var game = rs.getString("game");
        var realGame = new Gson().fromJson(game, ChessGame.class);

        return new GameData(gameID, gameName, whiteUsername, blackUsername, realGame);
    }


    @Override
    public Collection<GameData> listGames() throws DataAccessException, UnauthorizedException {

        Collection<GameData> listGames = new ArrayList<>();

        try {
            var statement = "SELECT * FROM GameData";
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(statement);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    listGames.add(readGame(rs));
                }
            }
        } catch (SQLException e){
            throw new UnauthorizedException(String.format("Unable to read data: %s", e.getMessage()));
        } catch (DataAccessException e ) {
            throw new DataAccessException(e.getMessage());
        }
        return listGames;
    }

    @Override
    public void updateGame(String gameId, GameData gameData) throws DataAccessException, UnauthorizedException {
        try {
            var statement = "UPDATE GameData SET game = ? WHERE gameID = ?";
            String gameString = new Gson().toJson(gameData.game());
            executeUpdate(statement, gameString, Integer.parseInt(gameId));
        } catch (SQLException e){
            throw new UnauthorizedException(String.format("Unable to read data: %s", e.getMessage()));
        } catch (DataAccessException e ) {
            throw new DataAccessException(e.getMessage());
        }
    }



    @Override
    public void joinGame(String newUsername, ChessGame.TeamColor playerColor, String gameId) throws
            AlreadyTakenException, UnauthorizedException, DataAccessException {

        try {
            //If the game is found and exists
            var statement = "SELECT * FROM GameData WHERE gameID = ?";
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(statement);
            ps.setInt(1, Integer.parseInt(gameId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                var theGame = readGame(rs);
                helperJoin(theGame, newUsername, playerColor, gameId);
                //Update Data bast
            }
            else {
                throw new UnauthorizedException("Game does not exist");
            }
        } catch (SQLException e) {
            throw new UnauthorizedException(((String.format("Unable to read data: %s", e.getMessage()))));
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        } catch (AlreadyTakenException e) {
            throw new AlreadyTakenException(e.getMessage());
        }
    }

    private void helperJoin(GameData theGame, String newUsername, ChessGame.TeamColor playerColor, String gameId) throws
            AlreadyTakenException, DataAccessException, SQLException {

        Connection conn = DatabaseManager.getConnection();

            if (playerColor == ChessGame.TeamColor.WHITE) {
                if (Objects.equals(theGame.whiteUsername(), null)) {
                    String statement = "UPDATE GameData SET whiteUsername = ? WHERE gameID = ?";
                    PreparedStatement ps = conn.prepareStatement(statement);
                    ps.setInt(2, Integer.parseInt(gameId));
                    ps.setString(1, newUsername);
                    ps.executeUpdate();
                } else {
                    throw new AlreadyTakenException("Color already filled");
                }
            }

            if (playerColor == ChessGame.TeamColor.BLACK) {
                if (Objects.equals(theGame.blackUsername(), null)) {
                    String statement = "UPDATE GameData SET blackUsername = ? WHERE gameID = ?";
                    PreparedStatement ps = conn.prepareStatement(statement);
                    ps.setInt(2, Integer.parseInt(gameId));
                    ps.setString(1, newUsername);
                    ps.executeUpdate();
                } else {
                    throw new AlreadyTakenException("Color already filled");
                }
            }
    }


    @Override
    public void clearGames() throws DataAccessException {
        String statement = "TRUNCATE GameData";
        try {
            executeUpdate(statement);
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(((String.format("Unable to read data: %s", e.getMessage()))));
        }
    }

    private int executeUpdate(String statement, Object... params) throws SQLException, DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS);
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    switch (param) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case null -> ps.setNull(i + 1, NULL);
                        default -> {
                        }
                    }
                }
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
        } catch (SQLException e) {
            throw new SQLException(String.format("Unable to update database: %s", e.getMessage()));
        } catch (DataAccessException e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS GameData (
              `gameID` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
              `whiteUsername` varchar(256) NULL,
              `blackUsername` varchar(256) NULL,
              `gameName` varchar(256) NOT NULL,
              `game` JSON NOT NULL,
              INDEX (`whiteUsername`),
              INDEX (`blackUsername`),
              INDEX(`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        MySQLUserDataAccess.configuredatabase(createStatements);
    }
}
