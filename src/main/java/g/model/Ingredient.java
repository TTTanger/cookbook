package g.model;

public class Ingredient {
    private int ingredientValue;
    private String ingredientName;

    public Ingredient(int ingredientValue, String ingredientName) {
        this.ingredientValue = ingredientValue;
        this.ingredientName = ingredientName;
    }

    public int getIngredientValue() {
        return ingredientValue;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientValue(int ingredientValue) {
        this.ingredientValue = ingredientValue;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
    
    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientValue=" + ingredientValue +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }

}
