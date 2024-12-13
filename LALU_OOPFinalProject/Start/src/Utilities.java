import java.util.Scanner;

public class Utilities  {
        public static void PastryPageLogo() {
            System.out.println("=========================================");
            System.out.println("");
            System.out.println("\r\n"+
                                "█▀█ ▄▀█ █▀ ▀█▀ █▀█ █▄█ █▀█ ▄▀█ █▀▀ █▀▀ █▀\r\n" +
                                "█▀▀ █▀█ ▄█ ░█░ █▀▄ ░█░ █▀▀ █▀█ █▄█ ██▄ ▄█\r\n");
            System.out.println("");
            System.out.println("=========================================");
        }
        
        public static void ClearScreen() {
            System.out.print("\033[H\033[2J");
        }
    
        public static void BackToMenu(){
            Scanner input = new Scanner (System.in);

            System.out.println("");
            System.out.println(" <- Go back to Menu (Press any button)");
            input.nextLine();
        
            MainMenu obj = new MainMenu();
            obj.MMenu();

            input.close();

        }

        public static void BackToLogIn(){
            Scanner input = new Scanner (System.in);

            System.out.println("");
            System.out.println(" <- Go back to Log In (Press any button)");
            input.nextLine();
        
            LogInPage.main();

            input.close();

        }
    }
    