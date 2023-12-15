import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ProductViewer extends JInternalFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1;
    private JTable table;
    private JTextField productIdField, productNameField, productDescriptionField, priceField, quantityField;
    private JLabel imageLabel;

    public ProductViewer() {
        super("Product Viewer");
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
    	BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
    	bui.setNorthPane(null);
        // Initialize components
        productIdField = new JTextField();
        productNameField = new JTextField();
        productDescriptionField = new JTextField();
        priceField = new JTextField();
        quantityField = new JTextField();
        imageLabel = new JLabel();

        // Set up the table
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        // Set up the table columns
        model.addColumn("Product ID");
        model.addColumn("Product Name");
        model.addColumn("Product Description");
        model.addColumn("Price");
        model.addColumn("Quantity");

        // Set up the frame layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(7, 2));
        infoPanel.add(new JLabel("Product ID:"));
        infoPanel.add(productIdField);
        infoPanel.add(new JLabel("Product Name:"));
        infoPanel.add(productNameField);
        infoPanel.add(new JLabel("Product Description:"));
        infoPanel.add(productDescriptionField);
        infoPanel.add(new JLabel("Price:"));
        infoPanel.add(priceField);
        infoPanel.add(new JLabel("Quantity:"));
        infoPanel.add(quantityField);
        infoPanel.add(new JLabel("Image:"));
        infoPanel.add(imageLabel);

        getContentPane().add(infoPanel, BorderLayout.SOUTH);

        // Set up the table selection listener
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    displayProductInfo(selectedRow);
                }
            }
        });

        // Set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        //setLocationRelativeTo(null);
        setVisible(true);

        // Populate the table with data from the database
        populateTable();
    }

    private void populateTable() {
        // JDBC connection and query to retrieve data
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/choppee", "root", "")) {
            String query = "SELECT * FROM tbl_products";
            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Clear existing rows

                while (resultSet.next()) {
                    Vector < Object > row = new Vector < > ();
                    row.add(resultSet.getInt("product_id"));
                    row.add(resultSet.getString("product_name"));
                    row.add(resultSet.getString("product_description"));
                    row.add(resultSet.getDouble("price"));
                    row.add(resultSet.getInt("quantity"));
                    model.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayProductInfo(int selectedRow) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/choppee", "root", "")) {
                String query = "SELECT * FROM tbl_products WHERE product_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    int productId = (int) table.getValueAt(selectedRow, 0);
                    preparedStatement.setInt(1, productId);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            productIdField.setText(String.valueOf(productId));
                            productNameField.setText(resultSet.getString("product_name"));
                            productDescriptionField.setText(resultSet.getString("product_description"));
                            priceField.setText(String.valueOf(resultSet.getDouble("price")));
                            quantityField.setText(String.valueOf(resultSet.getInt("quantity")));

                            // Display image in the JLabel
                            Blob imageBlob = resultSet.getBlob("image");
                            if (imageBlob != null) {
                                byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                                ImageIcon imageIcon = new ImageIcon(imageData);
                                imageLabel.setIcon(imageIcon);
                            } else {
                                // Clear the image if no image data is present
                                imageLabel.setIcon(null);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductViewer());
    }
}