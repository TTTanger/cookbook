package g.dto;

public class RecipeSummaryResponse {
    private int recipeId;
    private String title;
    private String imgAddr;

    public RecipeSummaryResponse(int recipeId, String title, String imgAddr) {
        this.recipeId = recipeId;
        this.title = title;
        this.imgAddr = imgAddr;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getTitle() {
        return title;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    @Override
    public String toString() {
        return "RecipeSummaryResponse{" +
                "recipeId=" + recipeId +
                ", title='" + title + '\'' +
                ", imgAddr='" + imgAddr + '\'' +
                '}';
    }
}
