package service.requestsandresults;

public record CreateGameRequest(String authToken, String gameName) {

    public CreateGameRequest newAuthToken(String authToken) {
        return new CreateGameRequest(authToken, this.gameName);
    }
}
