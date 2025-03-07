/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package recipecomponents;
/**
 *
 * @author kaitlynhuynh
 */
public class RecipeComponents {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create some item names
        String item1 = "Egg";
        String item2 = "Salt";
        // Create some Ingredient instances using a constructor
        Ingredient i1 = new Ingredient(5.0, item1);
        Ingredient i2 = new Ingredient(1, item2);
        //Create some step descriptions
        String step1 = "Crack the egg";
        String step2 = "Mix it up";
        // Create a Recipe instance
        Recipe r1 = new Recipe();
        //Populating Recipe instances
        r1.addIngredient(i1);
        r1.addIngredient(i2);
        r1.addStep(step1);
        r1.addStep(step2);
        // Use toString (override) on a Recipe instance
        System.out.println(r1);
    }
}
// A class to store Ingredient information
class Ingredient {
    //Instantiate some private attributes
    private double measurement;
    private String item;
    //Create an Ingredient constructor
    public Ingredient(double newMeasurement, String newItem) {
        //Populate the instantiated private attributes
       this.measurement = newMeasurement;
       this.item = newItem;
    }
    // Attribute getters
    public double getMeasurement() {
        return this.measurement;
    }
    public String getItem() {
        return this.item;
    }
    // Attribute setters 
    public void setMeasurement(double newVal) {
        this.measurement = newVal;
    }
    public void setItem(String newItem) {
        this.item = newItem;
    }
}
// A class to store an entire recipe
class Recipe {
    //Instantiate some private attribuets
    private int numOfSteps;
    private Ingredient[] ingredients;
    private String[] steps;
    private int numOfIngredients;
    //Constructor for a Recipe instance
    public Recipe() {
        //Populate the instantiated attributes
        this.numOfSteps = 2; // Default size
        this.numOfIngredients = 2; // Default size
        this.ingredients = new Ingredient[numOfIngredients]; // Create array of nulls
        this.steps = new String[numOfSteps];
        
    }
    // For the case that someone wants to add an ingredient
    public void addIngredient(Ingredient ing) {
        for (int i = 0; i < numOfIngredients; i++) {
            if (this.ingredients[i] == null) {
                this.ingredients[i] = ing;
                return;
            }
        }
    }
    // For the case that someone wants to add a step
    public void addStep(String newStep) {
        for (int i = 0; i < this.numOfSteps; i++) {
            if (steps[i] == null) {
                steps[i] = newStep;
                return;
            }
        }
    }
    // Define what it means to display an instance of a Recipe object
    public String toString() {
        String displayIngr = "";
        for (int i = 0; i < this.numOfIngredients; i++) {
            displayIngr = displayIngr + (this.ingredients[i].getMeasurement() + 
                    " units of " + this.ingredients[i].getItem() + "\r\n");
        }
        String twoBlankLines = "\r\n\r\n";
        String allSteps = "";
        for (int i = 0; i < this.numOfSteps; i++) {
            allSteps += this.steps[i];
            if (i < this.numOfSteps - 1) {
                allSteps += "\r\n";
            }
        }
        return displayIngr + twoBlankLines + allSteps;
    }
}
