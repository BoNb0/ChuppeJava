import java.awt.EventQueue;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Window.Type;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class AddProduct extends JInternalFrame {

    private static final long serialVersionUID = 1;
    private JPanel contentPane;
    private JTextField txtimagepath;
    private JTextField txtPname;
    private JTextArea txtPdesc;
    private JTextField txtPprice;
    private ImageIcon format = null;
    private JTextField txtqty;

    private String colNames[] = {
        "Account Id",
        "First Name",
        "Last Name",
        "Username",
        "Password"
    };

    File f = null;
    String path = null;
    String fname = null;
    int s = 0;
    byte[] pimage = null;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddProduct frame = new AddProduct();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
	private JLabel lblNewLabel_3;
    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/chupee", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public < InputStream > AddProduct() {
    	super("Add Product");
        //setUndecorated(true);
    	
    	this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
    	BasicInternalFrameUI bui = (BasicInternalFrameUI) this.getUI();
    	bui.setNorthPane(null);
    	
        Connect();
        //setType(Type.POPUP);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1068, 642);
        //setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setForeground(Color.BLACK);
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblimage = new JLabel("");
        lblimage.setBorder(new LineBorder(Color.BLACK));
        lblimage.setBounds(50, 70, 476, 390);
        contentPane.add(lblimage);

        JButton btnbrowse = new JButton("Browse");
       
        btnbrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter fnwf = new FileNameExtensionFilter("PNG JPG AND JPEG", "png", "jpg", "jpeg");
                fileChooser.addChoosableFileFilter(fnwf);
                int load = fileChooser.showOpenDialog(null);

                try {
					if (load == fileChooser.APPROVE_OPTION) {
					    f = fileChooser.getSelectedFile();
					    path = f.getAbsolutePath();

					    txtimagepath.setText(path);
					    ImageIcon ii = new ImageIcon(path);
					    Image img = ii.getImage().getScaledInstance(476, 390, Image.SCALE_SMOOTH);
					    lblimage.setIcon(new ImageIcon(img));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
              
			}
                
            
        });
        btnbrowse.setBounds(50, 471, 101, 34);
        contentPane.add(btnbrowse);

        JButton btnSave = new JButton("SAVE");
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSave.setBackground(Color.BLACK);
                btnSave.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnSave.setBackground(Color.WHITE);
                btnSave.setForeground(Color.BLACK);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                btnSave.setBackground(Color.GRAY);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                btnSave.setBackground(Color.GRAY);
            }
        });
        btnSave.setBackground(Color.WHITE);
        btnSave.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnSave.addActionListener(new ActionListener() {
            @SuppressWarnings("unlikely-arg-type")
            public void actionPerformed(ActionEvent e) {

                if (txtimagepath.getText().equals("") || txtPname.getText().equals("") || txtPdesc.getText().equals("") ||
                    txtPprice.getText().equals("") || txtqty.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Complete all the fields", "Error", JOptionPane.INFORMATION_MESSAGE);
                    txtPname.requestFocus();
                } else {
                    try {
                        int lg = (int) f.length();
                        if (lg > 102400) {
                            JOptionPane.showMessageDialog(null, "The file is to large, The File must be less than 100Kb", "Error", JOptionPane.ERROR_MESSAGE);
                            txtimagepath.setText("");
                            lblimage.setIcon(new ImageIcon(""));
                            
                        } else {
                            String PN = txtPname.getText().trim();
                            String PD = txtPdesc.getText().trim();
                            String PP = txtPprice.getText().trim();
                            String qty = txtqty.getText().trim();

                            System.out.println("Product Name - " + PN);
                            System.out.println("Product Description - " + PD);
                            System.out.println("Product Price - " + PP);
                            System.out.println("Quantity - " + qty);
                            System.out.println("Image Path - " + path);
                            System.out.println("Image Name - " + f.getName());
                            System.out.println("Image Size - " + lg);

                            File f = new File(path);

                            FileInputStream is = new FileInputStream(f);

                            pst = con.prepareStatement("INSERT INTO `products`(`product_name`, `product_description`, `Price`, `quantity`, `image`) VALUES(?,?,?,?,?)");
                            pst.setString(1, PN);
                            pst.setString(2, PD);
                            pst.setString(3, PP);
                            pst.setString(4, qty);
                            pst.setBlob(5, is);
                            int inserted = pst.executeUpdate();
                            if (inserted > 0) {
                                JOptionPane.showMessageDialog(null, "ADD SUCCESS!");
                                txtimagepath.setText("");
                                lblimage.setIcon(new ImageIcon(""));
                                txtPname.setText("");
                                txtPdesc.setText("");
                                txtPprice.setText("");
                                txtqty.setText("");
                            }

                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnSave.setBounds(558, 413, 414, 92);
        contentPane.add(btnSave);
        txtimagepath = new JTextField();
        
        		     		
        txtimagepath.setBounds(153, 471, 373, 34);
        contentPane.add(txtimagepath);
        txtimagepath.setColumns(10);

        txtPname = new JTextField();
        txtPname.setFont(new Font("Arial", Font.PLAIN, 13));
        txtPname.setBounds(558, 85, 414, 29);
        contentPane.add(txtPname);
        txtPname.setColumns(10);

        JLabel lblNewLabel = new JLabel("PRODUCT NAME:");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel.setBounds(558, 70, 261, 14);
        contentPane.add(lblNewLabel);

        JLabel lbldesc = new JLabel("PRODUCT DESCRIPTION:");
        lbldesc.setForeground(Color.BLACK);
        lbldesc.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbldesc.setBounds(558, 133, 261, 14);
        contentPane.add(lbldesc);

        txtPdesc = new JTextArea();
        txtPdesc.setWrapStyleWord(true);
        txtPdesc.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        txtPdesc.setLineWrap(true);
        txtPdesc.setFont(new Font("Arial", Font.PLAIN, 13));
        txtPdesc.setColumns(10);
        txtPdesc.setBounds(558, 147, 414, 132);
        contentPane.add(txtPdesc);

        JLabel lblProductPrice = new JLabel("PRODUCT PRICE:");
        lblProductPrice.setForeground(Color.BLACK);
        lblProductPrice.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblProductPrice.setBounds(558, 290, 261, 14);
        contentPane.add(lblProductPrice);

        txtPprice = new JTextField();
        txtPprice.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isLetter(c)) {
                    JOptionPane.showMessageDialog(null, "Number Only");
                    txtPprice.setText("");
                }
            }
        });
        txtPprice.setHorizontalAlignment(SwingConstants.LEFT);
        txtPprice.setFont(new Font("Arial", Font.PLAIN, 13));
        txtPprice.setColumns(10);
        txtPprice.setBounds(558, 304, 414, 29);
        contentPane.add(txtPprice);

        JLabel lblAddProduct = new JLabel("ADD PRODUCT");
        lblAddProduct.setForeground(Color.BLACK);
        lblAddProduct.setBackground(Color.BLACK);
        lblAddProduct.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblAddProduct.setBounds(520, 12, 198, 47);
        contentPane.add(lblAddProduct);

        JLabel lblNewLabel_1 = new JLabel("PRODUCT IMAGE");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setBounds(229, 45, 122, 14);
        contentPane.add(lblNewLabel_1);

        txtqty = new JTextField();
        txtqty.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isLetter(c)) {
                    JOptionPane.showMessageDialog(null, "Please Enter a Number Only");
                    txtqty.setText("");
                }
            }
        });
        txtqty.setBounds(558, 363, 414, 29);
        contentPane.add(txtqty);
        txtqty.setColumns(10);

        JLabel lblQuantity = new JLabel("QUANTITY:");
        lblQuantity.setForeground(Color.BLACK);
        lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblQuantity.setBounds(558, 349, 261, 14);
        contentPane.add(lblQuantity);

        JLabel lblNewLabel_2 = new JLabel("(Maximum File Size : 100Kb)");
        lblNewLabel_2.setBounds(50, 510, 282, 14);
        contentPane.add(lblNewLabel_2);
        
        lblNewLabel_3 = new JLabel("The Maximum file size is 100kb, please find a lower size file");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblNewLabel_3.setBounds(10, 524, 652, 34);
        
       
    }
}