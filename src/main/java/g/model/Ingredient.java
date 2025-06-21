package g.model;

public class Ingredient {

    private int pairId;
    private int recipeId;
    private String ingredientName;
    private int ingredientAmount;
    private String ingredientUnit;

    public Ingredient() {
    }

    public Ingredient(int pairId,int recipeId, String ingredientName,int ingredientAmount,String ingredientUnit) {
        this.pairId = pairId;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.ingredientAmount = ingredientAmount;
        this.ingredientUnit = ingredientUnit;

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

    public int getIngredientAmount() {
        return ingredientAmount;
    }   

    public void setIngredientAmount(int ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    @Override 
    public String toString() {
        return "Ingredient{" +
                "pairId" + pairId + 
                ", ingredientName='" + ingredientName + '\'' +
                ", ingredientAmount=" + ingredientAmount + ingredientUnit +
                '}';
    }
}
