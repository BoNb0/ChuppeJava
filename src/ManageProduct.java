import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.Blob;

import net.proteanit.sql.DbUtils;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.DefaultComboBoxModel;

public class ManageProduct extends JInternalFrame {

  private static final long serialVersionUID = 1;
  private JPanel contentPane;
  private JLabel lblimage;
  private JTextField txtPname;
  private JTextArea txtPdesc;
  private JTextField txtPprice;
  private JTextField txtqty;
  private JTextField txtSearch;
  private JTextField txtid;
  private ImageIcon format = null;
  private JComboBox cbSearch;

  private JComboBox < String > cbGetbyName;

  File f = null;
  String path = null;
  String fname = null;
  int s = 0;
  byte[] pimage = null;

  Container con;
  Connection cn;
  Statement st;
  ResultSet rs;
  private JTable table;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          ManageProduct frame = new ManageProduct();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */

  public void dbOpen() {
    try {
      String url = "jdbc:mysql://localhost:3306/chupee";
      String user = "root";
      String password = "";

      cn = DriverManager.getConnection(url, user, password);
      st = cn.createStatement();
      rs = st.executeQuery("select * from products");
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void dbClose() {
    try {
      rs.close();
      st.close();
      cn.close();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void LoadTable() {
    try {
      dbOpen();
      table.setModel(DbUtils.resultSetToTableModel(rs));
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void fillcb() {
    try {
      dbOpen();
      String qry = "SELECT * FROM products";
      PreparedStatement pst = cn.prepareStatement(qry);
      ResultSet rs = pst.executeQuery();

      while (rs.next()) {
        cbGetbyName.addItem(rs.getString("product_id"));
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public ManageProduct() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1104, 668);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblimage = new JLabel("");
    lblimage.setBorder(new LineBorder(Color.BLACK));
    lblimage.setBounds(25, 69, 282, 172);
    contentPane.add(lblimage);

    JLabel lblNewLabel = new JLabel("PRODUCT NAME:");
    lblNewLabel.setForeground(Color.BLACK);
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblNewLabel.setBounds(25, 325, 282, 14);
    contentPane.add(lblNewLabel);

    txtPname = new JTextField();
    txtPname.setFont(new Font("Arial", Font.PLAIN, 13));
    txtPname.setColumns(10);
    txtPname.setBounds(25, 340, 282, 29);
    contentPane.add(txtPname);

    JLabel lbldesc = new JLabel("PRODUCT DESCRIPTION:");
    lbldesc.setForeground(Color.BLACK);
    lbldesc.setFont(new Font("Tahoma", Font.BOLD, 11));
    lbldesc.setBounds(25, 373, 282, 14);
    contentPane.add(lbldesc);

    txtPdesc = new JTextArea();
    txtPdesc.setBorder(new LineBorder(new Color(0, 0, 0), 2));
    txtPdesc.setLineWrap(true);
    txtPdesc.setFont(new Font("Arial", Font.PLAIN, 13));
    txtPdesc.setColumns(10);
    txtPdesc.setBounds(25, 387, 282, 64);
    contentPane.add(txtPdesc);

    JLabel lblProductPrice = new JLabel("PRODUCT PRICE:");
    lblProductPrice.setForeground(Color.BLACK);
    lblProductPrice.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblProductPrice.setBounds(25, 458, 282, 14);
    contentPane.add(lblProductPrice);

    txtPprice = new JTextField();
    txtPprice.setHorizontalAlignment(SwingConstants.LEFT);
    txtPprice.setFont(new Font("Arial", Font.PLAIN, 13));
    txtPprice.setColumns(10);
    txtPprice.setBounds(25, 472, 282, 29);
    contentPane.add(txtPprice);

    JLabel lblQuantity = new JLabel("QUANTITY:");
    lblQuantity.setForeground(Color.BLACK);
    lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblQuantity.setBounds(25, 507, 282, 14);
    contentPane.add(lblQuantity);

    txtqty = new JTextField();
    txtqty.setColumns(10);
    txtqty.setBounds(25, 521, 282, 29);
    contentPane.add(txtqty);

    JButton btnEdit = new JButton("UPDATE");
    btnEdit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  try {
    	      int lg = (int) f.length();
    	      if (lg > 102400) {
    	          JOptionPane.showMessageDialog(null, "The file is to large, The File must be less than 100Kb", "Error", JOptionPane.ERROR_MESSAGE);
    	      } else {
    	          dbOpen();

    	          String id = txtid.getText();
    	          String PN = txtPname.getText().trim();
    	          String PD = txtPdesc.getText().trim();
    	          String PP = txtPprice.getText().trim();
    	          String qty = txtqty.getText().trim();

    	          String updateQuery = "UPDATE products SET product_id = ?, product_name = ?, product_description = ?, price = ?, quantity = ?, image = ? WHERE product_id = '" + id + "' ";
    	          PreparedStatement pst = cn.prepareStatement(updateQuery);

    	          File imageFile = new File(path);
    	          try (FileInputStream fis = new FileInputStream(imageFile)) {
    	              byte[] imageData = new byte[(int) imageFile.length()];
    	              fis.read(imageData);
    	              pst.setString(1, id);
    	              pst.setString(2, PN);
    	              
    	              pst.setString(3, PD);
    	              
    	              pst.setString(4, PP);
    	              
    	              pst.setString(5, qty);
    	              pst.setBytes(6, imageData);
    	              

    	              pst.executeUpdate();
    	              JOptionPane.showMessageDialog(null, "Update Product Success", "Info", JOptionPane.INFORMATION_MESSAGE);
    	              lblimage.setIcon(new ImageIcon(""));
    	              txtid.setText("");
    	              txtPname.setText("");
    	              txtPdesc.setText("");
    	              txtPprice.setText("");
    	              txtqty.setText("");
    	              LoadTable();

    	          } catch (IOException e1) {
    	              JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	          }

    	      }
    	  } catch (SQLException ex) {
    	      JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	  }
    	  }

    	  });

    btnEdit.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        btnEdit.setBackground(new Color(30, 144, 255));
      }
      @Override
      public void mouseExited(MouseEvent e) {
        btnEdit.setBackground(Color.WHITE);
        btnEdit.setForeground(Color.BLACK);
      }
      @Override
      public void mouseClicked(MouseEvent e) {
        btnEdit.setBackground(Color.BLACK);
        btnEdit.setForeground(Color.WHITE);
      }
    });
    btnEdit.setFont(new Font("Tahoma", Font.BOLD, 13));
    btnEdit.setBorder(new LineBorder(new Color(0, 0, 0), 2));
    btnEdit.setBackground(Color.WHITE);
    btnEdit.setBounds(25, 572, 139, 47);
    contentPane.add(btnEdit);

    JButton btnDelete = new JButton("DELETE");
    btnDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        try {
          dbOpen();

          String id = txtid.getText();
          String updateQuery = "delete from products where product_id = ?";
          PreparedStatement pst = cn.prepareStatement(updateQuery);
          pst.setString(1, id);
          pst.executeUpdate();

          JOptionPane.showMessageDialog(null, "Delete Product Success", "Info", JOptionPane.INFORMATION_MESSAGE);
          lblimage.setIcon(new ImageIcon(""));
          txtid.setText("");
          txtPname.setText("");
          txtPdesc.setText("");
          txtPprice.setText("");
          txtqty.setText("");
          LoadTable();

        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

      }
    });

    btnDelete.setFont(new Font("Tahoma", Font.BOLD, 13));
    btnDelete.setBorder(new LineBorder(new Color(0, 0, 0), 2));
    btnDelete.setBackground(Color.WHITE);
    btnDelete.setBounds(168, 572, 139, 47);
    contentPane.add(btnDelete);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(318, 69, 740, 556);
    contentPane.add(scrollPane);
    
    table = new JTable();
    LoadTable();
	
	
    table.addMouseListener(new MouseAdapter() {
    	@Override
    	public void mouseClicked(MouseEvent e) {
    		 try {
    	          dbOpen();
    	          int row = table.getSelectedRow();
    	          String productId = (table.getModel().getValueAt(row, 0)).toString();

    	          String query = "SELECT * FROM products WHERE product_id = '" + productId + "'";
    	          st = cn.prepareStatement(query);
    	          rs = st.executeQuery(query);

    	          while (rs.next()) {

    	            txtid.setText(String.valueOf(rs.getInt("product_id")));
    	            txtPname.setText(rs.getString("product_name"));
    	            txtPdesc.setText(rs.getString("product_description"));
    	            txtPprice.setText(String.valueOf(rs.getDouble("price")));
    	            txtqty.setText(String.valueOf(rs.getInt("quantity")));

    	            byte[] imageData = rs.getBytes("image");
    	            format = new ImageIcon(imageData);
    	            Image mm = format.getImage();
    	            Image img2 = mm.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
    	            ImageIcon image = new ImageIcon(img2);
    	            lblimage.setIcon(image);
    	          }
    	          dbClose();

    	        } catch (Exception e1) {
    	          JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	        }
    	      }
    	    });
    scrollPane.setViewportView(table);
    

    JLabel lblNewLabel_1 = new JLabel("Search By :");
    lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
    lblNewLabel_1.setBounds(479, 27, 93, 29);
    contentPane.add(lblNewLabel_1);

    cbSearch = new JComboBox();
    cbSearch.setModel(new DefaultComboBoxModel(new String[] {
      "All",
      "product_id",
      "product_name"
    }));
    cbSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

      }
    });

    cbSearch.setBounds(572, 29, 119, 29);
    contentPane.add(cbSearch);

    txtSearch = new JTextField();

    txtSearch.getDocument().addDocumentListener(new DocumentListener() {

      @Override
      public void insertUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        try {

          if (cbSearch.getSelectedItem().equals("product_id")) {
            dbOpen();
            String selecttion = (String) cbSearch.getSelectedItem();
            String query = "SELECT * FROM products WHERE " + selecttion + " = ? ";
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, txtSearch.getText());
            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            while (rs.next()) {

            }
            pst.close();
          } else if (cbSearch.getSelectedItem().equals("product_name")) {
            //String selecttion = (String) cbSearch.getSelectedItem();
            String query = "SELECT * FROM products WHERE  product_name like '%" + txtSearch.getText() + "%' ";

            //String query = "SELECT * FROM products WHERE product_name LIKE '%"+ selecttion+ "%'";
            PreparedStatement pst = cn.prepareStatement(query);
            //pst.setString(1, txtSearch.getText());
            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            while (rs.next()) {

            }
            pst.close();
          } else if (cbSearch.getSelectedItem().equals("All")) {

            String query = "select * from products";

            PreparedStatement pst = cn.prepareStatement(query);

            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            while (rs.next()) {

            }
            pst.close();
          }

        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        try {

          if (cbSearch.getSelectedItem().equals("product_id")) {
            dbOpen();
            String selecttion = (String) cbSearch.getSelectedItem();
            String query = "SELECT * FROM products WHERE " + selecttion + " = ? ";
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, txtSearch.getText());
            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            while (rs.next()) {

            }
            pst.close();
          } else if (cbSearch.getSelectedItem().equals("product_name")) {
            //String selecttion = (String) cbSearch.getSelectedItem();
            String query = "SELECT * FROM products WHERE  product_name like '%" + txtSearch.getText() + "%' ";

            //String query = "SELECT * FROM products WHERE product_name LIKE '%"+ selecttion+ "%'";
            PreparedStatement pst = cn.prepareStatement(query);
            //pst.setString(1, txtSearch.getText());
            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            while (rs.next()) {

            }
            pst.close();
          } else if (cbSearch.getSelectedItem().equals("All")) {

            String query = "select * from products";

            PreparedStatement pst = cn.prepareStatement(query);

            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            while (rs.next()) {

            }
            pst.close();
          }

        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

        try {

          if (cbSearch.getSelectedItem().equals("product_id")) {
            dbOpen();
            String selecttion = (String) cbSearch.getSelectedItem();
            String query = "SELECT * FROM products WHERE " + selecttion + " = ? ";
            PreparedStatement pst = cn.prepareStatement(query);
            pst.setString(1, txtSearch.getText());
            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            while (rs.next()) {

            }
            pst.close();
          } else if (cbSearch.getSelectedItem().equals("product_name")) {
            //String selecttion = (String) cbSearch.getSelectedItem();
            String query = "SELECT * FROM products WHERE  product_name like '%" + txtSearch.getText() + "%' ";

            //String query = "SELECT * FROM products WHERE product_name LIKE '%"+ selecttion+ "%'";
            PreparedStatement pst = cn.prepareStatement(query);
            //pst.setString(1, txtSearch.getText());
            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            while (rs.next()) {

            }
            pst.close();
          } else if (cbSearch.getSelectedItem().equals("All")) {

            String query = "select * from products";

            PreparedStatement pst = cn.prepareStatement(query);

            ResultSet rs = pst.executeQuery();

            table.setModel(DbUtils.resultSetToTableModel(rs));

            while (rs.next()) {

            }
            pst.close();
          }

        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }

    });

    txtSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
    txtSearch.setBounds(701, 27, 249, 31);
    contentPane.add(txtSearch);
    txtSearch.setColumns(10);

    txtid = new JTextField();
    txtid.setFont(new Font("Arial", Font.PLAIN, 13));
    txtid.setBounds(25, 286, 282, 29);
    txtid.setEditable(false);
    contentPane.add(txtid);
    txtid.setColumns(10);

    JLabel lblProductId = new JLabel("PRODUCT ID:");
    lblProductId.setForeground(Color.BLACK);
    lblProductId.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblProductId.setBounds(25, 273, 282, 14);
    contentPane.add(lblProductId);

    JButton btnNewButton = new JButton("Browse Image");
    btnNewButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fnwf = new FileNameExtensionFilter("PNG JPG AND JPEG", "png", "jpg", "jpeg");
        fileChooser.addChoosableFileFilter(fnwf);
        int load = fileChooser.showOpenDialog(null);

        try {
			if (load == fileChooser.APPROVE_OPTION) {
			  f = fileChooser.getSelectedFile();
			  path = f.getAbsolutePath();

			  ImageIcon ii = new ImageIcon(path);
			  Image img = ii.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
			  lblimage.setIcon(new ImageIcon(img));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      }
    });
    btnNewButton.setBounds(25, 239, 119, 23);
    contentPane.add(btnNewButton);

    JLabel lblNewLabel_2 = new JLabel("(Maximum File Size : 100Kb)");
    lblNewLabel_2.setBounds(149, 239, 179, 23);
    contentPane.add(lblNewLabel_2);

    cbGetbyName = new JComboBox();
    cbGetbyName.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          dbOpen();
          String qry = "SELECT * FROM products WHERE product_id = ?";
          PreparedStatement pst = cn.prepareStatement(qry);
          pst.setString(1, (String) cbGetbyName.getSelectedItem());
          ResultSet rs = pst.executeQuery();

          while (rs.next()) {

            txtid.setText(String.valueOf(rs.getInt("product_id")));
            txtPname.setText(rs.getString("product_name"));
            txtPdesc.setText(rs.getString("product_description"));
            txtPprice.setText(String.valueOf(rs.getDouble("price")));
            txtqty.setText(String.valueOf(rs.getInt("quantity")));

            byte[] imageData = rs.getBytes("image");
            format = new ImageIcon(imageData);
            Image mm = format.getImage();
            Image img2 = mm.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            ImageIcon image = new ImageIcon(img2);
            lblimage.setIcon(image);
          }
          dbClose();

        } catch (Exception e1) {
          JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    cbGetbyName.setBounds(25, 31, 282, 29);
    contentPane.add(cbGetbyName);

    JLabel lblNewLabel_3 = new JLabel("Select Item by Product id :");
    lblNewLabel_3.setBounds(25, 11, 282, 14);
    contentPane.add(lblNewLabel_3);
    fillcb();
  }
}