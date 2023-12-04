import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;

public class Main extends JFrame {

    private static final long serialVersionUID = 1;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //making panels needs switching as a global variable
    private JLayeredPane layeredMainPane;
    private JPanel accPanel;
    private JPanel prodPanel;
    private JPanel salesPanel;
    private JPanel frontPanel;
    private JButton salesRep_btn;
    private JPanel salesRepPanel;
    private JButton manageProducts_btn;

    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1366, 768);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panelSideBar = new JPanel();
        panelSideBar.setForeground(Color.GRAY);
        panelSideBar.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelSideBar.setBackground(Color.GRAY);
        contentPane.add(panelSideBar, BorderLayout.WEST);
        panelSideBar.setPreferredSize(new Dimension(200, 200));
        panelSideBar.setLayout(null);

        JButton viewAccount_btn = new JButton("View Account");
        viewAccount_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layeredMainPane.removeAll();
                ViewAccount Mp = new ViewAccount();
                layeredMainPane.add(Mp).setVisible(true);
            }
        });
        viewAccount_btn.setBounds(0, 155, 200, 74);
        panelSideBar.add(viewAccount_btn);

        JButton Products_btn = new JButton("Add Products");
        Products_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layeredMainPane.removeAll();
                AddProduct Mp = new AddProduct();
                layeredMainPane.add(Mp).setVisible(true);
            }
        });
        Products_btn.setBounds(0, 240, 200, 74);
        panelSideBar.add(Products_btn);

        JButton sales_btn = new JButton("Sales");
        sales_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchScreen(salesPanel);
            }
        });
        sales_btn.setBounds(0, 622, 200, 74);
        panelSideBar.add(sales_btn);

        JButton front_btn = new JButton("Front");
        front_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchScreen(frontPanel);
            }
        });
        front_btn.setBounds(0, 70, 200, 74);
        panelSideBar.add(front_btn);

        salesRep_btn = new JButton("Sales Report");
        salesRep_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchScreen(salesRepPanel);
            }
        });
        salesRep_btn.setBounds(0, 537, 200, 74);
        panelSideBar.add(salesRep_btn);

        manageProducts_btn = new JButton("Manage Product");
        manageProducts_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layeredMainPane.removeAll();
                ManageProduct Mp = new ManageProduct();
                layeredMainPane.add(Mp).setVisible(true);

            }
        });
        manageProducts_btn.setBounds(0, 325, 200, 74);
        panelSideBar.add(manageProducts_btn);
        
        JButton viewProducts_btn = new JButton("View Products");
        viewProducts_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		layeredMainPane.removeAll();
                ProductViewer Mp = new ProductViewer();
                layeredMainPane.add(Mp).setVisible(true);
        	}
        });
        viewProducts_btn.setBounds(0, 410, 200, 74);
        panelSideBar.add(viewProducts_btn);

        layeredMainPane = new JLayeredPane();
        contentPane.add(layeredMainPane, BorderLayout.CENTER);
        layeredMainPane.setLayout(new CardLayout(0, 0));

        frontPanel = new JPanel();
        layeredMainPane.setLayer(frontPanel, 1);
        frontPanel.setBackground(new Color(0, 191, 255));
        layeredMainPane.add(frontPanel, "name_909208434163500");

        accPanel = new JPanel();
        layeredMainPane.setLayer(accPanel, 2);
        accPanel.setBackground(new Color(112, 128, 144));
        layeredMainPane.add(accPanel, "name_909218630872200");

        prodPanel = new JPanel();
        layeredMainPane.setLayer(prodPanel, 3);
        prodPanel.setBackground(new Color(210, 180, 140));
        layeredMainPane.add(prodPanel, "name_909220516969100");

        salesPanel = new JPanel();
        layeredMainPane.setLayer(salesPanel, 4);
        salesPanel.setBackground(new Color(230, 230, 250));
        layeredMainPane.add(salesPanel, "name_909227160308500");

        salesRepPanel = new JPanel();
        salesRepPanel.setBackground(new Color(0, 128, 128));
        layeredMainPane.add(salesRepPanel, "name_935662017087300");

    }
    //method for switching panels
    public void switchScreen(JPanel p) {
        layeredMainPane.removeAll();
        layeredMainPane.add(p);
        layeredMainPane.repaint();
        layeredMainPane.revalidate();
    }
}