package websocket.commands;

import chess.ChessMove;

import java.util.Objects;

public class MakeMoveCommand extends UserGameCommand {

    private ChessMove TheMove;

    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessMove move) {

        super(commandType, authToken, gameID);
        TheMove = move;
    }

    public ChessMove getTheMove() {
        return TheMove;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MakeMoveCommand that = (MakeMoveCommand) o;
        return Objects.equals(TheMove, that.TheMove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), TheMove);
    }
}
