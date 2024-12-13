import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class LogInPage {
    public static void main() {
        Scanner input = new Scanner(System.in);
        
        while (true) {
            Utilities.ClearScreen();
            Utilities.PastryPageLogo();

            System.out.println("[1] Log in");
            System.out.println("[2] Sign up");
            System.out.println("[0] Exit");
            System.out.print("Choice: ");

            int choice = -1;

            while (true) {
                try {
                    choice = input.nextInt();
                    input.nextLine(); 
                    if (choice >= 0 && choice <= 2) {
                        break; 
                    } else {
                        System.out.println("Error: Invalid choice. Please enter 0, 1, or 2."); 
                    }
                } catch (InputMismatchException e) {
                    System.out.print("Error, please enter a valid integer: ");
                    input.nextLine(); 
                } 
            } 

            if (choice == 1) {
                Login.loginPage(input);
            } else if (choice == 2) {
                SignUp.signUpPage(input);
            } else if (choice == 0) {
                System.out.println("Exiting the application. Goodbye!");
                break; 
            } else {
                System.out.println("Error: Invalid choice. Try again.");
                Utilities.BackToLogIn();
            }
        }
    } 
}

class Login {
    private static final String FILE_PATH = "credentials.txt";
    private static String loggedInUsername;

    public static void loginPage(Scanner input) {
        System.out.println("");

        while (true) {
            System.out.print("Username: ");
            String username = input.nextLine();

            if (UserAccount.usernameExists(username)) {
                System.out.print("Password: ");
                String password = input.nextLine();

                if (passwordIsCorrect(username, password)) {
                    loggedInUsername = username; 
                    System.out.println("\nLogin successful!");

                    MainMenu obj = new MainMenu();
                    obj.MMenu();
                    break;
                    
                } else {
                    System.out.println("Incorrect password. Try again.");
                    Utilities.BackToLogIn();
                }
            } else {
                System.out.println("Invalid username. Try again.");
                Utilities.BackToLogIn();
            }
        }
    }

    private static boolean passwordIsCorrect(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
        return false;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername; 
    }
}

class SignUp {
    private static final String FILE_PATH = "credentials.txt";

    public static void signUpPage(Scanner input) {
        System.out.println("");

        while (true) {
            System.out.print("Username: ");
            String username = input.nextLine();

            if (UserAccount.usernameExists(username)) {
                System.out.println("Username is taken. Try another one.");
            } else {
                System.out.print("Password: ");
                String password = input.nextLine();

                if (password.length() < 8) {
                    System.out.println("Password must be at least 8 characters long. Try again.");
                } else {
                    saveCredentials(username, password);
                    System.out.println("Sign-up successful!");

                    Utilities.BackToLogIn();

                    break;
                }
            }
        }
    }

    private static void saveCredentials(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + "," + password);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the credentials.");
        }
    }
}