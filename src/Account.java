import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.Set;

public class Account {
    static File account = new File(Main.acc_folder + LogIn.logUsername + ".txt");
    static String account_tasks = Main.tasks_dir + LogIn.logUsername + File.separator;
    static Set<String> files = Main.listFilesUsingJavaIO(Main.acc_folder);
    static String name;

    static void account() throws IOException {
        Scanner sc = new Scanner(System.in);
        String accChoice;
        RemMe.readAccount();
        System.out.println("|LOGOUT|");
        System.out.println("|EXIT|");
        accChoice = sc.nextLine();
        if (accChoice.equalsIgnoreCase("logout")) {
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
                if (RemMe.token.exists()) {
                    Files.delete(RemMe.token.toPath());
                    File accountToDelete = new File(String.valueOf(account));
                    deleteAccount(accountToDelete);
                }
                String[] args = {};
                Main.main(args);
                break;
            case "n":
                while (true) {
                    System.out.println("\nAccount:" + LogIn.logUsername);
                    Main.mainMenu();
                }
            default:
                System.out.println("No such command!");

        }
    }

    static void signedAcc() {
        try {
            new File(Main.acc_folder).mkdirs();
            // Make sure dir exists
            if (account.createNewFile()) {
                new File(account_tasks).mkdirs();
                FileWriter remMeWriter = new FileWriter(account);
                remMeWriter.write(LogIn.logUsername);
                remMeWriter.close();

            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void checkAccount() {
        files = Main.listFilesUsingJavaIO(Main.acc_folder);
        for (String file : files) {
            name = file.replace(".txt", "");
        }
        File accountFile = new File(Main.acc_folder + name + ".txt");
        if (accountFile.exists()){
            files = Main.listFilesUsingJavaIO(Main.tasks_dir + name + File.separator);
            for (String file : files) {
                System.out.println(file.replace(".txt", ""));
            }
        }
    }

    public static void deleteAccount(File folder) {
        File directory = new File(Main.acc_folder);
        File[] files = directory.listFiles();
        for (File f : files) {
            if (f.getName().endsWith(".txt")) {
                f.delete();
            }
        }
        folder.delete();
    }
}
