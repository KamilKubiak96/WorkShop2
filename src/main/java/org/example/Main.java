package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {



    public static void main(String[] args) throws SQLException {

        Connection connection = DBUtill.connect();
        Scanner optionScanner = new Scanner(System.in);
        UserDao userDao = new UserDao();
        login(optionScanner);
        SettingsMenu();
        while (true) {
            String line = optionScanner.nextLine();
            switch (line) {
                case "menu":
                    SettingsMenu();
                    break;
                case "add":
                    addToDatabase(optionScanner, userDao);
                    break;
                case "remove":
                    System.out.println("Select row number to remove:");
                    try {
                        int temp = optionScanner.nextInt();
                        userDao.remove(temp);
                    } catch (InputMismatchException e) {
                        System.out.println("Exception: " + e.getMessage());
                    }
                    System.out.println("Done");
                    break;
                case "update":
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Select row to update");
                    int nr = scanner.nextInt();

                    System.out.println("Adding new User. User name:");
                    String name1 = scanner.next();

                    System.out.println("User email:");
                    String email1 = scanner.next();

                    System.out.println("User password:");
                    String password1 = scanner.next();

                    User userToUpdate = userDao.read(nr);
                    userToUpdate.setUserName(name1);
                    userToUpdate.setEmail(email1);
                    userToUpdate.setPassword(password1);
                    userDao.update(userToUpdate);
                    System.out.println("Done");
                    break;
                case "read":
                    System.out.println("Select row number to read:");
                    try {
                        int rem = optionScanner.nextInt();
                        userDao.readToUpdate(rem);
                    } catch (InputMismatchException e) {
                        System.out.println("Exception: " + e.getMessage());
                    }
                    System.out.println("Done");
                    break;
                case "show":
                    userDao.findAll();
                    System.out.println("Done");
                    break;
                case "quit":
                    System.exit(0);
                    break;
                default:

                    break;
            }
        }
    }

    private static void addToDatabase(Scanner optionScanner, UserDao userDao) {

        System.out.println("Adding new User. User name:");
        String name = optionScanner.nextLine();
        System.out.println("User email:");
        String email = optionScanner.nextLine();
        System.out.println("User password:");
        String password = optionScanner.nextLine();
        userDao.create(new User(name,email, password));
        System.out.println("Done");
    }

    private static void SettingsMenu() {
        System.out.println("=========================");
        System.out.println("Choose option:");
        System.out.println("Add User - 'add'");
        System.out.println("Remove User - 'remove'");
        System.out.println("Update User - 'update'");
        System.out.println("Read User - 'read'");
        System.out.println("Show All Database - 'show'");
        System.out.println("Show options - 'menu'");
        System.out.println("End program - 'quit'");
        System.out.println("=========================");
    }

    private static void login(Scanner optionScanner) {
        while (true) {
            String login = "root";
            String password = "Kamilek44@";
            System.out.println("Podaj login...");
            String logIn = optionScanner.nextLine();
            System.out.println("Podaj haslo...");
            String logPass = optionScanner.nextLine();
            if (logIn.equals(login) && logPass.equals(password)) {
                break;
            } else
                System.out.println("Zły login lub hasło");
        }
    }


}
