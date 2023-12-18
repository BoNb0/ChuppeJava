import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddAccount extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddAccount frame = new AddAccount();
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
	    private JTextField txtLn;
	    private JTextField txtFn;
	    private JTextField txtUn;
	    private JPasswordField txtpwd;
	
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
	
	/**
	 * Create the frame.
	 */
	public AddAccount() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		setBounds(100, 100, 245, 487);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Add Account");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNewLabel.setBounds(47, 11, 131, 53);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Last Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(29, 91, 78, 28);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("First Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(29, 149, 78, 28);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("User Name");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1.setBounds(29, 217, 78, 28);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Password");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1_1_1_1.setBounds(29, 286, 78, 28);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		txtLn = new JTextField();
		txtLn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtLn.setBounds(29, 118, 166, 20);
		contentPane.add(txtLn);
		txtLn.setColumns(10);
		
		txtFn = new JTextField();
		txtFn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtFn.setColumns(10);
		txtFn.setBounds(29, 176, 166, 20);
		contentPane.add(txtFn);
		
		txtUn = new JTextField();
		txtUn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtUn.setColumns(10);
		txtUn.setBounds(29, 245, 166, 20);
		contentPane.add(txtUn);
		
		txtpwd = new JPasswordField();
		txtpwd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtpwd.setColumns(10);
		txtpwd.setBounds(29, 315, 166, 20);
		contentPane.add(txtpwd);
		
		JButton btnNewButton = new JButton("Add Account");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				Connect();
				try {
					if (txtLn.getText().equals("") || txtFn.getText().equals("") || txtUn.getText().equals("") || txtpwd.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Complete all the fields", "Error", JOptionPane.ERROR_MESSAGE);
						 txtLn.requestFocusInWindow();
					} else {
							String Ln = txtLn.getText().trim();
							String Fn = txtFn.getText().trim();
							String Un = txtUn.getText().trim();
							String Pwd = txtpwd.getText().trim();
							//String admin = "1";
							
							 pst = con.prepareStatement("INSERT INTO `users`(`first_name`, `last_name`, `username`, `password`, `isAdmin`) VALUES(?,?,?,?,1)");
	                            pst.setString(1, Ln);
	                            pst.setString(2, Fn);
	                            pst.setString(3, Un);
	                            pst.setString(4, Pwd);
	                           // pst.setString(5, admin);
	                            int inserted = pst.executeUpdate();
	                            if (inserted > 0) {
	                            //pst.executeUpdate();
	                                JOptionPane.showMessageDialog(null, "ADD ACCOUNT SUCCESS!");
	                                txtLn.setText("");
	                                txtFn.setText("");
	                                txtUn.setText("");
	                                txtpwd.setText("");	   
                               
									ViewAccount.LoadTable();
								
	                            }
							
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(61, 373, 108, 37);
		contentPane.add(btnNewButton);
	}
}
