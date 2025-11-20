package service.requestsandresults;

public class ErrorResponse {

    String message;

    public ErrorResponse (String message) {
        this.message = message;
    }

    public String getMessage () {
        return this.message;
    }
}
