package ui;

import facade.ServerFacade;
import model.UserData;

import java.io.IOException;
import java.util.Scanner;
import server.Server;

public class PreloginUI {
    static Server server;
    static int desPort = 8080;
    static ServerFacade facade = ServerFacade.getInstance("localhost", desPort);
    public static void main(String[] args) throws IOException {
        System.out.printf("Welcome to the best Chess server. Type help to get started. \n");
        while (true) {
            System.out.printf("\n[LOGGED_OUT]>>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] words = line.split(" ");
            if (words[0].equals("register") && words.length == 4){
                try {
                    facade.register(new UserData(words[1], words[2], words[3]));
                    facade.login(new UserData(words[1], words[2], null));
                    if (new PostloginUI().run(facade, words[1])){
                        break;
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage() + "try again");
                }
            }
            else if(words[0].equals("login") && words.length == 3){
                try {
                    facade.login(new UserData(words[1], words[2], null));
                    //run post login
                    if (new PostloginUI().run(facade, words[1])){
                        break;
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage() + "try again");
                }
            }
            else if(words[0].equals("quit")){
                System.out.println("BYE!");
                break;
            }
            else if (words[0].equals("help")){
                System.out.println("Type:'register <USERNAME> <PASSWORD> <EMAIL>' - to create an account \nType: 'login <USERNAME> <PASSWORD>' - to login for the best chess game\nType: 'quit' - to stop playing chess\nType: 'help' - to find out what you can do");
            }
            else{
                System.out.println("INVALID COMMAND: try typing 'help' for valid commands");
            }
        }
        server.stop();
    }
}
