import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RemMe {
    static final String tokenDir = Main.base_dir + File.separator + "RememberMe" + File.separator;
    static File token = new File(tokenDir + "remember_me.txt");
    static String LINE;

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
        Path folderPath = Paths.get(Main.acc_folder);
        // prepare a data structure for a file's name and content
        Map<String, List<String>> linesOfFiles = new TreeMap<>();

        // retrieve a list of the files in the folder
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {
            System.err.println("Error reading files");
            ex.printStackTrace();
        }

        // go through the list of files
        for (String file : fileNames) {
            try {
                // put the file's name and its content into the data structure
                List<String> lines = Files.readAllLines(folderPath.resolve(file));
                linesOfFiles.put(file, lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // finally, print everything
        linesOfFiles.forEach((String _, List<String> lines) -> {
            lines.forEach((line) -> {
                System.out.println("\nAccount:" + line);
            });
        });
    }
}