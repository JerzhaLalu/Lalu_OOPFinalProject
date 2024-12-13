import java.util.Scanner;

public class Conversion {
    public static void conversionMenu() {
        Scanner input = new Scanner(System.in);
            
            String username = Login.getLoggedInUsername();

            

        while (true) {

            Utilities.ClearScreen();
            Utilities.PastryPageLogo();

            System.out.println("");
            System.out.println("User:  " + username);
            System.out.println("");

            System.out.println("========= Ingredients Conversion ==========\n");

            double amount = 0;
            int measurement = 0;
            int ingredient = 0;
            int measurementConvert = 0;

            
            while (true) {
                System.out.print("Input amount: ");
                if (input.hasNextDouble()) {
                    amount = input.nextDouble();
                    if (amount > 0) break;
                    System.out.println("Amount must be greater than 0.");
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    input.next(); 
                }
            }

            System.out.println("");
            System.out.println("Unit of Measurements:");
            System.out.println("[1] Teaspoon");
            System.out.println("[2] Tablespoon");
            System.out.println("[3] Cup");
            System.out.println("[4] Grams");
            System.out.println("[5] Ounce");
            System.out.println("[6] Fluid Ounce");

            System.out.println("");

            measurement = getValidIntegerInput(input, "Input unit of measurement (1-6): ", 1, 6);

            System.out.println("");
            System.out.println("Ingredients:");
            System.out.println("[1] Flour / Baking Soda / Baking Powder");
            System.out.println("[2] Sugar");
            System.out.println("[3] Butter");
            System.out.println("[4] Milk");

            System.out.println("");
            ingredient = getValidIntegerInput(input, "Input ingredient (1-4): ", 1, 4);

            System.out.println("");
            measurementConvert = getValidIntegerInput(input, "Input unit of measurement to convert to (1-6): ", 1, 6);

            
            double convertedAmount = convert(amount, measurement, measurementConvert, ingredient);

            if (convertedAmount != -1) {
                System.out.printf("\n%.2f %s of %s is equivalent to %.2f %s.%n",
                        amount, getMeasurementName(measurement),
                        getIngredientName(ingredient),
                        convertedAmount, getMeasurementName(measurementConvert));

                System.out.println("\n[1] Convert Another Ingredient");
                System.out.println("[2] Go back to menu");

                int choice = getValidIntegerInput(input, "Enter your choice: ", 1, 2);

                if (choice == 1) {
                    continue; 
                } else if (choice == 2) {
                    MainMenu obj = new MainMenu();
                    obj.MMenu();
                    break;
                } 
            } else {
                System.out.println("Conversion not possible.");
            }
        }
    }

    private static int getValidIntegerInput(Scanner input, String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (input.hasNextInt()) {
                value = input.nextInt();
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.next(); 
            }
        }
    }

    private static double convert(double amount, int fromUnit, int toUnit, int ingredient) {
        
        double[][] conversionFactors = {
                {1, 0.333, 0.0208, 4.92, 0.0353, 0.0338}, // Teaspoon
                {3, 1, 0.0625, 14.79, 0.106, 0.1014},     // Tablespoon
                {48, 16, 1, 236.59, 8.345, 8.115},        // Cup
                {0.21, 0.0676, 0.0042, 1, 0.0353, 0.0338}, // Gram
                {5.69, 1.92, 0.12, 28.35, 1, 0.9608},     // Ounce
                {5.92, 1.92, 0.12, 29.57, 1.043, 1}       // Fluid Ounce
        };

        if (fromUnit < 1 || fromUnit > 6 || toUnit < 1 || toUnit > 6) {
            return -1;
        }

       
        double amountInBaseUnit = amount * conversionFactors[fromUnit - 1][0]; 
        return amountInBaseUnit * conversionFactors[0][toUnit - 1]; 
    }

    private static String getMeasurementName(int unit) {
        switch (unit) {
            case 1: return "Teaspoon";
            case 2: return "Tablespoon";
            case 3: return "Cup";
            case 4: return "Grams";
            case 5: return "Ounce";
            case 6: return "Fluid Ounce";
            default: return "Unknown Unit";
        }
    }

    private static String getIngredientName(int ingredient) {
        switch (ingredient) {
            case 1: return "Flour / Baking Soda / Baking Powder";
            case 2: return "Sugar";
            case 3: return "Butter";
            case 4: return "Milk";
            default: return "Unknown Ingredient";
        }
    }

}
