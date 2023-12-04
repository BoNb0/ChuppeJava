import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class frmLogin extends JFrame {

    //private Image marketLogo = new ImageIcon(frmLogin.class.getResource("res/market.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

    private static final long serialVersionUID = 1;
    private JPanel contentPane;
    private JTextField txtUsername;

    Container con;
    Connection cn;
    Statement st;
    ResultSet rs;
    private JPasswordField txtpassword;
    protected Component frmLogin;
    protected Component frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frmLogin frame = new frmLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void dbOpen() {

        try {
            String url = "jdbc:mysql://localhost:3306/choppee";
            String user = "root";
            String password = "";

            cn = DriverManager.getConnection(url, user, password);
            st = cn.createStatement();
            rs = st.executeQuery("select * from tbl_users");
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

    /**
     * Create the frame.
     */
    public frmLogin() {
            setUndecorated(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(100, 100, 540, 422);
            setLocationRelativeTo(null);
            contentPane = new JPanel();
            contentPane.setBackground(Color.WHITE);
            contentPane.setBorder(new LineBorder(Color.BLACK, 2));

            setContentPane(contentPane);
            contentPane.setLayout(null);

            txtUsername = new JTextField();
            txtUsername.setBorder(new LineBorder(Color.BLACK, 2));
            txtUsername.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (txtUsername.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Enter Username", "Error", JOptionPane.ERROR_MESSAGE);
                        txtUsername.requestFocusInWindow();
                    } else {
                        txtpassword.requestFocusInWindow();
                    }
                }
            });
            txtUsername.setText("Username");
            txtUsername.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (txtUsername.getText().equals("Username")) {
                        txtUsername.setText("");
                    } else {
                        txtUsername.selectAll();
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (txtUsername.getText().equals("")) {
                        txtUsername.setText("Username");
                    }
                }
            });
            txtUsername.setFont(new Font("Arial", Font.BOLD, 15));
            txtUsername.setBounds(135, 196, 276, 41);
            contentPane.add(txtUsername);
            txtUsername.setColumns(10);

            JButton btnLogin = new JButton("LOGIN");
            btnLogin.setForeground(Color.WHITE);
            btnLogin.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dbOpen();
                    if (txtUsername.getText().equals("") || txtUsername.getText().equals("Username") ||
                        txtpassword.getText().equals("") || txtpassword.getText().equals("Password")) {
                        JOptionPane.showMessageDialog(null, "Complete all the fields", "Error", JOptionPane.INFORMATION_MESSAGE);
                        txtUsername.requestFocusInWindow();
                    } else {
                        try {
                            String uname = txtUsername.getText();
                            String pword = new String(txtpassword.getPassword());

                            rs = st.executeQuery("SELECT count(*) as me FROM tbl_users WHERE username='" + uname + "'");
                            rs.next();
                            if (Integer.parseInt(rs.getString("me")) == 0) {
                                JOptionPane.showMessageDialog(null, "User Not Found !", "Warning", JOptionPane.WARNING_MESSAGE);
                                txtUsername.requestFocusInWindow();
                            } else {
                                rs = st.executeQuery("SELECT password FROM tbl_users WHERE username='" + uname + "'");
                                rs.next();
                                if (!rs.getString("password").equals(pword)) {
                                    JOptionPane.showMessageDialog(frame, "Incorrect Password Please Try Again !", "Warning", JOptionPane.WARNING_MESSAGE);
                                    txtpassword.requestFocusInWindow();
                                } else {
                                    JOptionPane.showMessageDialog(frmLogin, "Login Success Welcome, " + uname + ". ", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                                    AddProduct m = new AddProduct();
                                    m.setVisible(true);
                                    frmLogin.this.dispose();
                                    dbClose();
                                }
                            }
                        } catch (Exception i) {
                            JOptionPane.showMessageDialog(null, i.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
            btnLogin.setBackground(Color.BLACK);
            btnLogin.setBorder(new LineBorder(Color.WHITE, 2));
            btnLogin.setBounds(135, 324, 276, 56);
            contentPane.add(btnLogin);

            JLabel lblNewLabel = new JLabel("CHUPPEE");
            lblNewLabel.setForeground(Color.BLACK);
            lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
            lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
            lblNewLabel.setBounds(135, 78, 276, 63);
            contentPane.add(lblNewLabel);

            JLabel lblx = new JLabel("X");
            lblx.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this application?", "Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
                        frmLogin.this.dispose();
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    lblx.setForeground(Color.RED);
                }
                public void mouseExited(MouseEvent e) {
                    lblx.setForeground(Color.WHITE);
                }
            });
            lblx.setForeground(Color.BLACK);
            lblx.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            lblx.setHorizontalAlignment(SwingConstants.CENTER);
            lblx.setBounds(507, 11, 23, 21);
            contentPane.add(lblx);

            txtpassword = new JPasswordField();
            txtpassword.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (txtpassword.getText().equals("Password")) {
                        txtpassword.setText("");
                        txtpassword.setEchoChar('●');
                    } else {
                        txtpassword.selectAll();
                    }
                }
                @Override
                public void focusLost(FocusEvent e) {
                    if (txtpassword.getText().equals("")) {
                        txtpassword.setText("Password");
                        txtpassword.setEchoChar((char) 0);
                    }
                }
            });
		txtpassword.setText("Password");
		txtpassword.setFont(new Font("Arial", Font.BOLD, 15));
		txtpassword.setEchoChar(' ');
		txtpassword.setBorder(new LineBorder(Color.BLACK, 2));
		txtpassword.setBounds(135, 248, 276, 41);
		contentPane.add(txtpassword);
		
	}
}
