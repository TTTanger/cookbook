package g.model;
import java.util.Objects;

public class Ingredient {

    private int pairId;
    private int recipeId;
    private String ingredientName;
    private int ingredientAmount;
    private String ingredientUnit;

    public Ingredient() {
    }

    public Ingredient(int pairId, int recipeId, String ingredientName, int ingredientAmount, String ingredientUnit) {
        this.pairId = pairId;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.ingredientAmount = ingredientAmount;
        this.ingredientUnit = ingredientUnit;
    }

    public int getPairId() {
        return this.pairId;
    }

    public void setPairId(int pairId) {
        this.pairId = pairId;
    }

    public int getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredientName() {
        return this.ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getIngredientAmount() {
        return this.ingredientAmount;
    }

    public void setIngredientAmount(int ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public String getIngredientUnit() {
        return this.ingredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Ingredient)) {
            return false;
        }
        Ingredient ingredient = (Ingredient) o;
        return pairId == ingredient.pairId && recipeId == ingredient.recipeId && Objects.equals(ingredientName, ingredient.ingredientName) && ingredientAmount == ingredient.ingredientAmount && Objects.equals(ingredientUnit, ingredient.ingredientUnit);
    }

    @Override
    public String toString() {
        return "{" +
            " pairId='" + getPairId() + "'" +
            ", recipeId='" + getRecipeId() + "'" +
            ", ingredientName='" + getIngredientName() + "'" +
            ", ingredientAmount='" + getIngredientAmount() + "'" +
            ", unit='" + getIngredientUnit() + "'" +
            "}";
    }
}
