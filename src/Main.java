import java.io.*;
import java.util.Scanner;

public class Main {
    static String choice;
    static String newTask;
    static String exitChoice;
    static String editChoice;

    public static void main(String[] args) throws IOException {
        mainMenu();
        setChoice();
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

    static void listOfTasks(){
        System.out.println("  _      _     _    ____   __ _______        _        _ \n" +
                " | |    (_)   | |  / __ \\ / _|__   __|      | |      | |\n" +
                " | |     _ ___| |_| |  | | |_   | | __ _ ___| | _____| |\n" +
                " | |    | / __| __| |  | |  _|  | |/ _` / __| |/ / __| |\n" +
                " | |____| \\__ \\ |_| |__| | |    | | (_| \\__ \\   <\\__ \\_|\n" +
                " |______|_|___/\\__|\\____/|_|    |_|\\__,_|___/_|\\_\\___(_)");
        System.out.println("LIST OF TASKS:");
        try (BufferedReader reader = new BufferedReader(new FileReader("ListOfTasks.txt"))) {
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

    static void readTask(){

    }

    static void setChoice() throws IOException {
        Scanner sc = new Scanner(System.in);
        switch (choice) {
            //Case for Main Menu
            case "1":
                System.out.println(" _   _                 _____         _    _ \n" +
                        "| \\ | |               |_   _|       | |  | |\n" +
                        "|  \\| | _____      __   | | __ _ ___| | _| |\n" +
                        "| . ` |/ _ \\ \\ /\\ / /   | |/ _` / __| |/ / |\n" +
                        "| |\\  |  __/\\ V  V /    | | (_| \\__ \\   <|_|\n" +
                        "\\_| \\_/\\___| \\_/\\_/     \\_/\\__,_|___/_|\\_(_)");
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
                    }
                    else {
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

            //Case for showing the previously created tasks and modifying them
            case "2":
                listOfTasks();

                while (true) {
                    System.out.println("\nType:");
                    System.out.println(" |OPEN| to read an existing task");
                    System.out.println(" |EDIT| to modify an existing task");
                    System.out.println(" |EXIT| to return to the main menu");
                    exitChoice = sc.nextLine();
                    //exit
                    if (exitChoice.equalsIgnoreCase("exit")) {
                        mainMenu();
                        setChoice();
                        break;
                    }
                     if (exitChoice.equalsIgnoreCase("edit")) {
                        System.out.println("Which file would you like to edit?");

                        break;
                    }
                     if (exitChoice.equalsIgnoreCase("open")) {
                        System.out.println("Which file would you like to OPEN?");

                        break;
                    }
                    else {
                        System.out.println("There is no such command!");
                    }

                }
                break;

            //Case "Exit"
            case "3":
                System.out.print("See you soon :)");
                break;
            default:
                System.out.println("There is no such command!");
                choice = sc.nextLine();
                setChoice();
                //recursion
                break;
        }
    }
}
