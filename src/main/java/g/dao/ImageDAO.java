package g.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import g.utils.DBUtil;

public class ImageDAO {

    public boolean updateImage(int recipeId, String imgAddr) {
        String sql = "UPDATE recipe SET img_addr = ? WHERE recipe_id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imgAddr);
            stmt.setInt(2, recipeId);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getImageByRecipeId(int recipeId) {
        String sql = "SELECT img_addr FROM recipe WHERE recipe_id = ?";
        String imgAddr = null;

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recipeId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    imgAddr = rs.getString("img_addr");
                    System.out.println("Image address found: " + imgAddr);
                } else {
                    System.out.println("No image found for recipe ID: " + recipeId);
                }
            }

            return imgAddr;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {

        // ImageDAO dao = new ImageDAO();


        // boolean updateResult = dao.updateImage(1, "images/new_image.jpg");
        // System.out.println(updateResult ? "Image updated!" : "Failed to update image.");

        // String img = dao.getImageByRecipeId(1);
        // System.out.println("Image path: " + img);

    }
}
