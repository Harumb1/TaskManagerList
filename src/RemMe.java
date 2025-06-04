import com.mysql.cj.log.Log;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RemMe {
    static final String tokenDir = Main.tasks_dir + File.separator + "RememberMe" + File.separator;
    static File token = new File(tokenDir + "remember_me.txt");

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
//        File rememberedFile = new File(tokenDir + "remember_me_" + LogIn.logUsername + ".txt");
        if (Files.exists(Path.of(token.toURI()))) {
            readToken();
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
    static void readToken(){
        Path folderPath = Paths.get(tokenDir);
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
        linesOfFiles.forEach((String fileName, List<String> lines) -> {
            lines.forEach((line) -> {
                System.out.println("Welcome back, " + line + "! (Auto-logged in)");
                System.out.println("\nAccount:" + line);
            });
        });
    }
}

