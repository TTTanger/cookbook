package g.model;

public class Ingredient {

    private int pairId;
    private int recipeId;
    private String ingredientName;
    private int ingredientValue;

    public Ingredient() {
    }

    public Ingredient(int pairId,int recipeId, String ingredientName,int ingredientValue) {
        this.pairId = pairId;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.ingredientValue = ingredientValue;

    }

    public int getPairId(){
        return pairId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }    
    
    public int getIngredientValue() {
        return ingredientValue;
    }

    public void setIngredientValue(int ingredientValue) {
        this.ingredientValue = ingredientValue;
    }

    @Override 
    public String toString() {
        return "Ingredient{" +
                "pairId" + pairId + 
                ", ingredientName='" + ingredientName + '\'' +
                ", ingredientValue=" + ingredientValue + 
                '}';
    }
}
