package server;

import com.google.gson.Gson;
import dataaccess.*;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import service.requestsandresults.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import service.MainService;
import java.util.Map;


public class Server {

    private final MainService mainService;
    private final Javalin javalin;

    public Server() {


        UserDAO user = new MySQLUserDataAccess();
        GameDAO game = new MySQLGameDataAccess();
        AuthDAO auth = new MySLQAuthDataAccess();


        this.mainService = new MainService(user, game, auth);


        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .delete("/db", this::clear)
                .post("/user", this::register)
                .post("/session", this::login)
                .exception(Exception.class, this::exceptionHandler)
                .delete("/session", this::logout)
                .post("/game", this::createGame)
                .put("/game", this::joinGame)
                .get("/game", this::listGames);
    }


    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }



    private void clear(Context ctx) throws DataAccessException{
        mainService.clear();
    }

    private void createGame(Context ctx) {
        try {
            CreateGameRequest createGameRequest = new Gson().fromJson(ctx.body(), CreateGameRequest.class);
            createGameRequest = createGameRequest.newAuthToken(ctx.header("Authorization"));
            CreateGameResult createGameResult = mainService.createGame(createGameRequest);
            ctx.json(new Gson().toJson(createGameResult));
        } catch (Exception e) {
            exceptionHandler(e, ctx);
        }
    }

    private void joinGame(Context ctx)  {
        try {
            JoinGameRequest joinGameRequest = new Gson().fromJson(ctx.body(), JoinGameRequest.class);
            joinGameRequest = joinGameRequest.newAuthToken(ctx.header("Authorization"));
            JoinGameResult joinGameResult = mainService.joinGame(joinGameRequest);
            ctx.json(new Gson().toJson(joinGameResult));
        } catch (Exception e) {
            exceptionHandler(e, ctx);
        }
    }


    private void register(Context ctx) {

        try {
                RegisterRequest registerRequest = new Gson().fromJson(ctx.body(), RegisterRequest.class);
                RegisterResult registerResult = mainService.register(registerRequest);
                ctx.json(new Gson().toJson(registerResult));
            } catch (Exception e) {
                exceptionHandler(e, ctx);
            }
    }

    private void login(Context ctx) {
        try {
            LoginRequest loginRequest = new Gson().fromJson(ctx.body(), LoginRequest.class);
            LoginResult loginResult = mainService.login(loginRequest);
            ctx.json(new Gson().toJson(loginResult));
        } catch (Exception e) {
            exceptionHandler(e, ctx);
        }
    }

    private void logout(Context ctx) {
        try {
            LogoutRequest logoutRequest = new LogoutRequest(ctx.header("Authorization"));
            LogoutResult logoutResult = mainService.logout(logoutRequest);
            ctx.json(new Gson().toJson(logoutResult));
        } catch (Exception e) {
            exceptionHandler(e, ctx);
        }
    }

    private void listGames(Context ctx)  {
        try {
            ListGamesRequest listGamesRequest = new ListGamesRequest(ctx.header("Authorization"));
            ListGamesResult listGamesResult = mainService.listGames(listGamesRequest);
            ctx.json(new Gson().toJson(listGamesResult));
        } catch (Exception e) {
            exceptionHandler(e, ctx);
        }
    }

    private void exceptionHandler(Exception ex, Context ctx) {
        if (ex instanceof UnauthorizedException) {
            ctx.status(401);
        }
        if (ex instanceof BadRequestException) {
            ctx.status(400);
        }
        if (ex instanceof AlreadyTakenException) {
            ctx.status(403);
        }
        if (ex instanceof DataAccessException) {
            ctx.status(500);
        }

        ctx.json(new Gson().toJson(Map.of("message", ex.getMessage())));
        //        ctx.json(new Gson().toJson(ex.getMessage()));

    }

    public void stop() {
        javalin.stop();
    }
}
