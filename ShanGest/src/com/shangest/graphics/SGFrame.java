package com.shangest.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import com.desktop.rhinos.gui.table.NotesTable;
import com.desktop.rhinos.gui.table.QueriesTable;
import com.desktop.rhinos.gui.table.SignsTable;
import com.shangest.connector.Connector.App;
import com.shangest.graphics.forms.EditorConsulta;
import com.shangest.graphics.forms.EditorNota;
import com.shangest.graphics.forms.EditorSigno;

public class SGFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
		
	private NotesTable notesTable;
	private SignsTable signsTable;
	private QueriesTable queriesTable;
	
	private JMenuItem mntmALocal;
	private JMenuItem mntmABaseDe;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SGFrame frame = new SGFrame();
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
	public SGFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SGFrame.class.getResource("/icons/SG.png")));
		setTitle("ShanGest");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 483);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenu mnExportar = new JMenu("Sincronizar");
		mnExportar.setIcon(new ImageIcon(SGFrame.class.getResource("/icons/Synchronize_24x24.png")));
		mnArchivo.add(mnExportar);
		
		mntmALocal = new JMenuItem("Local");
		mntmALocal.setIcon(new ImageIcon(SGFrame.class.getResource("/icons/Download_24x24.png")));
		mntmALocal.setEnabled(false);
		mnExportar.add(mntmALocal);
		
		mntmALocal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				toLocalSync();
			}
		});
		
		mntmABaseDe = new JMenuItem("Base de Datos");
		mntmABaseDe.setIcon(new ImageIcon(SGFrame.class.getResource("/icons/Upload_24x24.png")));
		mntmABaseDe.setEnabled(false);
		mnExportar.add(mntmABaseDe);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setIcon(new ImageIcon(SGFrame.class.getResource("/icons/Log Out_24x24.png")));
		mnArchivo.add(mntmSalir);
		mntmSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
				
		JMenuItem mntmNota = new JMenuItem("Nota");
		mntmNota.setIcon(new ImageIcon(SGFrame.class.getResource("/icons/Edit_24x24.png")));
		mnEditar.add(mntmNota);
		mntmNota.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				registerNote();
			}
		});

		JMenuItem mntmSigno = new JMenuItem("Signo");
		mntmSigno.setIcon(new ImageIcon(SGFrame.class.getResource("/icons/Favorites_24x24.png")));
		mnEditar.add(mntmSigno);
		mntmSigno.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				registerSign();
			}
		});
		
		JMenuItem mntmConsulta = new JMenuItem("Consulta");
		mntmConsulta.setIcon(new ImageIcon(SGFrame.class.getResource("/icons/Copy v2_24x24.png")));
		mnEditar.add(mntmConsulta);
		mntmConsulta.addActionListener(new ActionListener() {
						
			@Override
			public void actionPerformed(ActionEvent e) {
				registerQuery();
			}
		});
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de ..");
		mntmAcercaDe.setIcon(new ImageIcon(SGFrame.class.getResource("/icons/Help_24x24.png")));
		mnAyuda.add(mntmAcercaDe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel_1.add(tabbedPane);
		
		JPanel panel_2 = new JPanel() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				Graphics g2 = (Graphics2D)g;
				
				ImageIcon i = new ImageIcon(SGFrame.class.getResource("/pics/homeTab.png"));
				g2.drawImage(i.getImage(), 0, 0, getWidth(), getHeight(), null);
			
			}
		};
		panel_2.setFocusable(false);
		tabbedPane.addTab("Inicio", new ImageIcon(SGFrame.class.getResource("/icons/Information_24x24.png")), panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel tabNotes = new JPanel(new BorderLayout());
		tabNotes.setBackground(new Color(0xd3, 0xdf, 0x9e));
		tabNotes.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		tabNotes.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		tabNotes.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);
				
		notesTable = new NotesTable();
		tabNotes.add(notesTable);
		tabbedPane.addTab("Notas", new ImageIcon(SGFrame.class.getResource("/icons/Edit_24x24.png")), tabNotes, null);
		
		JPanel tabSignos = new JPanel(new BorderLayout());
		tabSignos.setBackground(new Color(0xd3, 0xdf, 0x9e));
		tabSignos.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		tabSignos.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		tabSignos.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);
		
		signsTable = new SignsTable();
		tabSignos.add(signsTable);
		tabbedPane.addTab("Signos", new ImageIcon(SGFrame.class.getResource("/icons/Favorites_24x24.png")), tabSignos, null);		
		
		JPanel tabConsultas = new JPanel(new BorderLayout());
		tabConsultas.setBackground(new Color(0xd3, 0xdf, 0x9e));
		tabConsultas.add(Box.createHorizontalStrut(30), BorderLayout.WEST);
		tabConsultas.add(Box.createHorizontalStrut(30), BorderLayout.EAST);
		tabConsultas.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);
		
		queriesTable = new QueriesTable();
		tabConsultas.add(queriesTable);
		tabbedPane.addTab("Consultas", new ImageIcon(SGFrame.class.getResource("/icons/Copy v2_24x24.png")), tabConsultas, null);
	}

	/**
	 * Non Graphic Block
	 * */
	private void registerNote() {
		EditorNota en = new EditorNota();
		en.setLocationRelativeTo(this);
		en.setVisible(true);
	}
	
	private void registerSign() {
		EditorSigno es = new EditorSigno();
		es.setLocationRelativeTo(this);
		es.setVisible(true);
	}
	
	private void registerQuery() {
		EditorConsulta ec = new EditorConsulta();
		ec.setLocationRelativeTo(this);
		ec.setVisible(true);
	}
	
	public void updateTableData() {
		notesTable.updateTableData();
		signsTable.updateTableData();
		queriesTable.updateTableData();
	}
	
	public void setConnectorMode(int mode) {
		if (mode == App.ONLINE)
			mntmALocal.setEnabled(true);
		
		if (mode == App.OFFLINE)
			mntmABaseDe.setEnabled(true);
	}
	
	public void toLocalSync() {
		App.onlineConn.exportDataFile();
	}
}
