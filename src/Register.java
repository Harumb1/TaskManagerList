import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Register {
    static String conPassword;

    static void Register() {

        String regUsername;
        String regPassword;

        Scanner sc = new Scanner(System.in);
        System.out.println("  _____            _     _            _ \n" +
                " |  __ \\          (_)   | |          | |\n" +
                " | |__) |___  __ _ _ ___| |_ ___ _ __| |\n" +
                " |  _  // _ \\/ _` | / __| __/ _ \\ '__| |\n" +
                " | | \\ \\  __/ (_| | \\__ \\ ||  __/ |  |_|\n" +
                " |_|  \\_\\___|\\__, |_|___/\\__\\___|_|  (_)\n" +
                "              __/ |                     \n" +
                "             |___/                      ");
        System.out.println("Username:");
        regUsername = sc.nextLine();
        System.out.println("+------------------------------+");
        System.out.printf("| %-28s |\n", regUsername);
        System.out.println("+------------------------------+");
        System.out.println("Password:");
        regPassword = sc.nextLine();
        int passLength = regPassword.length();
        String repeated = new String(new char[passLength]).replace('\0', '*');
        System.out.println("+------------------------------+");
        System.out.printf("| %-28s |\n", repeated);
        System.out.println("+------------------------------+");
        setConPassword();
        try {
            if (regPassword.equals(conPassword)) {
                JDBSconnection.register(regUsername, regPassword);
                System.out.println("Registration complete!");
                //Call the main method
                String[] args = {};
                Main.main(args);
            } else if(!Objects.equals(conPassword, regPassword)) {
                while (true) {
                    System.out.println("Passwords don't match!");
                    setConPassword();
                    //Check whether the confirmed password is now the same as the password we first entered
                    if (regPassword.equals(conPassword)) {
                        JDBSconnection.register(regUsername, regPassword);
                        System.out.println("Registration complete!");
                        //Call the main method

                        String[] args = {};
                        Main.main(args);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void setConPassword() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Confirm password:");
        conPassword = sc.nextLine();
        int conPassLength = conPassword.length();
        System.out.println("+------------------------------+");
        System.out.printf("| %-28s |\n", new String(new char[conPassLength]).replace('\0', '*'));
        System.out.println("+------------------------------+");
    }
}