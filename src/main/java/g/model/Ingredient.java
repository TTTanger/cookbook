package g.model;

public class Ingredient {

    private int pairId;
    private int recipeId;
    private String ingredientName;
    private String ingredientAmount;

    public Ingredient() {
    }

    public Ingredient(int pairId,int recipeId, String ingredientName,String ingredientAmount) {
        this.pairId = pairId;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.ingredientAmount = ingredientAmount;

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
    
    public String getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(String ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    @Override 
    public String toString() {
        return "Ingredient{" +
                "pairId" + pairId + 
                ", ingredientName='" + ingredientName + '\'' +
                ", ingredientAmount=" + ingredientAmount + 
                '}';
    }
}
