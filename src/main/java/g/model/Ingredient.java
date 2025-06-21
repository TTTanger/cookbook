package g.model;
import java.util.Objects;

public class Ingredient {

    private int pairId;
    private int recipeId;
    private String ingredientName;
    private int ingredientAmount;
    private String unit;

    public Ingredient() {
    }

    public Ingredient(int pairId, int recipeId, String ingredientName, int ingredientAmount, String unit) {
        this.pairId = pairId;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.ingredientAmount = ingredientAmount;
        this.unit = unit;
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

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Ingredient pairId(int pairId) {
        setPairId(pairId);
        return this;
    }

    public Ingredient recipeId(int recipeId) {
        setRecipeId(recipeId);
        return this;
    }

    public Ingredient ingredientName(String ingredientName) {
        setIngredientName(ingredientName);
        return this;
    }

    public Ingredient ingredientAmount(int ingredientAmount) {
        setIngredientAmount(ingredientAmount);
        return this;
    }

    public Ingredient unit(String unit) {
        setUnit(unit);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Ingredient)) {
            return false;
        }
        Ingredient ingredient = (Ingredient) o;
        return pairId == ingredient.pairId && recipeId == ingredient.recipeId && Objects.equals(ingredientName, ingredient.ingredientName) && ingredientAmount == ingredient.ingredientAmount && Objects.equals(unit, ingredient.unit);
    }

    @Override
    public String toString() {
        return "{" +
            " pairId='" + getPairId() + "'" +
            ", recipeId='" + getRecipeId() + "'" +
            ", ingredientName='" + getIngredientName() + "'" +
            ", ingredientAmount='" + getIngredientAmount() + "'" +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
