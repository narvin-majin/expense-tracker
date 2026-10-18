import java.sql.*;
import java.util.Scanner;

public class ExpenseTracker {

    static final String URL = "jdbc:sqlite:expense_tracker.db";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL)) {
            System.out.println("Connected to database successfully!");
            createTable(connection);

            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addExpense(connection);
                    case 2 -> viewExpenses(connection);
                    case 3 -> deleteExpense(connection);
                    case 4 -> showTotalSpending(connection);
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error:");
            e.printStackTrace();
        }
    }

    static void createTable(Connection connection) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS expenses (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                description TEXT NOT NULL,
                amount REAL NOT NULL,
                category TEXT NOT NULL,
                expense_date TEXT NOT NULL
            )
            """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }

        System.out.println("Expenses table is ready.");
    }

    static void showMenu() {
        System.out.println("\n========== EXPENSE DETECTIVE ==========");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Delete Expense");
        System.out.println("4. Total Spending");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    static void addExpense(Connection connection) throws SQLException {
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Category: ");
        String category = scanner.nextLine();

        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        String sql = """
            INSERT INTO expenses
            (description, amount, category, expense_date)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement statement =
                 connection.prepareStatement(sql)) {
            statement.setString(1, description);
            statement.setDouble(2, amount);
            statement.setString(3, category);
            statement.setString(4, date);
            statement.executeUpdate();
        }

        System.out.println("Expense added successfully!");
    }

    static void viewExpenses(Connection connection) throws SQLException {
        String sql = """
            SELECT id, description, amount, category, expense_date
            FROM expenses
            ORDER BY expense_date DESC
            """;

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            System.out.printf("%-5s %-20s %-10s %-15s %-12s%n",
                "ID", "DESCRIPTION", "AMOUNT", "CATEGORY", "DATE");

            while (result.next()) {
                System.out.printf("%-5d %-20s ₹%-9.2f %-15s %-12s%n",
                    result.getInt("id"),
                    result.getString("description"),
                    result.getDouble("amount"),
                    result.getString("category"),
                    result.getString("expense_date"));
            }
        }
    }

    static void deleteExpense(Connection connection) throws SQLException {
        System.out.print("Enter expense ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM expenses WHERE id = ?";

        try (PreparedStatement statement =
                 connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Expense deleted successfully!");
            } else {
                System.out.println("No expense found with that ID.");
            }
        }
    }

    static void showTotalSpending(Connection connection) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM expenses";

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            double total = result.getDouble("total");
            System.out.printf("Total spending: ₹%.2f%n", total);
        }
    }
}	
