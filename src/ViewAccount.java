import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.proteanit.sql.DbUtils;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewAccount extends JInternalFrame {

    private static final long serialVersionUID = 1;

    Container con;
    Connection cn;
    Statement st;
    ResultSet rs;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewAccount frame = new ViewAccount();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    PreparedStatement pst;

    private JTextField txtSearch;
    private JTable table;

    private JComboBox cbUserType;
    public void dbOpen() {
        try {
            String url = "jdbc:mysql://localhost:3306/chupee";
            String user = "root";
            String password = "";

            cn = DriverManager.getConnection(url, user, password);
            st = cn.createStatement();
            rs = st.executeQuery("SELECT user_id, first_name, last_name, username, isAdmin FROM users");
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
            String qry = "SELECT user_id, first_name, last_name, username, isAdmin FROM users";
            PreparedStatement pst = cn.prepareStatement(qry);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                //cbUserType.getString("user_id"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Create the frame.
     */
    public ViewAccount() {

        setBounds(100, 100, 1150, 655);
        getContentPane().setLayout(null);

        txtSearch = new JTextField();
        txtSearch.setBounds(132, 23, 258, 34);
        getContentPane().add(txtSearch);
        txtSearch.setColumns(10);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                try {
                    dbOpen();
                    String qry = "SELECT user_id, first_name, last_name, username, isAdmin FROM users WHERE user_id = ? or first_name like '%" + txtSearch.getText() + "%' or last_name like '%" + txtSearch.getText() + "%' or username like '%" + txtSearch.getText() + "%'  ";
                    PreparedStatement pst = cn.prepareStatement(qry);
                    pst.setString(1, (String) cbUserType.getSelectedItem());
                    ResultSet rs = pst.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    while (rs.next()) {

                    }
                    dbClose();

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                try {
                    dbOpen();
                    String qry = "SELECT user_id, first_name, last_name, username, isAdmin FROM users WHERE user_id = ? or first_name like '%" + txtSearch.getText() + "%' or last_name like '%" + txtSearch.getText() + "%' or username like '%" + txtSearch.getText() + "%'  ";
                    PreparedStatement pst = cn.prepareStatement(qry);
                    pst.setString(1, (String) cbUserType.getSelectedItem());
                    ResultSet rs = pst.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));

                    while (rs.next()) {

                    }
                    dbClose();

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // TODO Auto-generated method stub
                try {
                    dbOpen();
                    String qry = "SELECT user_id, first_name, last_name, username, isAdmin FROM users WHERE user_id = ? or first_name like '%" + txtSearch.getText() + "%' or last_name like '%" + txtSearch.getText() + "%' or username like '%" + txtSearch.getText() + "%'  ";
                    PreparedStatement pst = cn.prepareStatement(qry);
                    pst.setString(1, (String) cbUserType.getSelectedItem());
                    ResultSet rs = pst.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    while (rs.next()) {

                    }
                    dbClose();

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        cbUserType = new JComboBox();
        cbUserType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    dbOpen();
                    String qry = "SELECT user_id, first_name, last_name, username, isAdmin FROM users WHERE user_id = ? or first_name like '%" + txtSearch.getText() + "%' or last_name like '%" + txtSearch.getText() + "%' or username like '%" + txtSearch.getText() + "%'  ";
                    PreparedStatement pst = cn.prepareStatement(qry);
                    pst.setString(1, (String) cbUserType.getSelectedItem());
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {

                    }
                    dbClose();

                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cbUserType.setBounds(418, 23, 87, 34);
        getContentPane().add(cbUserType);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 76, 1114, 457);
        getContentPane().add(scrollPane);

        table = new JTable();
        LoadTable();

        scrollPane.setViewportView(table);

        JButton delAcc_btn = new JButton("Delete Account");
        delAcc_btn.setBounds(355, 557, 151, 44);
        getContentPane().add(delAcc_btn);

        JButton addAcc_btn = new JButton("Add Account");
        addAcc_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AddAccount a = new AddAccount();
        		a.setVisible(true);
        	}
        });
        addAcc_btn.setBounds(580, 557, 151, 44);
        getContentPane().add(addAcc_btn);

    }
}