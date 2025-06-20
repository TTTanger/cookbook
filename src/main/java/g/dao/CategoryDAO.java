package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import g.model.Category;
import g.utils.DBUtil;

public class CategoryDAO {
    
    public boolean createCategory(String categoryName) {
        String sql = "INSERT INTO category (category_name) VALUES (?)";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoryName);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCategory(int categoryId) {
        String sql = "DELETE FROM category WHERE category_id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateCategory(int categoryId, String CategoryName) {
        String sql = "UPDATE category SET category_name = ? WHERE category_id = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, CategoryName);
            stmt.setInt(2, categoryId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Category> getCategoriesByIds(List<Integer> categoryIds) {

        List<Category> categories = new ArrayList<>();

        if (categoryIds == null || categoryIds.isEmpty()) {
            return categories;
        }

        try {
            for (int categoryId : categoryIds) {
                String sql = "SELECT category_id, category_name FROM category WHERE category_id = ?";

                try (Connection conn = DBUtil.getConnection();PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setInt(1, categoryId);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            categories.add(new Category(
                                rs.getInt("category_id"),
                                rs.getString("category_name")
                            ));
                        }
                    }
                }
            }
            return categories;

        } catch (Exception e) {
            e.printStackTrace();
            return categories;
        }
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM category";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                categories.add(new Category(rs.getInt("category_id"), rs.getString("category_name")));
            }
            System.out.println("Categories: ");
            for (Category category : categories) {
                System.out.println(category);
            }
            return categories;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {

        // CategoryDAO categorydao = new CategoryDAO();
        
        // categorydao.createCategory("tc1");
        // categorydao.createCategory("tc2");
        // categorydao.createCategory("tc3");
        // categorydao.createCategory("tc4");
        // categorydao.deleteCategory(13);
        // categorydao.updateCategory(14, "update");
        // List<Category> categories =categorydao.getAllCategories();
        // System.out.println("Success: Found " + categories.size() + " categories.");
        //     for (Category category : categories) {
        //         System.out.println("Category ID: " + category.getCategoryId() +
        //                            ", Name: " + category.getCategoryName());
        // }

    }
}