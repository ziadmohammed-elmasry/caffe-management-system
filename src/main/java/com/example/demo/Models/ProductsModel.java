package com.example.demo.Models;

import com.example.demo.Controllers.Product.Product;
import com.example.demo.Database.DbConnection;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsModel {
    public static List<Product> getAllProducts() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM products";
        PreparedStatement pstm = connection.prepareStatement(sql);
        List<Product> products = new ArrayList<>();

        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            products.add(new Product(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getDouble(5)));
        }
        return products;
    }
    public static List<Product> getAllProductsWithImages() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM products";
            PreparedStatement pstm = connection.prepareStatement(sql);
            List<Product> products = new ArrayList<>();
            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                String product_id = resultSet.getString(1);
                String product_name = resultSet.getString(2);
                String product_type = resultSet.getString(3);
                int stock = resultSet.getInt(4);
                double price = resultSet.getDouble(5);
                byte[] imageBytes = resultSet.getBytes(6);
                products.add(new Product(product_id, product_name, product_type, stock, price, imageBytes));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteProduct(String productId) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "DELETE FROM products WHERE product_id=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, productId);
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addProduct(Product product, Image image) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "INSERT INTO products VALUES(?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, product.getProductId());
            pstm.setString(2, product.getProductName());
            pstm.setString(3, product.getProductType());
            pstm.setInt(4, product.getStock());
            pstm.setDouble(5, product.getPrice());


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            pstm.setBytes(6, imageBytes);

            return pstm.executeUpdate() > 0;
        } catch (SQLException | IOException e) {

            throw new RuntimeException(e);
        }
    }

    public static Product searchProduct(String productId) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM products WHERE product_id=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, productId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String product_id = resultSet.getString(1);
                String product_name = resultSet.getString(2);
                String product_type = resultSet.getString(3);
                int stock = resultSet.getInt(4);
                double price = resultSet.getDouble(5);
                byte[] imageBytes = resultSet.getBytes(6);
                return new Product(product_id, product_name, product_type, stock, price, imageBytes);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public static String generateNextProductId() {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT product_id FROM products ORDER BY product_id DESC LIMIT 1";
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String lastProductId = resultSet.getString(1);
                int newProductId = Integer.parseInt(lastProductId.substring(1)) + 1;
                if (newProductId < 10) {
                    return "P00" + newProductId;
                } else if (newProductId < 100) {
                    return "P0" + newProductId;
                } else {
                    return "P" + newProductId;
                }
            } else {
                return "P001";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean updateProduct(Product product, Image image) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "UPDATE products SET product_name=?, product_type=?, stock=?, price=?, image=? WHERE product_id=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, product.getProductName());
            pstm.setString(2, product.getProductType());
            pstm.setInt(3, product.getStock());
            pstm.setDouble(4, product.getPrice());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            pstm.setBytes(5, imageBytes);

            pstm.setString(6, product.getProductId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getStockAmount(String productName) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT stock FROM products WHERE product_name=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, productName);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static void updateStock(String name, int decreaseStock) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "UPDATE products SET stock=? WHERE product_name=?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            int newStock = getStockAmount(name)-decreaseStock;
            pstm.setInt(1, newStock);
            pstm.setString(2, name);
            pstm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
