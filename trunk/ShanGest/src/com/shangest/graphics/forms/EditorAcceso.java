package com.shangest.graphics.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.shangest.connector.Connector.App;
import com.shangest.connector.disk.LocalConnector;
import com.shangest.connector.mysql.MysqlConnector;

public class EditorAcceso extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel;
	private JTextField textField;
	private JPasswordField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditorAcceso dialog = new EditorAcceso();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditorAcceso() {
		setUndecorated(true);
		setTitle("Acceso ShanGest");
		setSize(400, 512);
		setIconImage(new ImageIcon(EditorAcceso.class.getResource("/icons/SG.png")).getImage());

		contentPanel = new JPanel() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics g2 = (Graphics2D)g;
				
				ImageIcon i = new ImageIcon(EditorAcceso.class.getResource("/pics/front.jpg"));
				g2.drawImage(i.getImage(), 0, 0, getWidth(), getHeight(), null);
			
			}
		};
		
		((JComponent) getContentPane()).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setVgap(5);
		getContentPane().setLayout(borderLayout);
		getContentPane().setBackground(new Color(0xd3, 0xdf, 0x9e));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0, 0, 0, 0));
			contentPanel.add(panel, BorderLayout.SOUTH);
			{
				JPanel panel_3 = new JPanel();
				panel_3.setBackground(new Color(0, 0, 0, 0));
				panel.add(panel_3);
				panel_3.setLayout(new BorderLayout(5, 0));
				{
					JPanel panel_2 = new JPanel();
					panel_3.add(panel_2, BorderLayout.WEST);
					panel_2.setLayout(new BorderLayout(0, 0));
					{
						JPanel panel_1 = new JPanel();
						panel_2.add(panel_1);
						panel_1.setLayout(new GridLayout(2, 1, 0, 2));
						panel_1.setBackground(new Color(0, 0, 0, 0));
						{
							JLabel lblNewLabel = new JLabel("Usuario ");
							lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 13));
							lblNewLabel.setForeground(new Color(0x00, 0x90, 0x45));
							lblNewLabel.setBackground(new Color(0, 0, 0, 0));
							panel_1.add(lblNewLabel);
						}
						{
							JLabel lblNewLabel_1 = new JLabel("Contraseña ");
							lblNewLabel_1.setFont(new Font("Monospaced", Font.BOLD, 13));
							lblNewLabel_1.setForeground(new Color(0x00, 0x90, 0x45));
							lblNewLabel_1.setBackground(new Color(0, 0, 0, 0));
							panel_1.add(lblNewLabel_1);
						}
					}
				}
				{
					JPanel panel_2 = new JPanel();
					panel_3.add(panel_2, BorderLayout.CENTER);
					panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
					{
						JPanel panel_1 = new JPanel();
						panel_1.setBackground(new Color(0, 0, 0, 0));
						panel_2.setBackground(new Color(0, 0, 0, 0));
						panel_2.add(panel_1);
						panel_1.setLayout(new GridLayout(2, 1, 0, 2));
						{
							textField = new JTextField();
							textField.setFont(new Font("Monospaced", Font.BOLD, 13));
							textField.setForeground(new Color(0x00, 0x90, 0x45));
							textField.setBackground(new Color(0xd3, 0xdf, 0x9e));
							panel_1.add(textField);
							textField.setColumns(20);
						}
						{
							textField_1 = new JPasswordField();
							textField_1.setFont(new Font("Monospaced", Font.BOLD, 13));
							textField_1.setForeground(new Color(0x00, 0x90, 0x45));
							textField_1.setBackground(new Color(0xd3, 0xdf, 0x9e));
							panel_1.add(textField_1);
							textField_1.setColumns(20);
						}
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(0, 0, 0, 0));
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Online");
				okButton.setFont(new Font("Monospaced", Font.BOLD, 12));
				okButton.setBackground(new Color(0xd3, 0xdf, 0x9e));
				okButton.setFocusable(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						onlineButtonAction();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton btnNewButton = new JButton("Offline");
				btnNewButton.setFont(new Font("Monospaced", Font.BOLD, 12));
				btnNewButton.setBackground(new Color(0xd3, 0xdf, 0x9e));
				btnNewButton.setFocusable(false);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						offlineButtonAction();
					}
				});				
				buttonPane.add(btnNewButton);
			}
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});
	}
	
	/**
	 * Non Graphic block
	 * */
	
	private void onlineButtonAction() {
		App.onlineConn = MysqlConnector.getInstance();

		if (App.onlineConn.login(textField.getText().trim(), new String(textField_1.getPassword()).trim())) {
			App.offlineConn = null;
			App.setConnectorMode(App.ONLINE);
			setVisible(false);
		}
		
		textField_1.setText("");
	}
	
	@SuppressWarnings("deprecation")
	private void offlineButtonAction() {
		App.offlineConn = LocalConnector.getInstance();

		if (App.offlineConn.login(textField.getText().trim(), textField_1.getText().trim())) {
			App.onlineConn = null;
			App.setConnectorMode(App.OFFLINE);
			
			if (new File(LocalConnector.DATA_FILE).exists())
				App.offlineConn.loadDataFile();
			
			setVisible(false);
		}
		
		textField_1.setText("");
	}

}
