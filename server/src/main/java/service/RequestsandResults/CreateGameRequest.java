package service.RequestsandResults;

public record CreateGameRequest(String authToken, String gameName) {

    public CreateGameRequest new_authToken (String authToken) {
        return new CreateGameRequest(authToken, this.gameName);
    }
}
