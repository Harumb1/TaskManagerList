import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static String choice;
    static String newTask;
    public static void main(String[] args) {
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
    static void setChoice() {
        Scanner sc = new Scanner(System.in);
        switch (choice) {
            case "1":
                System.out.print("How would you like to name your new task?\n");
                newTask = sc.nextLine();
                try {
                    File myObj = new File(newTask + ".txt");
                    if (myObj.createNewFile()) {
                        System.out.println("New Task Created:\n" + myObj.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                    break;
                    case "2":
                    
                        break;
                    case "3":
                        System.out.print("See you soon :)!");
                        break;
                }
        }
    }