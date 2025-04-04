import java.io.*;
import java.util.Scanner;

public class Main {
    static String choice;
    static String newTask;
    static String exitChoice;

    public static void main(String[] args) throws IOException {
        mainMenu();
        setChoice();
    }
    static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("  _______    _____              _     _      _ \n" +
                " |__   __|  |  __ \\            | |   | |    | |\n" +
                "    | | ___ | |  | | ___   __ _| |__ | | ___| |\n" +
                "    | |/ _ \\| |  | |/ _ \\ / _` | '_ \\| |/ _ \\ |\n" +
                "    | | (_) | |__| | (_) | (_| | |_) | |  __/_|\n" +
                "    |_|\\___/|_____/ \\___/ \\__,_|_.__/|_|\\___(_)");

        System.out.println("\n                  1.New Task");
        System.out.println("                  2.Open an existing task");
        System.out.println("                  3.Exit");
        choice = sc.nextLine();
    }

    static void setChoice() throws IOException {
        Scanner sc = new Scanner(System.in);
        switch (choice) {

            case "1":
                System.out.print("Your New Task's Name:\n");
                newTask = sc.nextLine();
                try {
                    File task = new File(newTask + ".txt");
                    if (task.createNewFile()) {
                        String input = null;
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
                FileWriter taskList = new FileWriter("C:\\Users\\alexs\\IdeaProjects\\TaskManagerList\\ListOfTasks.txt", true);
                try {
                    taskList.append(newTask).append("\n");
                    taskList.flush();
                    taskList.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                mainMenu();
                break;

            case "2":
                System.out.println("List of tasks:");
                try (BufferedReader reader = new BufferedReader(new FileReader("ListOfTasks.txt"))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Could not locate file");
                } catch (IOException e) {
                    System.out.println("Something went wrong");
                }
                System.out.println("\n" + "Exit?");
                exitChoice = sc.nextLine();
                if (exitChoice.equals("exit")) {
                    mainMenu();
                    setChoice();
                } else {
                    while (!"exit".equalsIgnoreCase(exitChoice)) {
                            System.out.println("There is no such command!");
                            System.out.println("Exit?");
                        }

                    }
                break;
            case "3":
                System.out.print("See you soon :)");
                break;
        }
    }
}