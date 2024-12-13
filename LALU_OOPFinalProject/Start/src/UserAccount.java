import java.io.*;

public class UserAccount {
    private String username;
    private String password;
    private static final String FILE_PATH = "credentials.txt";

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
        
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username + "," + password;
    }

    
    public static boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
        }
        return false;
    }
}
