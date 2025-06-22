package g.dto;

public class CategoryResponse {
    
    private int categoryId;
    private String categoryName;

    public CategoryResponse(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    public int getCategoryId() {
        return categoryId;
    }   

    public String getCategoryName() {
        return categoryName;
    }
}
