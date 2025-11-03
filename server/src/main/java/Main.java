import server.Server;
import dataaccess.*;

public class Main {
    public static void main(String[] args) {


        //Here you can toggle which datatypes to ues
        var userDAO = new MySQLUserDataAccess();
        var gameDAO = new MySQLGameDataAccess();
        var authDAO = new MySLQAuthDataAccess();




        Server server = new Server(userDAO, gameDAO, authDAO);
        server.run(8080);

        System.out.println("♕ 240 Chess Server");
    }
}