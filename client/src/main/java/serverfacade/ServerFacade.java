package serverfacade;

import service.requestsandresults.*;

import com.google.gson.Gson;
import dataaccess.exceptions.*;

import javax.xml.crypto.Data;
import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;


public class ServerFacade {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    public RegisterResult register(RegisterRequest registerRequest) throws UnauthorizedException, BadRequestException, DataAccessException {
        var request = buildRequest("POST", "/user", registerRequest);
        var response = sendRequest(request);
        return handleResponse(response, RegisterResult.class);
    }

    public LoginResult login(LoginRequest loginRequest)  throws UnauthorizedException, BadRequestException, DataAccessException {
        var request = buildRequest("POST", "/session", loginRequest);
        var response = sendRequest(request);
        return handleResponse(response, LoginResult.class);

}

    public LogoutResult logout(LogoutRequest logoutRequest) throws UnauthorizedException, BadRequestException, DataAccessException  {
        var request = buildRequestHeader("DELETE", "/session", logoutRequest, logoutRequest.authToken());
        var response = sendRequest(request);
        return handleResponse(response, LogoutResult.class);
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws BadRequestException, UnauthorizedException, DataAccessException  {
        var request = buildRequest("POST", "/game", createGameRequest);
        var response = sendRequest(request);
        return handleResponse(response, CreateGameResult.class);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest) throws BadRequestException,
            UnauthorizedException, AlreadyTakenException, DataAccessException  {

        var request = buildRequest("PUT", "/game", joinGameRequest);
        var response = sendRequest(request);
        return handleResponse(response, JoinGameResult.class);
    }


    public ListGamesResult listGames(ListGamesRequest listGamesRequest) throws BadRequestException, UnauthorizedException, DataAccessException {
        var request = buildRequestHeader("GET", "/game", listGamesRequest, listGamesRequest.authToken());
        var response = sendRequest(request);
        return handleResponse(response, ListGamesResult.class);
    }

    public void clear() throws DataAccessException{
        var request = buildRequest("DELETE", "/db", null);
        var response = sendRequest(request);
    }


    private HttpRequest buildRequestHeader(String method, String path, Object body, String authToken) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        request.setHeader("Authorization", authToken);

        return request.build();

    }


    private HttpRequest buildRequest(String method, String path, Object body) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path))
                .method(method, makeRequestBody(body));
        if (body != null) {
            request.setHeader("Content-Type", "application/json");
        }
        return request.build();
    }

    //I'm thinking we need to make a build request specifically for those that have a body and request
    //For example, Join Game, Create Game. For everything else the function above works



    private BodyPublisher makeRequestBody(Object request) {
        if (request != null) {
            return BodyPublishers.ofString(new Gson().toJson(request));
        } else {
            return BodyPublishers.noBody();
        }
    }


    //I'm not sure about the handling of this error, but this is about what I would expect since
    //they don't want us to be verbose about the error is or what is happening
    private HttpResponse<String> sendRequest(HttpRequest request) throws DataAccessException {
        try {
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws DataAccessException {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw new DataAccessException((String.format("Server error occurred: %s", body)));
            }
            //Keep in mind that this is different
            throw new DataAccessException("other failure: " + status);
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}



