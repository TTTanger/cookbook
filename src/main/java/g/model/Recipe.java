package g.model;

public class Recipe {
    private int recipeId;
    private String title;
    private int prepTime;
    private int cookTime;
    private String instruction;
    private String imgAddr;
    private int serve;

    public Recipe() {
    }

    public Recipe(int recipeId, String title, int prepTime, int cookTime, String instruction, String imgAddr, int serve) {
        this.recipeId = recipeId;
        this.title = title;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.instruction = instruction;
        this.imgAddr = imgAddr;
        this.serve = serve;
    }
    
    public int getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrepTime() {
        return this.prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getInstruction() {
        return this.instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getImgAddr() {
        return this.imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public int getServe() {
        return this.serve;
    }

    public void setServe(int serve) {
        this.serve = serve;
    }
    
    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", prepTime=" + prepTime +
                ", cookTime=" + cookTime +
                ", instruction='" + instruction + '\'' +
                ", imgAddr='" + imgAddr + '\'' +
                ", serve=" + serve +
                '}';
    }
    
}
