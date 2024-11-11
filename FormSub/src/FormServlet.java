
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submitForm")
public class FormServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/userDB";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Freepass@12";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember_me");

        // Convert checkbox value to integer (checked checkbox sends "on" value)
        int rememberMeValue = (rememberMe != null) ? 1 : 0;

        // Insert data into the database
        String insertSql = "INSERT INTO users (email, password, remember_me) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD); PreparedStatement statement = conn.prepareStatement(insertSql)) {

            statement.setString(1, email);
            statement.setString(2, password);  // Use hashed password in real applications
            statement.setInt(3, rememberMeValue);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                response.getWriter().write("Data successfully saved!");
            } else {
                response.getWriter().write("Failed to save data.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error: " + e.getMessage());
        }
    }
}
