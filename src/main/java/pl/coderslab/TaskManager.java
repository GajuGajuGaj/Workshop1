package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {

  public static void main(String[] args) throws IOException {
    tasks();
    showOptions();
  }
  public static void tasks() throws IOException {
    Path task = Paths.get("src/main/java/pl/coderslab/tasks.csv");
    Path taskTemp = Paths.get("src/main/java/pl/coderslab/tasksTemp.csv");
    Files.copy(task, taskTemp, StandardCopyOption.REPLACE_EXISTING);
  }
  public static void showOptions() throws IOException {
    System.out.println(ConsoleColors.BLUE + "Please select an option");
    System.out.println(ConsoleColors.RESET + "add");
    System.out.println("remove");
    System.out.println("list");
    System.out.println("exit");
    choice();
  }
  public static void choice() throws IOException {
    Scanner scan = new Scanner(System.in);
    if (scan.hasNext("add")) {
      add();
    } else if (scan.hasNext("remove")) {
      remove();
    } else if (scan.hasNext("list")) {
      list();
    } else if (scan.hasNext("exit")) {
      exit();
    } else {
      System.out.println("Command not found. \nPlease try again.");
      choice();
    }
  }
  public static void add() throws IOException {
    String[][] oldTable = updated();
    String[][] tableUpdated = new String[oldTable.length + 1][oldTable[0].length];
    Scanner scan = new Scanner(System.in);
    System.out.println("Please add description");
    if (!scan.hasNext("exit")) {
      tableUpdated[tableUpdated.length - 1][0] = scan.nextLine() + " ";
      System.out.println("Please add task due date");
      if (!scan.hasNext("exit")) {
        tableUpdated[tableUpdated.length - 1][1] = scan.nextLine() + " ";
        System.out.println("Is your task important? - true/false");
        if (!scan.hasNext("exit")) {
          tableUpdated[tableUpdated.length - 1][2] = scan.nextLine() + " ";
          for (int i = 0; i < tableUpdated.length - 1; i++) {
            for (int j = 0; j < tableUpdated[i].length; j++) {
              tableUpdated[i][j] = oldTable[i][j];
            }
          }
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i< tableUpdated.length; i++) {
            for (int j = 0; j < tableUpdated[i].length-1; j++) {
              sb.append(tableUpdated[i][j]).append(",");
            }
            for (int j = tableUpdated[i].length-1; j <= tableUpdated[i].length-1; j++) {
              sb.append(tableUpdated[i][j]);
            }
            sb.append("\n");
          }
          Path taskTemp = Paths.get("src/main/java/pl/coderslab/tasksTemp.csv");
          Files.writeString(taskTemp, sb);
        }
      }
    }
    showOptions();
  }

  public static void remove() throws IOException {
    String[][] oldTable = updated();
    String[][] tableUpdated = new String[oldTable.length-1][oldTable[0].length];
    Scanner scan = new Scanner(System.in);
    System.out.println("Please select number to remove");
    if (!scan.hasNext("exit")) {
      try {
        int num = scan.nextInt();
        if (num >= 0 && num <= tableUpdated.length) {
          for (int i = 0; i < num; i++) {
            for (int j = 0; j < tableUpdated[i].length; j++) {
              tableUpdated[i][j] = oldTable[i][j];
            }
          }
          for (int i = num; i < tableUpdated.length; i++) {
            for (int j = 0; j < tableUpdated[i].length; j++) {
              tableUpdated[i][j] = oldTable[i + 1][j];
            }
          }
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i< tableUpdated.length; i++) {
            for (int j = 0; j < tableUpdated[i].length-1; j++) {
              sb.append(tableUpdated[i][j]).append(",");
            }
            for (int j = tableUpdated[i].length-1; j <= tableUpdated[i].length-1; j++) {
              sb.append(tableUpdated[i][j]);
            }
            sb.append("\n");
          }
          Path taskTemp = Paths.get("src/main/java/pl/coderslab/tasksTemp.csv");
          Files.writeString(taskTemp, sb);
          showOptions();
        } else {
          System.out.println("Incorrect argument passed. Index doesn't exist.");
          remove();
        }
      } catch (InputMismatchException e) {
        System.out.println("Incorrect argument passed. It is not a number");
        remove();
      }
    } else {
      showOptions();
    }
  }
  public static void list() throws IOException {
    System.out.println("list");
    String[][] table = updated();
    for (int i = 0; i < table.length; i++) {
      System.out.print(i + " : ");
      for (int j = 0; j < table[i].length; j++) {
        System.out.print(table[i][j] + " ");
      }
      System.out.print("\n");
    }
    showOptions();
  }
  public static void exit() throws IOException {
    System.out.println(ConsoleColors.RED + "Bye, bye");
    Path task = Paths.get("src/main/java/pl/coderslab/tasks.csv");
    Path taskTemp = Paths.get("src/main/java/pl/coderslab/tasksTemp.csv");
    Files.copy(taskTemp, task, StandardCopyOption.REPLACE_EXISTING);
    Files.delete(taskTemp);
  }
  static String[][] updated() throws IOException {
    Path tasks = Paths.get("src/main/java/pl/coderslab/tasksTemp.csv");
    int tasksLength = 0;
    int column = 0;
    try {
      Scanner scan = new Scanner(tasks);
      while (scan.hasNextLine()) {
        tasksLength = tasksLength + 1;
        String word = scan.nextLine();
        String[] columns = word.split(",");
        column = columns.length;
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
    String[][] result = new String[tasksLength][column];
    Scanner scan = new Scanner(tasks);
    for (int i = 0; i < tasksLength; i++) {
      String row = scan.nextLine();
      String[] columns = row.split(",");
      for (int j = 0; j < column; j++) {
        result[i][j] = columns[j];
      }
    }
    return result;
  }
}