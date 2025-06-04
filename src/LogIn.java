import java.io.IOException;
import java.util.Scanner;

public class LogIn {

    static String logUsername;
    static String logPassword;


    static boolean LogIn() throws IOException {
        String logInChoice;
        Scanner sc = new Scanner(System.in);
        System.out.println("  _                 _____       _ \n" +
                " | |               |_   _|     | |\n" +
                " | |     ___   __ _  | |  _ __ | |\n" +
                " | |    / _ \\ / _` | | | | '_ \\| |\n" +
                " | |___| (_) | (_| |_| |_| | | |_|\n" +
                " |______\\___/ \\__, |_____|_| |_(_)\n" +
                "               __/ |              \n" +
                "              |___/               ");
        System.out.println("Do you have an account?");
        System.out.println("y/n/exit:");
        logInChoice = sc.nextLine();
        switch (logInChoice) {
            case "y":
                return LogInCall();

            case "n":
                Register.Register();
                return false;

            case "exit":
                System.exit(0);
                return false;

            default:
                System.out.println("No such command!");
                LogIn();
                return false;
        }
    }
    static boolean LogInCall() throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Username:");
        logUsername = sc.nextLine();
        System.out.println("+------------------------------+");
        System.out.printf("| %-28s |\n", logUsername);
        System.out.println("+------------------------------+");
        System.out.println("Password:");
        logPassword = sc.nextLine();
        int passLength = logPassword.length();
        System.out.println("+------------------------------+");
        System.out.printf("| %-28s |\n", new String(new char[passLength]).replace('\0', '*'));
        System.out.println("+------------------------------+");
        if(JDBSconnection.validateLogin(logUsername, logPassword)){
            System.out.println("Successful Login!");
            return true;
        }else {
            System.out.println("Wrong username or password!");
            //Call the main method
            String[] args = {};
            Main.main(args);
            return false;
        }
    }
}