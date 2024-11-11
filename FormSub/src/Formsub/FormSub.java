package Formsub;

import java.sql.*;

public class FormSub {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/userDB?useSSL=true";
        String username = "root";
        String password = "Freepass@12";

		      // Using try-with-resources for automatic resource management
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connection Successful!");
			
			// inserting data into the database table 
            String sql = "INSERT INTO users (email, password, remember_me) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, "NanaOwus1@gmail.com");
                statement.setString(2, "FA-121YbnQE");
                statement.setInt(3, 1);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Data successfully saved!");
                }
            }

            sql = "SELECT * FROM users";
            try (PreparedStatement statement = conn.prepareStatement(sql); 
			ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    System.out.println("Email: " + resultSet.getString("email"));
                    System.out.println("Password: " + resultSet.getString("password"));
                    System.out.println("Remember Me: " + resultSet.getInt("remember_me"));
                }
            }

        } catch (SQLException e) {
			 System.err.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
