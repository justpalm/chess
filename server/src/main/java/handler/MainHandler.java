package handler;

import chess.ChessGame;
import com.google.gson.Gson;



public class MainHandler () {

    var serializer = new Gson();

    var game = new ChessGame();

// serialize to JSON
    var json = serializer.toJson(game);

// deserialize back to ChessGame
    game = serializer.fromJson(json, ChessGame.class);





}
