import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static String choice;
    static String newTask;

    public static void main(String[] args) throws IOException {
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
        setChoice();
    }

    static void setChoice() throws IOException {
        Scanner sc = new Scanner(System.in);
        switch (choice) {
            case "1":
                System.out.print("How would you like to name your new task?\n");
                newTask = sc.nextLine();
                try {
                    File task = new File(newTask + ".txt");
                    //https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
                    //https://stackoverflow.com/questions/34836789/how-to-append-java-output-to-text-file
                    if (task.createNewFile()) {
                        String input = null;
                        System.out.println("New Task Created!\n" + "\"" + newTask + "\"");
                        System.out.println("=================================================" + "\n");
                        while (!"exit".equalsIgnoreCase(input = sc.nextLine())) {
                            FileWriter writer = new FileWriter(task, true);
//                            taskList.append(newTask);
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
                break;
            case "2":

                break;
            case "3":
                System.out.print("See you soon :)");
                break;
        }
    }
}