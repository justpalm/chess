package server;

import com.google.gson.Gson;
import dataaccess.exceptions.AlreadyTakenException;
import dataaccess.exceptions.BadRequestException;
import dataaccess.exceptions.DataAccessException;
import dataaccess.exceptions.UnauthorizedException;
import io.javalin.*;
import service.RequestsandResults.*;
import model.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import service.MainService;

import java.util.HashSet;
import java.util.Map;


public class Server {
    final private HashSet<String> validTokens = new HashSet<String>();

    private final MainService mainService;

    private final Javalin javalin;

    public Server() {

        this.mainService = new MainService();


        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .delete("/db", this::clear)
                .post("/user", this::register)
                .post("/session", this::login)
                .exception(Exception.class, this::exceptionHandler)
                .delete("/session", this::logout)
                .post("/game", this::createGame)
                .put("/game", this::joinGame)
                .get("/game", this::listGames);

        // Register your endpoints and exception handlers here.
//
//    }
//
//    private boolean authorized(Context ctx) {
//        String authToken = ctx.header("authorization");
//        if (!validTokens.contains(authToken)) {
//            ctx.contentType("application/json");
//            ctx.status(401);
//            ctx.result(new Gson().toJson(Map.of("msg", "invalid authorization")));
//            return false;
//        }
//        return true;
    }


    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }



    private void clear(Context ctx) {
        mainService.clear();
    }

    private void createGame(Context ctx) throws UnauthorizedException, BadRequestException {
        try {
            CreateGameRequest createGameRequest = new Gson().fromJson(ctx.body(), CreateGameRequest.class);
            createGameRequest = createGameRequest.new_authToken(ctx.header("Authorization"));
            CreateGameResult createGameResult = mainService.createGame(createGameRequest);
            ctx.json(new Gson().toJson(createGameResult));
        } catch (Exception e) {
            exceptionHandler(e, ctx);
        }
    }

    private void joinGame(Context ctx) throws UnauthorizedException, BadRequestException {
        try {
            JoinGameRequest joinGameRequest = new Gson().fromJson(ctx.body(), JoinGameRequest.class);
            joinGameRequest = joinGameRequest.new_authToken(ctx.header("Authorization"));
            JoinGameResult joinGameResult = mainService.joinGame(joinGameRequest);
            ctx.json(new Gson().toJson(joinGameResult));
        } catch (Exception e) {
            exceptionHandler(e, ctx);
        }
    }


    private void register(Context ctx) throws AlreadyTakenException, BadRequestException, UnauthorizedException {

        try {
                RegisterRequest registerRequest = new Gson().fromJson(ctx.body(), RegisterRequest.class);
                RegisterResult registerResult = mainService.register(registerRequest);
                ctx.json(new Gson().toJson(registerResult));
            } catch (Exception e) {
                exceptionHandler(e, ctx);
            }
    }

    private void login(Context ctx) throws UnauthorizedException, BadRequestException, AlreadyTakenException, DataAccessException {
        try {
            LoginRequest loginRequest = new Gson().fromJson(ctx.body(), LoginRequest.class);
            LoginResult loginResult = mainService.login(loginRequest);
            ctx.json(new Gson().toJson(loginResult));
        } catch (Exception e) {
            exceptionHandler(e, ctx);
        }
    }

    private void logout(Context ctx) throws UnauthorizedException, BadRequestException {
        try {
            LogoutRequest logoutRequest = new LogoutRequest(ctx.header("Authorization"));
            LogoutResult logoutResult = mainService.logout(logoutRequest);
            ctx.json(new Gson().toJson(logoutResult));
        } catch (Exception e) {
            exceptionHandler(e, ctx);
        }
    }

    private void listGames(Context ctx) throws UnauthorizedException, BadRequestException {
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

        ctx.json(new Gson().toJson(Map.of("message", ex.getMessage(),"stats", ctx.status())));


    }


    public void stop() {
        javalin.stop();
    }
}
