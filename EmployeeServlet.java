import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "your_password";
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String empId = request.getParameter("empId");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            out.println("<html><body>");
            out.println("<h2>Employee Directory</h2>");
            out.println("<form method='get' action='EmployeeServlet'>");
            out.println("Search by Employee ID: <input type='text' name='empId' />");
            out.println("<input type='submit' value='Search' />");
            out.println("</form><br>");
            Statement stmt = con.createStatement();
            String query;
            if (empId != null && !empId.trim().isEmpty()) {
                query = "SELECT * FROM employees WHERE id = " + empId;
            } else {
                query = "SELECT * FROM employees";
            }
            ResultSet rs = stmt.executeQuery(query);
            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Department</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("department") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body></html>");
            con.close();
        } catch (Exception e) {
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }
}
