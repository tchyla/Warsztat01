package pl.codersLabs;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainTest {
    static String[] menu = {"ADD", "REMOVE", "LIST", "EXIT"};
    static String[][] dataFromTasksCSV;
    static Scanner scan = new Scanner(System.in);
    static Path tasksCSV = Paths.get("tasks.csv");

    public static void main(String[] args) {
        uploadTasksCSV(dataFromTasksCSV);
        menuOptions(menu);
        scanner(scan);
    }

    public static String[][] uploadTasksCSV(String[][] tasks) {
        if (!Files.exists(tasksCSV)) {
            System.out.println("Missing data file tasks.csv");
            System.exit(0);
        }
        try {
            List<String> str = Files.readAllLines(tasksCSV);
            dataFromTasksCSV = new String[str.size()][str.get(0).split(",").length];

            for (int i = 0; i < str.size(); i++) {
                String[] split = str.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    dataFromTasksCSV[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataFromTasksCSV;
    }
    public static void showData(String[][] showData) {
        for (int i = 0; i < showData.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < showData[i].length; j++) {
                System.out.print(showData[i][j] + " ");
            }
            System.out.println();
    }
}

    public static void menuOptions(String[] showData) {
        String head = "Please select an option: ";
        System.out.println(ConsoleColors.BLUE);
        System.out.println(head + ConsoleColors.RESET);
        for (String menu : showData) {
            System.out.println(menu);
        }
    }

    public static void scanner(Scanner options) {
        while (scan.hasNextLine()) {
            String strStr = scan.nextLine();
            switch (strStr) {
                case "add" -> {
                    add();
                    savingToTasksCSV();
                    menuOptions(menu);
                    break;
                }
                case "remove" -> {
                    removeTask(dataFromTasksCSV, NumberOfTaskToRemove());
                    savingToTasksCSV();
                    menuOptions(menu);
                    break;

                }
                case "list" -> {
                    showData(dataFromTasksCSV);
                    menuOptions(menu);
                    break;

                }
                case "exit" -> {
                    savingToTasksCSV();
                    System.out.println(ConsoleColors.RED + "Bye, bye...");
                    System.exit(0);
                }
                default -> System.out.println("Select a correct option or die trying.");
            }
            uploadTasksCSV(dataFromTasksCSV);
        }
    }

    public static void add() {
        Scanner addScan = new Scanner(System.in);
        System.out.println("Please, add text description");
        String addDescription = addScan.nextLine();
        System.out.println("Please, add task due date (format [YYYY]-[MM]-[DD]");
        String addDate = addScan.nextLine();
        System.out.println("Is your task importanrt? write: true / false");
        String important = addScan.nextLine();

        dataFromTasksCSV = Arrays.copyOf(dataFromTasksCSV, dataFromTasksCSV.length +1);
        dataFromTasksCSV[dataFromTasksCSV.length-1] = new String[3];
        dataFromTasksCSV[dataFromTasksCSV.length-1][0] = addDescription;
        dataFromTasksCSV[dataFromTasksCSV.length-1][1] = addDate;
        dataFromTasksCSV[dataFromTasksCSV.length-1][2] = important;
    }

    public static void savingToTasksCSV() {
        String[] forSaving = new String[dataFromTasksCSV.length];
        for (int i = 0; i < dataFromTasksCSV.length; i++) {
            forSaving[i] = String.join(",", dataFromTasksCSV[i]);
        }
        try {
            Files.write(tasksCSV, Arrays.asList(forSaving));
            System.out.println("Saving changes...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean NumberGraeterEqualZero(String gez) {
        if (NumberUtils.isParsable(gez)) {
            return Integer.parseInt(gez) >=0;
    }
        return false;
    }

    public static int NumberOfTaskToRemove() {
        System.out.println("Please select number of task to remove.");
        Scanner scanToRemove = new Scanner(System.in);
        String number = scanToRemove.nextLine();
        while (!NumberGraeterEqualZero(number)) {
            System.out.println("Incorrect number of task. Please choose number of task properly (number greater or equal zero)");
            scanToRemove.nextLine();
        }
        return Integer.parseInt(number);
        }

    public static void removeTask(String[][] showData, int removingPosition) {
        try {
            if (removingPosition < showData.length)
                dataFromTasksCSV = ArrayUtils.remove(showData, removingPosition);
            System.out.println("Task was successfully deleted.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Task not exist.");
        }
    }
}
