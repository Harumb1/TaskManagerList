import java.io.*;
import java.nio.file.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static String choice;
    static String newTask;
    static String exitChoice;
    static String readChoice;
    static String deleteChoice;


    static final String base_dir = System.getProperty("user.dir");
    //gets the current working directory of the program
    static final String tasks_dir = base_dir + File.separator + "TaskFolder" + File.separator ;
    //adds a file separator (like / on macOS/Linux or \ on Windows) to the end of base_dir (current working directory of the program)
    static final String edit_folder = tasks_dir + "EditFolder" + File.separator;
    //gets the directory of EditFolder and adds a file separator
    //gets the directory of ListOfTasks.txt where the tasks' names are stored and later displayed
    static Set<String> files = listFilesUsingJavaIO(tasks_dir);

    public static void main(String[] args) throws IOException {
        RemMe.checkToken();
    }

    static void setChoice() throws IOException {
        Scanner sc = new Scanner(System.in);
        switch (choice) {
            case "1": // New Task
                setNewTask();
                break;

            case "2": // Open/Edit Task
                ListOfTasks();
                while (true) {
                    System.out.println("\nType:");
                    System.out.println(" |OPEN| to read an existing task");
                    System.out.println(" |EXIT| to return to the main menu");
                    System.out.println("\n*Note* To delete or modify an existing task you have to first open it.");

                    exitChoice = sc.nextLine();

                    if (exitChoice.equalsIgnoreCase("exit")) {
                        mainMenu();
                        break;
                    }

                    if (exitChoice.equalsIgnoreCase("open")) {
                        System.out.println("Which file would you like to OPEN?");
                        readTask();
                        String input = null;

                        try {
                            if (exitChoice.equalsIgnoreCase("edit")) {
                                System.out.println("Replacement created!");
                                System.out.println("============================================================================");

                                // Make sure EditFolder exists
                                new File(edit_folder).mkdirs();

                                String originalFile = tasks_dir + readChoice + ".txt";
                                //gets the directory of the task (text file) chosen with readChoice
                                String tempFile = edit_folder + readChoice + ".txt";
                                //gets the directory of the temporary created task (text file) in EditFolder which will later replace the original one but with the saved changes
                                File task = new File(tempFile);


                                if (task.createNewFile()) {
                                    while (!"exit".equalsIgnoreCase(input = sc.nextLine())) {
                                        FileWriter writer = new FileWriter(tempFile, true);
                                        writer.append(input).append("\n");
                                        writer.flush();
                                        writer.close();
                                        System.out.println("Changes saved!");
                                        //ADD A FEATURE WHICH ASKS THE USER IF THE
                                    }
                                    Files.deleteIfExists(Path.of(originalFile));
                                    Files.move(Path.of(tempFile), Path.of(originalFile), StandardCopyOption.REPLACE_EXISTING);
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }

                        if (exitChoice.equalsIgnoreCase("delete")) {

                            Scanner fullName = new Scanner(System.in);
                            String delFile;
                            System.out.println("Are you sure you would like to delete this file?");
                            System.out.println("y/n:");
                            deleteChoice = sc.nextLine();

                            if (deleteChoice.equalsIgnoreCase("y") || deleteChoice.equalsIgnoreCase("yes")) {
                                System.out.println("Full name of the file: ");
                                delFile = fullName.nextLine();
                                Files.deleteIfExists(Path.of(tasks_dir + delFile + ".txt"));

                            } else if (deleteChoice.equalsIgnoreCase("n") || deleteChoice.equalsIgnoreCase("no")) {
                                ListOfTasks();
                            }

                        }

                    } else {
                        System.out.println("There is no such command!");
                    }

                    break;
                }
                break;

            case "3": // Exit
                accInfo();
                break;

            case "4":
            System.out.print("See you soon :) ");
            System.exit(0);
            break;

            default:
                System.out.println("There is no such command!");
                choice = sc.nextLine();
                setChoice(); // recursion
                break;
        }

    }

    //New Task
    static void setNewTask(){
        Scanner sc = new Scanner(System.in);
        System.out.println(" _   _                 _____         _    _ \n" +
                "| \\ | |               |_   _|       | |  | |\n" +
                "|  \\| | _____      __   | | __ _ ___| | _| |\n" +
                "| . ` |/ _ \\ \\ /\\ / /   | |/ _` / __| |/ / |\n" +
                "| |\\  |  __/\\ V  V /    | | (_| \\__ \\   <|_|\n" +
                "\\_| \\_/\\___| \\_/\\_/     \\_/\\__,_|___/_|\\_(_)");
        System.out.print("Your New Task's Name:\n");
        newTask = sc.nextLine();

        try {
            String input;
            File task = new File(tasks_dir + newTask + ".txt");
            if (task.createNewFile()) {
                input = null;
                System.out.println("New Task Created!\n" + "\"" + newTask + "\"");
                System.out.println("============================================================================");
                while (!"exit".equalsIgnoreCase(input = sc.nextLine())) {
                    FileWriter writer = new FileWriter(task, true);
                    writer.append(input).append("\n");
                    writer.flush();
                    writer.close();
                }
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void mainMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n                      *Note*" +
                "\n        Type 1-3 in the empty line bellow.\n");
        System.out.println("  _______    _____              _     _      _ \n" +
                " |__   __|  |  __ \\            | |   | |    | |\n" +
                "    | | ___ | |  | | ___   __ _| |__ | | ___| |\n" +
                "    | |/ _ \\| |  | |/ _ \\ / _` | '_ \\| |/ _ \\ |\n" +
                "    | | (_) | |__| | (_) | (_| | |_) | |  __/_|\n" +
                "    |_|\\___/|_____/ \\___/ \\__,_|_.__/|_|\\___(_)");

        System.out.println("\n                  1.NEW TASK");
        System.out.println("                  2.SEE EXISTING TASKS");
        System.out.println("                  3.ACCOUNT");
        System.out.println("                  4.EXIT");

        choice = sc.nextLine();
        setChoice();
    }
    static void ListOfTasks(){
        System.out.println("  _      _     _    ____   __ _______        _        _ \n" +
                " | |    (_)   | |  / __ \\ / _|__   __|      | |      | |\n" +
                " | |     _ ___| |_| |  | | |_   | | __ _ ___| | _____| |\n" +
                " | |    | / __| __| |  | |  _|  | |/ _` / __| |/ / __| |\n" +
                " | |____| \\__ \\ |_| |__| | |    | | (_| \\__ \\   <\\__ \\_|\n" +
                " |______|_|___/\\__|\\____/|_|    |_|\\__,_|___/_|\\_\\___(_)");

        System.out.println("LIST OF TASKS:");
        files = listFilesUsingJavaIO(tasks_dir);
        for (String file : files) {
            System.out.println(file.replace(".txt", ""));
        }

    }
    //Account Information *
    public static void accInfo(){
        System.out.println("This method is empty lol");
    }

    //New way to show which tasks are in the list
    public static Set<String> listFilesUsingJavaIO(String tasks_dir) {

        return Stream.of(Objects.requireNonNull(new File(tasks_dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    static void readTask() {
        Scanner sc = new Scanner(System.in);
        readChoice = sc.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader(tasks_dir + readChoice + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not locate file.");
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
        exitChoice = sc.nextLine();
    }
    static void openTask(){
            Scanner sc = new Scanner(System.in);
            readTask();
            String input = null;

            try {
                if (exitChoice.equalsIgnoreCase("edit")) {
                    System.out.println("Replacement created!");
                    System.out.println("============================================================================");

                    // Make sure EditFolder exists
                    new File(edit_folder).mkdirs();

                    String originalFile = tasks_dir + readChoice + ".txt";
                    //gets the directory of the task (text file) chosen with readChoice
                    String tempFile = edit_folder + readChoice + ".txt";
                    //gets the directory of the temporary created task (text file) in EditFolder which will later replace the original one but with the saved changes
                    File task = new File(tempFile);


                    if (task.createNewFile()) {
                        while (!"exit".equalsIgnoreCase(input = sc.nextLine())) {
                            FileWriter writer = new FileWriter(tempFile, true);
                            writer.append(input).append("\n");
                            writer.flush();
                            writer.close();
                        }

                        Files.deleteIfExists(Path.of(originalFile));
                        Files.move(Path.of(tempFile), Path.of(originalFile), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
}
