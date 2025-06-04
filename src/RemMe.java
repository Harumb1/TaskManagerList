import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class RemMe {
    static File token;
    static final String tokenDir = Main.tasks_dir + File.separator + "RememberMe" + File.separator;

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
            token = new File(tokenDir + "remember_me_" + LogIn.logUsername + ".txt");
            if (token.createNewFile()) {
                FileWriter remMeWriter = new FileWriter(token);
                remMeWriter.write(LogIn.logUsername);
                remMeWriter.close();
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
        File rememberedFile = new File(tokenDir + "remember_me_" + LogIn.logUsername + ".txt");

        if (Files.exists(Path.of(tokenDir))) {
            System.out.println("Welcome back, " + LogIn.logUsername + "! (Auto-logged in)");
            while (true) {
                Main.mainMenu();
            }
        } else {
            boolean loginSuccess = LogIn.LogIn();
            if (loginSuccess) {
                RemMe.remMe();
                while (true) {
                    Main.mainMenu();
                }
            } else {
                System.out.println("Login failed.");
            }
        }
    }
}

