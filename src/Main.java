

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final JFrame frame = new JFrame("EasyKanban");
    private static String username;
    private static String password;
    private static String firstName;
    private static String lastName;
    private static boolean loggedIn = false;

    private static List<String> developers = new ArrayList<>();
    private static List<String> taskNames = new ArrayList<>();
    private static List<String> taskIDs = new ArrayList<>();
    private static List<Double> taskDurations = new ArrayList<>();
    private static List<String> taskStatuses = new ArrayList<>();

    public static void main(String[] args) {
        register();
        if (login()) {
            displayWelcomeMessage();
            showMenu();
        } else {
            JOptionPane.showMessageDialog(frame, "Login required. Exiting the application.");
        }
    }

    public static void register() {
        String[] options = {"Register", "Cancel"};
        int option = JOptionPane.showOptionDialog(frame, "Welcome to User Registration", "EasyKanban", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (option == 0) {
            while (true) {
                String[] fields = {"Username", "Password", "First Name", "Last Name"};
                String[] inputs = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    inputs[i] = (String) JOptionPane.showInputDialog(frame, "Enter " + fields[i] + ":", fields[i]);
                }

                username = inputs[0];
                password = inputs[1];
                firstName = inputs[2];
                lastName = inputs[3];

                if (isValidUsername(username)) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username. Must contain an underscore and be no more than 5 characters in length.");
                }
            }

            JOptionPane.showMessageDialog(frame, "Registration Successful");
        }
    }

    public static boolean isValidUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean login() {
        int option = JOptionPane.showOptionDialog(frame, "Welcome to Login", "EasyKanban", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Login", "Cancel"}, "Login");

        if (option == 0) {
            while (true) {
                String[] fields = {"Username", "Password"};
                String[] inputs = new String[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    inputs[i] = (String) JOptionPane.showInputDialog(frame, "Enter your " + fields[i] + ":", fields[i]);
                }

                if (inputs[0].equals(username) && inputs[1].equals(password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful! Welcome, " + firstName + " " + lastName);
                    loggedIn = true;
                    return true;
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        }

        return false;
    }

    public static void displayWelcomeMessage() {
        JOptionPane.showMessageDialog(frame, "Welcome to EasyKanban");
    }

    public static void showMenu() {
        int choice = 0;
        do {
            String[] options = {"Add tasks", "Show report", "Display tasks with status 'Done'", "Display task with the longest duration", "Search for a task by name", "Search for tasks by developer", "Delete a task by name", "Quit"};
            int option = JOptionPane.showOptionDialog(frame, "\nMain Menu", "EasyKanban Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (option) {
                case 0:
                    addTasks();
                    break;
                case 1:
                    showReport();
                    break;
                case 2:
                    displayTasksWithStatusDone();
                    break;
                case 3:
                    displayTaskWithLongestDuration();
                    break;
                case 4:
                    searchTaskByName();
                    break;
                case 5:
                    searchTasksByDeveloper();
                    break;
                case 6:
                    deleteTaskByName();
                    break;
                case 7:
                    System.out.println("\nThank you for using EasyKanban. Goodbye!");
                    break;
            }
        } while (choice != 7);
    }

    public static void addTasks() {
        String[] options = {"Add", "Cancel"};
        int option = JOptionPane.showOptionDialog(frame, "Add a new task", "EasyKanban", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (option == 0) {
            String[] fields = {"Task Name", "Task ID", "Task Duration", "Task Status", "Developer"};
            String[] inputs = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                inputs[i] = (String) JOptionPane.showInputDialog(frame, "Enter " + fields[i] + ":", fields[i]);
            }

            taskNames.add(inputs[0]);
            taskIDs.add(inputs[1]);
            taskDurations.add(Double.parseDouble(inputs[2]));
            taskStatuses.add(inputs[3]);
            developers.add(inputs[4]);

            JOptionPane.showMessageDialog(frame, "Task added successfully");
        }
    }

    public static void showReport() {
        String report = "";
        for (int i = 0; i < taskNames.size(); i++) {
            report += taskNames.get(i) + "\n";
        }
        JOptionPane.showMessageDialog(frame, report);
    }

    public static void displayTasksWithStatusDone() {
        String report = "";
        for (int i = 0; i < taskNames.size(); i++) {
            if (taskStatuses.get(i).equals("Done")) {
                report += taskNames.get(i) + "\n";
            }
        }
        JOptionPane.showMessageDialog(frame, report);
    }

    public static void displayTaskWithLongestDuration() {
        double maxDuration = 0;
        int index = -1;
        for (int i = 0; i < taskDurations.size(); i++) {
            if (taskDurations.get(i) > maxDuration) {
                maxDuration = taskDurations.get(i);
                index = i;
            }
        }

        if(index != -1){
            String report = taskNames.get(index) + "\n";
            JOptionPane.showMessageDialog(frame, report);
        }else{
            JOptionPane.showMessageDialog(frame,"No tasks available");
        }
    }

    public static void searchTaskByName() {
        String searchInput = (String) JOptionPane.showInputDialog(frame, "Enter the name of the task to search:", "Search Task");
        for (int i = 0; i < taskNames.size(); i++) {
            if (taskNames.get(i).equals(searchInput)) {
                String report = taskNames.get(i) + "\n";
                JOptionPane.showMessageDialog(frame, report);
                return;
            }
        }

        JOptionPane.showMessageDialog(frame, "Task not found");
    }

    public static void searchTasksByDeveloper() {
        String searchInput = (String) JOptionPane.showInputDialog(frame, "Enter the name of the developer to search:", "Search Tasks by Developer");
        List<String> matchingTasks = new ArrayList<>();
        for (int i = 0; i < developers.size(); i++) {
            if (developers.get(i).equals(searchInput)) {
                matchingTasks.add(taskNames.get(i));
            }
        }

        if (!matchingTasks.isEmpty()) {
            String report="";
            for(String name: matchingTasks){
                report+=name+"\n";
            }
            JOptionPane.showMessageDialog(frame,report);
        } else{
            JOptionPane.showMessageDialog(frame,"No tasks found by this developer");
        }
    }

    public static void deleteTaskByName() {
        String searchInput=(String)JOptionPane.showInputDialog(frame,"Enter the name of the task to delete","Delete Task");

        // implement your logic here
        // right now it doesn't do anything

        // you need to check if the task exists and then remove it from lists
        // also you may want to add some error handling in case the task is not found
        // and also ask user to confirm before deleting

        // example:
        if(taskNames.contains(searchInput)){
            int index=taskNames.indexOf(searchInput);
            if(index!=-1){
                taskNames.remove(index);
                taskIDs.remove(index);
                taskDurations.remove(index);
                taskStatuses.remove(index);
                developers.remove(index);
                JOptionPane.showMessageDialog(frame,"Task deleted successfully");
            }else{
                JOptionPane.showMessageDialog(frame,"Task not found");
            }
        }else{
            JOptionPane.showMessageDialog(frame,"Task not found");
        }


    }
}