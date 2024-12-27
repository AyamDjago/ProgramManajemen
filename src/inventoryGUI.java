import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// Class for CRUD operations
class InventoryCRUD {
    private static final String DB_URL = "jdbc:sqlite:inventory.db";

    static {
        createTable();
    }

    private static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS inventory " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, quantity INTEGER NOT NULL, " +
                "description TEXT, created_at TEXT, updated_at TEXT)";
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public static void addItem(String name, int quantity, String description) {
        String sql = "INSERT INTO inventory(name, quantity, description, created_at, updated_at) VALUES(?, ?, ?, ?, ?)";
        String currentDate = getCurrentDate();
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, description);
            pstmt.setString(4, currentDate);
            pstmt.setString(5, currentDate);
            pstmt.executeUpdate();
            showInfo("Item added successfully!");
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public static void updateItem(int id, String name, int quantity, String description) {
        String sql = "UPDATE inventory SET name = ?, quantity = ?, description = ?, updated_at = ? WHERE id = ?";
        String currentDate = getCurrentDate();
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, quantity);
            pstmt.setString(3, description);
            pstmt.setString(4, currentDate);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            showInfo("Item updated successfully!");
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public static void deleteItem(int id) {
        String sql = "DELETE FROM inventory WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            showInfo("Item deleted successfully!");
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    public static void fetchItems(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM inventory";
        try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getString("description"), rs.getString("created_at"), rs.getString("updated_at")});
            }
        } catch (SQLException e) {
            showError(e.getMessage());
        }
    }

    private static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    private static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// Class for GUI
public class InventoryGUI {
    private static JTable table;
    private static DefaultTableModel tableModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryGUI::createGUI);
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Inventory Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Panel for input fields
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField descriptionField = new JTextField();

        JButton addButton = new JButton("Add Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                String description = descriptionField.getText();
                InventoryCRUD.addItem(name, quantity, description);
                refreshTable();
            } catch (NumberFormatException ex) {
                showError("Quantity must be a valid number.");
            }
        });

        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String description = descriptionField.getText();
                InventoryCRUD.updateItem(id, name, quantity, description);
                refreshTable();
            } catch (NumberFormatException ex) {
                showError("ID and Quantity must be valid numbers.");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                InventoryCRUD.deleteItem(id);
                refreshTable();
            } catch (NumberFormatException ex) {
                showError("ID must be a valid number.");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("ID (for update/delete):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        inputPanel.add(addButton, gbc);

        gbc.gridx = 1;
        inputPanel.add(updateButton, gbc);

        gbc.gridx = 2;
        inputPanel.add(deleteButton, gbc);

        // Table for displaying inventory
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity", "Description", "Created At", "Updated At"}, 0);
        table = new JTable(tableModel);
        refreshTable();

        // Adding components to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void refreshTable() {
        InventoryCRUD.fetchItems(tableModel);
    }

    private static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
