import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Recipe {
    String name;
    String category;
    int prepTime;
    int cookTime;
    int servings;
    List<String> ingredients = new ArrayList<>();
    List<String> steps = new ArrayList<>();
    String privacy; 

    public Recipe(String name, String category, int prepTime, int cookTime, int servings, String privacy) {
        this.name = name;
        this.category = category;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.privacy = privacy;
    }

    public void addIngredient(String ingredient) {
        ingredients.add(ingredient);
    }

    public void addStep(String step) {
        steps.add(step);
    }

    public abstract void displayRecipe();

    public String formatForFile(String username) {
        StringBuilder sb = new StringBuilder();
        sb.append("User:  ").append(username).append("\n");
        sb.append("Recipe: ").append(name).append("\n");
        sb.append("Category: ").append(category).append("\n");
        sb.append("Prep Time: ").append(prepTime).append(" minutes\n");
        sb.append("Cook Time: ").append(cookTime).append(" minutes\n");
        sb.append("Servings: ").append(servings).append("\n");
        sb.append("Privacy: ").append(privacy).append("\n");
        sb.append("Ingredients:\n");
        for (String ingredient : ingredients) {
            sb.append("- ").append(ingredient).append("\n");
        }
        sb.append("Steps:\n");
        for (int i = 0; i < steps.size(); i++) {
            sb.append((i + 1)).append(". ").append(steps.get(i)).append("\n");
        }
        sb.append("----\n");
        return sb.toString();
    }
}

class PublicRecipe extends Recipe {
    public PublicRecipe(String name, String category, int prepTime, int cookTime, int servings) {
        super(name, category, prepTime, cookTime, servings, "Public");
    }

    @Override
    public void displayRecipe() {
        System.out.println("\nPublic Recipe: " + name);
        System.out.println("Category: " + category);
        System.out.println("Prep Time: " + prepTime + " minutes");
        System.out.println("Cook Time: " + cookTime + " minutes");
        System.out.println("Servings: " + servings);
        System.out.println("Ingredients:");
        for (String ingredient : ingredients) {
            System.out.println("- " + ingredient);
        }
        System.out.println("Steps:");
        for (int i = 0; i < steps.size(); i++) {
            System.out.println((i + 1) + ". " + steps.get(i));
        }
    }
}

class PrivateRecipe extends Recipe {
    public PrivateRecipe(String name, String category, int prepTime, int cookTime, int servings) {
        super(name, category, prepTime, cookTime, servings, "Private"); 
    }

    @Override
    public void displayRecipe() {
        System.out.println("\nPrivate Recipe: " + name);
        System.out.println("(This recipe is private and only visible to you)");
        System.out.println("Category: " + category);
        System.out.println("Prep Time: " + prepTime + " minutes");
        System.out.println("Cook Time: " + cookTime + " minutes");
        System.out.println("Servings: " + servings);
        System.out.println("Ingredients:");
        for (String ingredient : ingredients) {
            System.out.println("- " + ingredient);
        }
        System.out.println("Steps:");
        for (int i = 0; i < steps.size(); i++) {
            System.out.println((i + 1) + ". " + steps.get(i));
        }
    }
}

class RecipeManager {
    public static final String FILE_PATH = "database.txt";
    public static final String FAVORITES_PATH = "favorites.txt";

    public static void saveRecipe(Recipe recipe, String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(recipe.formatForFile(username));
        } catch (IOException e) {
            System.out.println("An error occurred while saving the recipe.");
            e.printStackTrace();
        }
    }

    public static void saveFavorite(Recipe recipe, String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAVORITES_PATH, true))) {
            writer.write("User:  " + username + "\n");
            writer.write("Recipe: " + recipe.name + "\n");
            writer.write("----\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the favorite.");
            e.printStackTrace();
        }
    }

    public static List<Recipe> loadAllRecipes() throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            Recipe recipe = null;
            boolean readingIngredients = false;
            boolean readingSteps = false;
            String privacy = "Private";
    
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.equals("----")) {
                    if (recipe != null) {
                        recipes.add(recipe);
                    }
                    recipe = null; 
                    readingIngredients = false;
                    readingSteps = false;
                    privacy = "Private"; 
                } else if (line.startsWith("Recipe:")) {
                    String name = line.substring("Recipe:".length()).trim();
                    recipe = new PrivateRecipe(name, "", 0, 0, 0);
                } else if (line.startsWith("Privacy:")) {
                    privacy = line.substring("Privacy:".length()).trim();
                    if (recipe != null) {
                        if (privacy.equalsIgnoreCase("Public")) {
                            recipe = new PublicRecipe(recipe.name, recipe.category, recipe.prepTime, recipe.cookTime, recipe.servings);
                        }
                    }
                } else if (line.startsWith("Category:")) {
                    if (recipe != null) {
                        recipe.category = line.substring("Category:".length()).trim();
                    }
                } else if (line.startsWith("Prep Time:")) {
                    if (recipe != null) {
                        recipe.prepTime = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    }
                } else if (line.startsWith("Cook Time:")) {
                    if (recipe != null) {
                        recipe.cookTime = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    }
                } else if (line.startsWith("Servings:")) {
                    if (recipe != null) {
                        recipe.servings = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                    }
                } else if (line.startsWith("Ingredients:")) {
                    readingIngredients = true;
                    readingSteps = false;
                } else if (line.startsWith("Steps:")) {
                    readingIngredients = false;
                    readingSteps = true;
                } else if (readingIngredients) {
                    if (recipe != null) {
                        recipe.ingredients.add(line.replaceFirst("^-", "").trim());
                    }
                } else if (readingSteps) {
                    if (recipe != null) {
                        recipe.steps.add(line.replaceFirst("^[0-9]+\\.", "").trim());
                    }
                }
            }
    
            if (recipe != null) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }
    

    public static List<Recipe> loadRecipesByUser(String username) throws IOException {
        List<Recipe> userRecipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            Recipe recipe = null;
            boolean readingIngredients = false;
            boolean readingSteps = false;
            String currentUser = "";
            String name = "", category = "";
            int prepTime = 0, cookTime = 0, servings = 0;
            char privacy = 'B';

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.equals("----")) {
                    if (recipe != null && currentUser.equals(username)) {
                        userRecipes.add(recipe);
                    }
                    recipe = null;
                    readingIngredients = false;
                    readingSteps = false;
                } else if (line.startsWith("User: ")) {
                    currentUser = line.substring("User: ".length()).trim();
                } else if (line.startsWith("Recipe: ")) {
                    name = line.substring("Recipe: ".length()).trim();
                } else if (line.startsWith("Category: ")) {
                    category = line.substring("Category: ".length()).trim();
                } else if (line.startsWith("Prep Time: ")) {
                    prepTime = Integer.parseInt(line.substring("Prep Time: ".length(), line.indexOf(" minutes")));
                } else if (line.startsWith("Cook Time: ")) {
                    cookTime = Integer.parseInt(line.substring("Cook Time: ".length(), line.indexOf(" minutes")));
                } else if (line.startsWith("Servings: ")) {
                    servings = Integer.parseInt(line.substring("Servings: ".length()).trim());
                } else if (line.startsWith("Privacy: ")) {
                    privacy = line.charAt("Privacy: ".length());
                    if (privacy == 'A') {
                        recipe = new PrivateRecipe(name, category, prepTime, cookTime, servings);
                    } else {
                        recipe = new PublicRecipe(name, category, prepTime, cookTime, servings);
                    }
                } else if (line.equals("Ingredients:")) {
                    readingIngredients = true;
                    readingSteps = false;
                } else if (line.equals("Steps:")) {
                    readingIngredients = false;
                    readingSteps = true;
                } else if (readingIngredients) {
                    if (recipe != null) {
                        recipe.addIngredient(line.substring(2).trim()); 
                    }
                } else if (readingSteps) {
                    if (recipe != null) {
                        recipe.addStep(line.substring(line.indexOf('.') + 2).trim());
                    }
                }
            }

            if (recipe != null && currentUser.equals(username)) {
                userRecipes.add(recipe);
            }
        }
        return userRecipes;
    }

    public static List<Recipe> loadFavoriteRecipe(String username) throws IOException {
        List<Recipe> favoriteRecipes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FAVORITES_PATH))) {
            String line;
            String currentUser  = null;
            String currentRecipeName = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("User:  ")) {
                    currentUser  = line.substring("User:  ".length()).trim();
                } else if (line.startsWith("Recipe: ")) {
                    currentRecipeName = line.substring("Recipe: ".length()).trim();
                } else if (line.equals("----")) {
                    if (currentUser  != null && currentUser .equals(username) && currentRecipeName != null) {
                        for (Recipe recipe : loadAllRecipes()) {
                            if (recipe.name.equals(currentRecipeName)) {
                                favoriteRecipes.add(recipe);
                                break;
                            }
                        }
                    }
                    currentUser  = null;
                    currentRecipeName = null;
                }
            }
        }
        return favoriteRecipes;
    }

    public static void deleteRecipe(String FILE_PATH, String recipeNameToDelete) {
        
        List<String> remainingRecipes = new ArrayList<>();
        StringBuilder currentRecipe = new StringBuilder();
        boolean recipeFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isRecipeToDelete = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("User:") && currentRecipe.length() > 0) {
                    if (!isRecipeToDelete) {
                        remainingRecipes.add(currentRecipe.toString());
                    }
                    currentRecipe.setLength(0);
                    isRecipeToDelete = false; 
                }

                currentRecipe.append(line).append("\n");

                if (line.startsWith("Recipe: ") && line.contains(recipeNameToDelete)) {
                    isRecipeToDelete = true;
                    recipeFound = true;
                }
            }

            
            if (!isRecipeToDelete && currentRecipe.length() > 0) {
                remainingRecipes.add(currentRecipe.toString());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return;
        }

        if (!recipeFound) {
            System.out.println("Recipe not found: " + recipeNameToDelete);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH)))  {
            for (String recipe : remainingRecipes) {
                writer.write(recipe);
                writer.newLine(); 
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }

        System.out.println("Recipe deleted successfully.");
    }

    public static void deleteFavoriteRecipe(String FAVORITES_PATH, String recipeNameToDelete) {
        List<String> remainingFavorites = new ArrayList<>();
        StringBuilder currentFavorite = new StringBuilder();
        boolean recipeFound = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(FAVORITES_PATH))) {
            String line;
            boolean isFavoriteToDelete = false;
    
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("User:") && currentFavorite.length() > 0) {
                    if (!isFavoriteToDelete) {
                        remainingFavorites.add(currentFavorite.toString());
                    }
                    currentFavorite.setLength(0);
                    isFavoriteToDelete = false; 
                }
    
                currentFavorite.append(line).append("\n");
    
                if (line.startsWith("Recipe: ") && line.contains(recipeNameToDelete)) {
                    isFavoriteToDelete = true;
                    recipeFound = true;
                }
            }
    
            
            if (!isFavoriteToDelete && currentFavorite.length() > 0) {
                remainingFavorites.add(currentFavorite.toString());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the favorites file: " + e.getMessage());
            return;
        }
    
        if (!recipeFound) {
            System.out.println("Favorite recipe not found: " + recipeNameToDelete);
            return;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAVORITES_PATH))) {
            for (String favorite : remainingFavorites) {
                writer.write(favorite);
                writer.newLine(); 
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the favorites file: " + e.getMessage());
        }
    
        System.out.println("Favorite recipe deleted successfully.");
    }
    
}

public class RecipeApp {
    private static int valueChecker(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static char privacyChecker(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            char userInput = input.next().charAt(0);
            input.nextLine();

            if (userInput == 'A' || userInput == 'a') {
                return 'A';
            } else if (userInput == 'B' || userInput == 'b') {
                return 'B';
            } else {
                System.out.println("Invalid input. Enter 'A' for Private or 'B' for Public.");
            }
        }
    }

    public static void addRecipe() {
        Scanner input = new Scanner(System.in);
        String username = Login.getLoggedInUsername();
    
        Utilities.ClearScreen();
        Utilities.PastryPageLogo();
    
        System.out.println("");
        System.out.println("User:  " + username);
        System.out.println("");
    
        System.out.println("=========================================\n");
        System.out.println("Add your Recipe:");
    
        System.out.print("Recipe Name: ");
        String recipeName = input.nextLine().trim();
    
        System.out.print("Category: ");
        String category = input.nextLine().trim();
    
        int prepTime = valueChecker(input, "Preparation Time (in minutes): ");
        int cookTime = valueChecker(input, "Cooking Time (in minutes): ");
        int servings = valueChecker(input, "Number of Servings: ");
    
        char privacy = privacyChecker(input, "Set recipe privacy to: [A] Private [B] Public: ");
    
        Recipe newRecipe = (privacy == 'A') 
            ? new PrivateRecipe(recipeName, category, prepTime, cookTime, servings) 
            : new PublicRecipe(recipeName, category, prepTime, cookTime, servings);
    
        System.out.println("Enter ingredients (type 'done' when finished):");
        while (true) {
            System.out.print("Ingredient: ");
            String ingredient = input.nextLine().trim();
            if (ingredient.equalsIgnoreCase("done")) {
                break;
            }
            if (!ingredient.isEmpty()) {
                newRecipe.addIngredient(ingredient);
            } else {
                System.out.println("Ingredient cannot be empty. Please try again.");
            }
        }
    
        System.out.println("Enter steps (type 'done' when finished):");
        int stepNumber = 1;
        while (true) {
            System.out.print("Step " + stepNumber + ": ");
            String step = input.nextLine().trim();
            if (step.equalsIgnoreCase("done")) {
                break;
            }
            if (!step.isEmpty()) {
                newRecipe.addStep(step);
                stepNumber++;
            } else {
                System.out.println("Step cannot be empty. Please try again.");
            }
        }

        RecipeManager.saveRecipe(newRecipe, username);
    
        Utilities.ClearScreen();
        Utilities.PastryPageLogo();
    
        System.out.println("\nRecipe added successfully! Here are the details:");
        newRecipe.displayRecipe();
    
        Utilities.BackToMenu();
    }
    

    public static void displayPublicRecipes() {
        try {
            List<Recipe> allRecipes = RecipeManager.loadAllRecipes();
    
            List<Recipe> publicRecipes = new ArrayList<>();
            for (Recipe recipe : allRecipes) {
                if (recipe instanceof PublicRecipe) { 
                    publicRecipes.add(recipe);
                }
            }
    
            if (publicRecipes.isEmpty()) {
                System.out.println("No public recipes available.");
                Utilities.BackToMenu();
                return;
            }

            Scanner input = new Scanner(System.in);
            int currentIndex = 0;
    
            while (true) {
                Recipe recipe = publicRecipes.get(currentIndex);

                Utilities.ClearScreen();
                Utilities.PastryPageLogo();
                System.out.println("\nPublic Recipe:");
                recipe.displayRecipe();

                System.out.println("=========================================");
                System.out.println("\n[1] Next Recipe");
                System.out.println("[2] Add to Favorites");
                System.out.println("[3] Go Back to Menu");
                System.out.print("Choice: ");
    
                if (input.hasNextInt()) {
                    int choice = input.nextInt();
                    input.nextLine();
    
                    if (choice == 1) {
                        currentIndex = (currentIndex + 1) % publicRecipes.size();
                    } else if (choice == 2) {
                        addToFavorites(recipe);
                    }else if (choice == 3) {
                            Utilities.BackToMenu();
                            return;
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    input.nextLine(); 
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading recipes.");
            e.printStackTrace();
        }
    }
    
    public static void addToFavorites(Recipe recipe) throws IOException {
        String username = Login.getLoggedInUsername();
        List<Recipe> favoriteRecipes = RecipeManager.loadFavoriteRecipe(username);
        if (!favoriteRecipes.contains(recipe)) {
            favoriteRecipes.add(recipe);
            RecipeManager.saveFavorite(recipe, username);
            System.out.println("Recipe added to favorites!");
        } else {
            System.out.println("Recipe is already in favorites.");
        }
    }

    public static void displayFavorites() {
        String username = Login.getLoggedInUsername();
        Scanner input = new Scanner(System.in);
        try { 
            List<Recipe> favoriteRecipes = RecipeManager.loadFavoriteRecipe(username);
            Utilities.ClearScreen();
            Utilities.PastryPageLogo();
    
            if (favoriteRecipes.isEmpty()) {
                System.out.println("No favorite recipes found.");
                Utilities.BackToMenu();
                return;
            }
    
            int currentIndex = 0;
    
            while (true) {
                
                Recipe recipe = favoriteRecipes.get(currentIndex);
    
                Utilities.ClearScreen();
                Utilities.PastryPageLogo();
    
                System.out.println("\n           Favorite Recipes\n");
                System.out.println("=========================================");
                recipe.displayRecipe();
                System.out.println("=========================================");
    
                System.out.println(" [1] Next Recipe");
                System.out.println(" [2] Delete Recipe");
                System.out.println(" [3] Go back to menu");
                System.out.print(" Choice: ");
    
                if (input.hasNextInt()) {
                    int choice = input.nextInt();
                    input.nextLine();
    
                    switch (choice) {
                        case 1: 
                            currentIndex = (currentIndex + 1) % favoriteRecipes.size();
                            break;
    
                        case 2: 
                            RecipeManager.deleteFavoriteRecipe(RecipeManager.FAVORITES_PATH, recipe.name);
                            favoriteRecipes.remove(currentIndex); 
                            if (favoriteRecipes.isEmpty()) {
                                System.out.println("No more favorite recipes.");
                                Utilities.BackToMenu();
                                return;
                            }
                            currentIndex = currentIndex % favoriteRecipes.size();
                            break;
    
                        case 3: 
                            Utilities.BackToMenu();
                            return;
    
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    input.nextLine();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading favorites.");
            e.printStackTrace();
            Utilities.BackToMenu();
        }
    }
    

    public static void removeRecipe() {
        Scanner input = new Scanner(System.in);

        String FILE_PATH = "database.txt";

        System.out.print("Enter the name of the recipe to delete: ");
        String recipeNameToDelete = input.nextLine();

        RecipeManager.deleteRecipe(FILE_PATH, recipeNameToDelete);

        Utilities.BackToMenu();

        input.close();
    }
        
    public static void displayRecipesByUser(String username) {
        try {
            List<Recipe> userRecipes = RecipeManager.loadRecipesByUser(username);
    
            Utilities.ClearScreen();
            Utilities.PastryPageLogo();
    
            System.out.println("\nRecipes by " + username + "\n");
    
            if (userRecipes.isEmpty()) {
                System.out.println("No recipes found for user: " + username);
                Utilities.BackToMenu();
                return;
            }
    
            Scanner input = new Scanner(System.in);
            int currentIndex = 0;
    
            while (true) {
                Recipe recipe = userRecipes.get(currentIndex);
    
                Utilities.ClearScreen();
                Utilities.PastryPageLogo();
    
                System.out.println("\nRecipes by " + username + "\n");
                System.out.println("-------------------");
                recipe.displayRecipe();
                System.out.println("-------------------");
    
                System.out.println(" [1] Next Recipe");
                System.out.println(" [2] Delete Recipe");
                System.out.println(" [3] Go back to menu");
                System.out.print(" Choice: ");
    
                if (input.hasNextInt()) {
                    int choice = input.nextInt();
                    input.nextLine();
    
                    switch (choice) {
                        case 1: 
                            currentIndex = (currentIndex + 1) % userRecipes.size();
                            break;
    
                        case 2: 
                            RecipeManager.deleteRecipe(RecipeManager.FILE_PATH, recipe.name);
                            userRecipes.remove(currentIndex);
                            if (userRecipes.isEmpty()) {
                                System.out.println("No more recipes to display.");
                                Utilities.BackToMenu();
                                return;
                            }
                            currentIndex = currentIndex % userRecipes.size();
                            break;
    
                        case 3: 
                            Utilities.BackToMenu();
                            return;
    
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    input.nextLine();
                }
                
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred while loading recipes.");
            e.printStackTrace();
            Utilities.BackToMenu();
        }
        
    }
    
} 

