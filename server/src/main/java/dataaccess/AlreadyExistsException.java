package dataaccess;

public class AlreadyExistsException extends Exception{
    public AlreadyExistsException(String message) {super(message); }
    public AlreadyExistsException(String message, Throwable ex) { super(message, ex); }
}
