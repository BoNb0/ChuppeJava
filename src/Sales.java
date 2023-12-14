import java.awt.Container;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Sales extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sales frame = new Sales();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Container con;
	Connection cn;
	Statement st;
	ResultSet rs;
	private JTable table;
	private JTable table_1;
	private JTextField txtSearch;
	
	public void dbOpen1() {
		try {	
			String url="jdbc:mysql://localhost:3306/chupee";
			String user="root";
			String password="";
				
			cn=DriverManager.getConnection(url,user,password);
			st=cn.createStatement();
			rs = st.executeQuery("select * from sold_products");
			
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void dbOpen() {
		try {	
			String url="jdbc:mysql://localhost:3306/chupee";
			String user="root";
			String password="";
				
			cn=DriverManager.getConnection(url,user,password);
			st=cn.createStatement();
			rs = st.executeQuery("select * from orders");
			
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void dbClose() {
		try {
			rs.close();
			st.close();
			cn.close();
		}
		catch (Exception e) {
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

	public void LoadTable1() {
		try {
			dbOpen();
			table_1.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void Reset() {
	  
	}

	public void fillcb() {
		try {
			dbOpen();
			String qry = "SELECT * FROM products";
			PreparedStatement pst = cn.prepareStatement(qry);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				//cbGetbyName.addItem(rs.getString("product_id"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		}
	
	
	/**
	 * Create the frame.
	 */
	public Sales() {
		super("Sales");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 setBounds(100, 100, 1150, 655);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 680, 447);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					dbOpen1();
					 int row = table.getSelectedRow();
	    	          String orderId = (table.getModel().getValueAt(row, 0)).toString();
					String qry = "select * from sold_products where order_id = '"+orderId+"' ";
					st = cn.prepareStatement(qry);
	    	          rs = st.executeQuery(qry);
	    	          table_1.setModel(DbUtils.resultSetToTableModel(rs));
	    	          
	    	          dbClose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		scrollPane.setViewportView(table);
		LoadTable();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(700, 45, 424, 447);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				 try {
					dbOpen();
					String qry = "SELECT * FROM orders WHERE customer_name LIKE ? or order_id like ?";
					PreparedStatement pst = cn.prepareStatement(qry);
					pst.setString(1, "%" +txtSearch.getText()+ "%");
					pst.setString(2, "%" +txtSearch.getText()+ "%");
					ResultSet rs = pst.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					while(rs.next()) {
					}
					dbClose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		txtSearch.setBounds(97, 11, 169, 20);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		LoadTable1();
	}

}

