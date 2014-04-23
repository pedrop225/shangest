package com.shangest.graphics.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.shangest.base.Consulta;
import com.shangest.base.Signo;
import com.shangest.connector.Connector.App;
import com.shangest.graphics.forms.util.RepresentaSigno;
import com.toedter.calendar.JDateChooser;

public class EditorConsulta extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final int INSERT_MODE = 0;
	public static final int EDIT_MODE = 1;
	
	private JPanel contentPane;
	private JTextField textName;

	private RepresentaSigno signRep;
	private RepresentaSigno witnessRep;
	private RepresentaSigno witness_2Rep;
	
	private JLabel lblSign;
	private JLabel lblWitness;
	private JLabel lblWitness_2;
	
	private JTextArea textSign;
	private JTextArea textWitness;
	private JTextArea textWitness_2;
	
	private JDateChooser dch;
	private int mode;
	
	private Signo signo;
	private Signo witness_1;
	private Signo witness_2;
	
	private JButton btnGuardar;
	private JButton btnImprimir;
	
	private JButton btnWitness;
	private JButton btnWitness_2;
	private JButton btnSign;
	
	private Consulta c = new Consulta();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorConsulta frame = new EditorConsulta();
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
	
	public EditorConsulta(Consulta c) {
		this();
		this.c = c;
		mode = EDIT_MODE;
				
		textName.setEnabled(false);
		textName.setBackground(UIManager.getColor("Button.background"));
		dch.setEnabled(false);
		
		textName.setText(c.getName());
		dch.setDate(c.getDate());
		
		signo = new Signo();
		witness_1 = new Signo();
		witness_2 = new Signo();
		
		signo.setNombre(c.getSigno().getNombre());
		signo.setRep(c.getSigno().getRep());
		signo.setDesc(c.getSigno().getDesc());
		signRep.setRep(signo.getRep());
		lblSign.setText(signo.getNombre());
		textSign.setText(signo.getDesc());

		witness_1.setNombre(c.getTest1().getNombre());
		witness_1.setRep(c.getTest1().getRep());
		witness_1.setDesc(c.getTest1().getDesc());
		witnessRep.setRep(witness_1.getRep());
		lblWitness.setText(witness_1.getNombre());
		textWitness.setText(witness_1.getDesc());
		
		witness_2.setNombre(c.getTest2().getNombre());
		witness_2.setRep(c.getTest2().getRep());
		witness_2.setDesc(c.getTest2().getDesc());
		witness_2Rep.setRep(witness_2.getRep());
		lblWitness_2.setText(witness_2.getNombre());
		textWitness_2.setText(witness_2.getDesc());
		
		btnImprimir.setVisible(true);
	}
	
	public EditorConsulta() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditorConsulta.class.getResource("/icons/SG.png")));
		setTitle("Editor de Consultas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 669, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		mode = INSERT_MODE;
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Consultando a:", TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(12, 0));
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new GridLayout(2, 1, 0, 3));
		
		JLabel lblNewLabel_1 = new JLabel(" Nombre");
		lblNewLabel_1.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
		panel_3.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(" Fecha");
		lblNewLabel_2.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
		panel_3.add(lblNewLabel_2);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new GridLayout(2, 1, 0, 3));
		
		textName = new JTextField();
		textName.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
		textName.setForeground(Color.DARK_GRAY);
		panel_4.add(textName);
		textName.setColumns(10);
		
		dch = new JDateChooser(new Date());
		dch.setDateFormatString("dd/MM/yyyy");
		dch.getDateEditor().setEnabled(false);
		panel_4.add(dch);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel_1.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("Signos", new ImageIcon(EditorConsulta.class.getResource("/icons/Favorites_24x24.png")), panel_6, null);
		panel_6.setLayout(new GridLayout(0, 3, 30, 0));
		
		JPanel panel_9 = new JPanel();
		panel_6.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		btnWitness_2 = new JButton("Segundo Testigo");
		btnWitness_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnWitness_2Action();
			}
		});
		
		btnWitness_2.setFocusable(false);
		btnWitness_2.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
		btnWitness_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(btnWitness_2, BorderLayout.NORTH);
		
		lblWitness_2 = new JLabel(" ");
		lblWitness_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblWitness_2.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
		panel_9.add(lblWitness_2, BorderLayout.SOUTH);
		
		JPanel panel_15 = new JPanel();
		panel_9.add(panel_15, BorderLayout.CENTER);
		
		witness_2Rep = new RepresentaSigno();
		panel_15.add(witness_2Rep);
		
		JPanel panel_8 = new JPanel();
		panel_6.add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		btnWitness = new JButton("Primer Testigo");
		btnWitness.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnWitnessAction();
			}
		});
		
		btnWitness.setFocusable(false);
		btnWitness.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
		btnWitness.setHorizontalAlignment(SwingConstants.CENTER);
		panel_8.add(btnWitness, BorderLayout.NORTH);
		
		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12, BorderLayout.CENTER);
		
		witnessRep = new RepresentaSigno();
		panel_12.add(witnessRep);
		
		lblWitness = new JLabel(" ");
		lblWitness.setHorizontalAlignment(SwingConstants.CENTER);
		lblWitness.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
		panel_8.add(lblWitness, BorderLayout.SOUTH);
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		btnSign = new JButton("Oddun Toyale");
		btnSign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				btnSignAction();
			}
		});
		
		btnSign.setFocusable(false);
		btnSign.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 14));
		btnSign.setHorizontalAlignment(SwingConstants.CENTER);
		panel_7.add(btnSign, BorderLayout.NORTH);
		
		JPanel panel_10 = new JPanel();
		panel_7.add(panel_10, BorderLayout.CENTER);
		
		signRep = new RepresentaSigno();
		panel_10.add(signRep);
		
		lblSign = new JLabel(" ");
		lblSign.setHorizontalAlignment(SwingConstants.CENTER);
		lblSign.setFont(new java.awt.Font("Monospaced",java.awt.Font.BOLD, 14));
		panel_7.add(lblSign, BorderLayout.SOUTH);
		
		JPanel panel_16 = new JPanel();
		tabbedPane.addTab("Descripciones", new ImageIcon(EditorConsulta.class.getResource("/icons/Edit_24x24.png")), panel_16, null);
		panel_16.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_17 = new JPanel();
		panel_16.add(panel_17, BorderLayout.CENTER);
		panel_17.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel_21 = new JPanel();
		panel_21.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Oddun Toyale", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(128, 128, 128)));
		panel_17.add(panel_21);
		panel_21.setLayout(new BorderLayout(0, 0));
		
		textSign = new JTextArea();
		textSign.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
		textSign.setRows(5);
		textSign.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(textSign);
		panel_21.add(scrollPane);
		
		JPanel panel_18 = new JPanel();
		panel_17.add(panel_18);
		panel_18.setBorder(new TitledBorder(null, "Primer Testigo", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		panel_18.setLayout(new BorderLayout(0, 0));
		
		textWitness = new JTextArea();
		textWitness.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
		textWitness.setRows(5);
		textWitness.setLineWrap(true);
		textWitness.setWrapStyleWord(true);
		panel_18.add(new JScrollPane(textWitness));
		
		JPanel panel_20 = new JPanel();
		panel_17.add(panel_20);
		panel_20.setBorder(new TitledBorder(null, "Segundo Testigo", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		panel_20.setLayout(new BorderLayout(0, 0));
		
		textWitness_2 = new JTextArea();
		textWitness_2.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
		textWitness_2.setRows(5);
		textWitness_2.setLineWrap(true);
		textWitness_2.setWrapStyleWord(true);
		panel_20.add(new JScrollPane(textWitness_2));
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel buttons = new JPanel();
		panel_2.add(buttons, BorderLayout.WEST);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(EditorConsulta.class.getResource("/icons/Save_24x24.png")));
		btnGuardar.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveQuery();
			}
		});
		buttons.add(btnGuardar);
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.setIcon(new ImageIcon(EditorConsulta.class.getResource("/icons/Print_24x24.png")));
		btnImprimir.setVisible(false);
		btnImprimir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openPrintableView();
			}
		});
		buttons.add(btnImprimir);
	}
	
	/**
	 * Non Graphic Block
	 * */
	private void btnSignAction() {
		signo = App.onlineConn.signSearch(signRep.toString());
		
		if (signo != null) {
			lblSign.setText(signo.getNombre());
			textSign.setText(signo.getDesc());
		}
	}
	
	private void btnWitnessAction() {
		witness_1 = App.onlineConn.signSearch(witnessRep.toString());
		
		if (witness_1 != null) {
			lblWitness.setText(witness_1.getNombre());
			textWitness.setText(witness_1.getDesc());
		}
	}
	
	private void btnWitness_2Action() {
		witness_2 = App.onlineConn.signSearch(witness_2Rep.toString());
		
		if (witness_2 != null) {
			lblWitness_2.setText(witness_2.getNombre());
			textWitness_2.setText(witness_2.getDesc());
		}
	}
	
	private void saveQuery() {
		if (textName.getText().trim().length() > 0) {
			
			c.setName(textName.getText().toUpperCase().trim());
			c.setDate((dch.getDate() != null) ? dch.getDate() : new Date());
			
			//updating descriptions
			signo.setDesc(textSign.getText());
			witness_1.setDesc(textWitness.getText());
			witness_2.setDesc(textWitness_2.getText());
			
			c.setSigno(signo);
			c.setTest1(witness_1);
			c.setTest2(witness_2);
			
			
			if (mode == INSERT_MODE) {
				if (App.onlineConn.querySearch(c.getName(), new SimpleDateFormat("dd-MM-yyyy").format(c.getDate())) == null) {
					App.onlineConn.addQuery(c);
					setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(this, "Actualmente existe una consulta con este nombre y fecha.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				App.onlineConn.editQuery(c);
				setVisible(false);
			}
		}
	}
	
	private void openPrintableView(){
		/*
		JFrame d = new JFrame();
		d.setSize(540, 480);
		d.setTitle("Version Imprimible");
		d.getContentPane().setLayout(new BorderLayout());*/
		
		try {
			//Creating document
			Document doc = new Document(PageSize.A4, 30, 30, 30, 30);
			File temp = new File(App.EXPORT_FOLDER+"/"+c.getName().replace(' ', '_')+ "_("+new SimpleDateFormat("dd-MM-yyyy").format(c.getDate())+").pdf");
			
			PdfWriter.getInstance(doc, new FileOutputStream(temp));
			doc.open();
			
			Font nf = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
			Font bf = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
			
			Phrase p1 = new Phrase(getPrintableTitle(), new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD | Font.UNDERLINE));			
			
			PdfPCell p2   = new PdfPCell(new Phrase("Nombre:  ", bf));
			PdfPCell p2_0 = new PdfPCell(new Phrase(textName.getText().trim(), nf));
			PdfPCell p3   = new PdfPCell(new Phrase("Fecha:  ", bf));
			PdfPCell p3_0 = new PdfPCell(new Phrase(new SimpleDateFormat("dd-MM-yyyy").format(dch.getDate()), nf));
			
			p2.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p2_0.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p3.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			p3_0.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			
			PdfPTable hTable = new PdfPTable(2);
			hTable.addCell(p2);
			hTable.addCell(p2_0);
			hTable.addCell(p3);
			hTable.addCell(p3_0);
			
			hTable.setWidthPercentage(100f);
			float [] w = {15f, 80f};
			hTable.setWidths(w);
		
			Paragraph parag = new Paragraph();
			parag.add(hTable);	
            
			Paragraph sign_p = new Paragraph(textSign.getText().trim()+"\n", nf);
			Paragraph witness_1_p = new Paragraph(textWitness.getText().trim()+"\n", nf);
			Paragraph witness_2_p = new Paragraph(textWitness_2.getText().trim()+"\n", nf);

			doc.add(new Paragraph(p1));
			doc.add(new Paragraph("\n\n"));
			doc.add(parag);
			doc.add(new Paragraph("\n"));
			doc.add(sign_p);
			doc.add(witness_1_p);
			doc.add(witness_2_p);
            
			doc.close();
			
			//open system viewer
			Desktop.getDesktop().open(temp);
	/*		
			//Reading document
			Viewer v = new Viewer();
			v.setDocumentInputStream(new FileInputStream(temp));
			v.activate();
			d.getContentPane().add(v);*/
		} 
		catch (Exception e) {e.printStackTrace();}
		/*
		d.setLocationRelativeTo(null);
		d.setVisible(true);*/
	}
	
	protected float[] getWidthsPrintableView() {
		return null;
	}
	
	protected String getPrintableTitle(){
		return "Vista de Orula";
	}
}
