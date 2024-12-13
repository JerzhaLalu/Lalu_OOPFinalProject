import java.util.Scanner;


public class MainMenu {
    public void MMenu() {
        String username = Login.getLoggedInUsername();
        
        Scanner input = new Scanner(System.in);
        
        Utilities.ClearScreen();
        Utilities.PastryPageLogo();

        System.out.println("");

        System.out.println("User: " + username );

        System.out.println("");

        System.out.println("===============  Main Menu  =============\n");

        System.out.println("[1] View liked recipes");
        System.out.println("[2] View own recipes");
        System.out.println("[3] Other recipes");
        System.out.println("[4] Add a recipe");
        System.out.println("[5] Delete a recipe");
        System.out.println("[6] Ingredient Conversion");
        System.out.println("[7] Log Out");
        System.out.print(" Choice: ");

        int choice = input.nextInt();

        if (choice == 1){
            RecipeApp.displayFavorites();
            Utilities.BackToMenu();
        } else if (choice == 2){
            RecipeApp.displayRecipesByUser(username);
        } else if (choice == 3){
            RecipeApp.displayPublicRecipes();
        } else if (choice == 4){
            RecipeApp.addRecipe();
        } else if (choice == 5){
            RecipeApp.removeRecipe(); 
        }else if (choice == 6){
            Conversion.conversionMenu();
        }
        else if (choice == 7){
            LogInPage.main();
        }
        
        input.close();
    }
}