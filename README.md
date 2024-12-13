<p align = "center">
  <img src = "PastryPages_Logo.png"  alt="LogoInsert"> 
</p>


## 🍰 Table of Contents
 [1. Project Overview] <br>
 [2. Key Features] <br>
 [3. How it Contributes to UN’s SDG] <br>
 [4. Instructions] <br>

<br>
<br>
<br>

## 🥐 Project Overview<br>
<br> PastryPages is a console program that lets the user store their own recipe, and view and save publicized baking recipes. The program can also convert ingredients to different units of measurements. PastryPages will be a baker's go to cookbook. 
<br>
<br>
<br>

##  🍞 Key Features<br>

**Pastry Pages uses the four OOP principles as follows:** <br>

- **Encapsulation:** It encapsulates the recipe's data and logic within the Recipe class and its subclasses. Encapsulation is used in the Recipe class where the attributes like name, category, prepTime, cookTime, servings, ingredients, and steps are declared as private. <br>
- **Inheritance:** The PublicRecipe and PrivateRecipe classes inherit from the Recipe class, allowing for code reuse. Inheritance is used well in the Recipe class hierarchy. PublicRecipe and PrivateRecipe are subclasses that inherit from the Recipe superclass. The Recipe class contains shared attributes and methods for both PublicRecipe and PrivateRecipe.
PublicRecipe and PrivateRecipe then extend Recipe and can add their specific behavior, such as the privacy flag and the displayRecipe method. <br>
- **Abstraction:** The Recipe class is abstract, providing a simplified interface to interact with recipes without needing to know how they are implemented. Abstraction is used in the Recipe class, which is abstract. This means you cannot directly instantiate a Recipe, but rather you must use a subclass like PublicRecipe or PrivateRecipe. <br>
- **Polymorphism:** Through method overriding, displayRecipe behaves differently based on the actual object type (PublicRecipe or PrivateRecipe), allowing for dynamic method dispatch. <br>

##  🎂 How it Contributes to UN’s SDG </span>

- **SDG 2: Zero Hunger** <br>
Encouraging Nutritious Recipes: This program can encourage wholesome, well-balanced meals by giving users a place to exchange and access healthy recipes. This may inadvertently encourage healthier eating habits, particularly in areas where people are searching for affordable and nutritious food options.<br>

- **SDG 3: Health and Well-Being** <br>
Improved Access to Food Information: By informing users about the nutritional value of the meals they cook, the application can assist people in making better dietary choices.

- **SDG 12:** Responsible Production and Consumption <br>
Reducing Food Waste: By making effective use of leftover or overripe ingredients, the app may help users identify recipes utilizing ingredients they already have at home.

## 🍪 Instructions
1. User LogIn or SignUp: Log in your account, or sign up if you do not have an account.
2. Main Menu: Pick a method you want to perform. For new users, adding a recipe of viewing public recipes are suggested.
   - Adding a recipe: Add your own recipe from its name, preparation time and cooking time, its ingredients, and the steps in making it. Set the privacy to your preffered setting.
   - Viewing recipes: from the menu, you can select to view your own recipes, or view other's publicized recipes. Viewing other's recipes gives you the power to add the recipe to a 'favborite' list.
   - Conversion Calculator: You can use the ingredient conversion to convert different kinds if ingredients to you preffered unit of measurement. 
3. Log out: After finishing your session, you can log out your account.


- Course:
  - CS 211 (Object-Oriented Programming)
- Course Facilitator:
  - Fatima Marie Agdon
