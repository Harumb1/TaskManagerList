import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {
    static String choice;
    static String newTask;
    static String exitChoice;
    static String readChoice;


    static final String base_dir = System.getProperty("user.dir");
    static final String tasks_dir = base_dir + File.separator;
    static final String edit_folder = tasks_dir + "EditFolder" + File.separator;
    static final String task_list_file = tasks_dir + "ListOfTasks.txt";

    public static void main(String[] args) throws IOException {
        mainMenu();
        setChoice();
    }

    static void setChoice() throws IOException {
        Scanner sc = new Scanner(System.in);
        switch (choice) {
            case "1": // New Task
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

                try (FileWriter taskList = new FileWriter(task_list_file, true)) {
                    taskList.append(newTask).append("\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                mainMenu();
                break;

            case "2": // Open/Edit Task
                listOfTasks();

                while (true) {
                    System.out.println("\nType:");
                    System.out.println(" |OPEN| to read an existing task");
                    System.out.println(" |EDIT| to modify an existing task");
                    System.out.println(" |EXIT| to return to the main menu");
                    exitChoice = sc.nextLine();

                    if (exitChoice.equalsIgnoreCase("exit")) {
                        mainMenu();
                        setChoice();
                        break;
                    }

                    if (exitChoice.equalsIgnoreCase("edit")) {
                        System.out.println("Which file would you like to EDIT?");
                        exitChoice = sc.nextLine();
                        break;
                    }

                    if (exitChoice.equalsIgnoreCase("open")) {
                        System.out.println("Which file would you like to OPEN?");
                        readTask();
                        exitChoice = sc.nextLine();
                        String input = null;

                        try {
                            if (exitChoice.equalsIgnoreCase("edit")) {
                                System.out.println("Replacement created!");
                                System.out.println("============================================================================");

                                // Ensure EditFolder exists
                                new File(edit_folder).mkdirs();

                                String originalFile = tasks_dir + readChoice + ".txt";
                                String tempFile = edit_folder + readChoice + ".txt";
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
                    } else {
                        System.out.println("There is no such command!");
                    }

                    break;
                }
                break;

            case "3": // Exit
                System.out.print("See you soon :)");
                break;

            default:
                System.out.println("There is no such command!");
                choice = sc.nextLine();
                setChoice(); // recursion
                break;
        }
    }

    static void mainMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("  _______    _____              _     _      _ \n" +
                " |__   __|  |  __ \\            | |   | |    | |\n" +
                "    | | ___ | |  | | ___   __ _| |__ | | ___| |\n" +
                "    | |/ _ \\| |  | |/ _ \\ / _` | '_ \\| |/ _ \\ |\n" +
                "    | | (_) | |__| | (_) | (_| | |_) | |  __/_|\n" +
                "    |_|\\___/|_____/ \\___/ \\__,_|_.__/|_|\\___(_)");

        System.out.println("\n                  1.NEW TASK");
        System.out.println("                  2.OPEN AN EXISTING TASK");
        System.out.println("                  3.EXIT");
        choice = sc.nextLine();
        setChoice();
    }

    static void listOfTasks() {
        System.out.println("  _      _     _    ____   __ _______        _        _ \n" +
                " | |    (_)   | |  / __ \\ / _|__   __|      | |      | |\n" +
                " | |     _ ___| |_| |  | | |_   | | __ _ ___| | _____| |\n" +
                " | |    | / __| __| |  | |  _|  | |/ _` / __| |/ / __| |\n" +
                " | |____| \\__ \\ |_| |__| | |    | | (_| \\__ \\   <\\__ \\_|\n" +
                " |______|_|___/\\__|\\____/|_|    |_|\\__,_|___/_|\\_\\___(_)");

        System.out.println("LIST OF TASKS:");
        try (BufferedReader reader = new BufferedReader(new FileReader(task_list_file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not locate file.");
        } catch (IOException e) {
            System.out.println("Something went wrong.");
        }
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
    }
}
