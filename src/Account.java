import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Account {
    static File account = new File(Main.acc_folder + LogIn.logUsername + ".txt");

    static void account() throws IOException {
        Scanner sc = new Scanner(System.in);
        String accChoice;
        RemMe.readAccount();
        System.out.println("|LOGOUT|");
        System.out.println("|EXIT|");
        accChoice = sc.nextLine();
        if(accChoice.equalsIgnoreCase("logout")){
            logout();
        }
    }

    static void logout() throws IOException {
        Scanner sc = new Scanner(System.in);
        String logOutChoice;
        System.out.println("Would you like to logout from you account?");
        System.out.println("y/n");
        logOutChoice = sc.nextLine();
        switch (logOutChoice) {
            case "y":
                if(RemMe.token.exists()){
                    Files.delete(RemMe.token.toPath());
                }
                if(account.exists()){
                    Files.delete(Path.of(account.getAbsolutePath()));

                }
                String[] args = {};
                Main.main(args);
                break;
            case "n":
                while(true) {
                    System.out.println("\nAccount:" + LogIn.logUsername);
                    Main.mainMenu();
                }
            default:
                System.out.println("No such command!");

        }
    }
    static void signedAcc(){
            try {
                new File(Main.acc_folder).mkdirs(); // Make sure dir exists
                if (account.createNewFile()) {
                    FileWriter remMeWriter = new FileWriter(account);
                    remMeWriter.write(LogIn.logUsername);
                    remMeWriter.close();
//                    System.out.println("\nAccount:" + LogIn.logUsername);
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
    }
}
