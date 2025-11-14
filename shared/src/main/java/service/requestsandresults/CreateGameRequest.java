package service.requestsandresults;

public record CreateGameRequest(String authToken, String gameName) {

    public CreateGameRequest newAuthToken(String authToken) {

        if (this.authToken != null) {
            return new CreateGameRequest(this.authToken, this.gameName);
        } else {
            return new CreateGameRequest(authToken, this.gameName);
        }
    }
}
