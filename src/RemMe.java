import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class RemMe {
    static String currentLine;
    static final String tokenDir = Main.base_dir + File.separator + "RememberMe" + File.separator;
    static File token = new File(tokenDir + "remember_me.txt");
    static Set<String> files = Main.listFilesUsingJavaIO(Main.acc_folder);

    static boolean remMe() throws IOException {
        String remChoice;
        Scanner sc = new Scanner(System.in);
        System.out.println("Remember me?");
        System.out.println("y/n:");
        remChoice = sc.nextLine();
        switch (remChoice) {
            case "y":
                return createToken();
            case "n":
                RemMe.readAccount();
                Main.mainMenu();
                return false;
            default:
                System.out.println("No such command!");
                return false;
        }
    }
    static boolean createToken() {
        try {
            new File(tokenDir).mkdirs(); // Make sure dir exists
            if (token.createNewFile()) {
                FileWriter remMeWriter = new FileWriter(token);
                remMeWriter.write(LogIn.logUsername);
                remMeWriter.close();
                System.out.println("\nAccount:" + LogIn.logUsername);
                return true;
            } else {
                System.out.println("Token already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    static void checkToken() throws IOException {
        if (Files.exists(Path.of(token.toURI()))) {
            System.out.println("Welcome back!" );
            while (true) {
                Main.mainMenu();
            }
        } else {
            while (true) {
                boolean isLoginScc = LogIn.LogIn();
                if (isLoginScc) {
                    RemMe.remMe();
                    Main.mainMenu();
                } else {
                    System.out.println("Login failed :(");
                }
            }
        }
    }
    static void readAccount(){
        files = Main.listFilesUsingJavaIO(Main.acc_folder);
        for (String file : files) {
            System.out.println( "Account: " + file.replace(".txt", ""));
        }
    }
}