package com.shangest.graphics.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.shangest.base.Note;
import com.shangest.connector.Connector.App;

public class EditorNota extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int INSERT_MODE = 0;
	private static final int EDIT_MODE = 1;
	
	private int mode = INSERT_MODE;
	private Note n;
	
	private JPanel contentPane;
	private JTextField noteName;
	private JButton btnAceptar;
	private JTextArea textArea;
	private JButton saveButton;
	private JButton printButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditorNota frame = new EditorNota();
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
	
	public EditorNota(String name) {
		this();
		acceptButtonAction(name);
	}
	
	public EditorNota() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditorNota.class.getResource("/icons/SG.png")));
		setTitle("Editor de Notas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 648, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 5));
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel_3.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 5));
		
		noteName = new JTextField();
		noteName.setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 18));
		noteName.setForeground(Color.DARK_GRAY);
		panel.add(noteName);
		noteName.setColumns(30);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.SOUTH);
		
		btnAceptar = new JButton("Describir");
		btnAceptar.setIcon(new ImageIcon(EditorNota.class.getResource("/icons/Edit_24x24.png")));
		panel_5.add(btnAceptar);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				acceptButtonAction();
			}
		});
				
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Descripci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, Color.GRAY));
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		panel_1.add(new JScrollPane(textArea), BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel buttons = new JPanel();
		panel_2.add(buttons, BorderLayout.WEST);
		
		saveButton = new JButton("Guardar");
		saveButton.setIcon(new ImageIcon(EditorNota.class.getResource("/icons/Save_24x24.png")));
		saveButton.setEnabled(false);
		buttons.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveNote();
			}
		});
		
		printButton = new JButton("Imprimir");
		printButton.setIcon(new ImageIcon(EditorNota.class.getResource("/icons/Print_24x24.png")));
		printButton.setEnabled(false);
		printButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openPrintableView();
			}
		});
		buttons.add(printButton);
	}
	
	/**
	 * Non Graphic Block
	 * */
	private void saveNote() {
		if (noteName.getText().trim().length() > 0) {
			
			if (n == null) {
				n = new Note();
				n.setName(noteName.getText().trim().toUpperCase());
			}
		
			n.setDescription(textArea.getText());
			n.setDate(new Date());

			//save note
			if (mode == INSERT_MODE) {
				if (App.onlineConn.noteSearch(n.getName()) == null) {
					App.onlineConn.addNote(n);
					setVisible(false);
				}
				else
					JOptionPane.showMessageDialog(this, "Actualmente existe una nota registrada con este nombre.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				App.onlineConn.editNote(n);
				setVisible(false);
			}
		}
	}
	
	private void acceptButtonAction() {
		
		noteName.setEditable(false);
		noteName.setBackground(UIManager.getColor("Button.background"));
		
		btnAceptar.setVisible(false);
		
		saveButton.setEnabled(true);
		printButton.setEnabled(true);
		
		textArea.setEditable(true);
		
		if (n != null) {
			mode = EDIT_MODE;
			
			noteName.setText(n.getName());
			textArea.setText(n.getDescription());
		}
	}
	
	private void acceptButtonAction(String name) {		
		n = App.onlineConn.noteSearch(name);
		
		noteName.setText(name);
		noteName.setEditable(false);
		noteName.setBackground(UIManager.getColor("Button.background"));
		
		btnAceptar.setVisible(false);
		
		saveButton.setEnabled(true);
		printButton.setEnabled(true);
		
		textArea.setEditable(true);
		
		if (n != null) {
			mode = EDIT_MODE;
			
			noteName.setText(n.getName());
			textArea.setText(n.getDescription());
		}
	}
	
	private void openPrintableView(){
		/*
		JFrame d = new JFrame();
		d.setSize(540, 480);
		d.setTitle("Version Imprimible");
		d.getContentPane().setLayout(new BorderLayout());
		*/
		try {
			//Creating document
			Document doc = new Document(PageSize.A4, 30, 30, 30, 30);
			File temp = new File(App.EXPORT_FOLDER+"/"+n.getName().replace(' ', '_')+".pdf");
			
			PdfWriter.getInstance(doc, new FileOutputStream(temp));
			doc.open();
			
			Font nf = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
			Phrase p1 = new Phrase(getPrintableTitle(), new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD | Font.UNDERLINE));			
            
			Paragraph sign_p = new Paragraph(n.getDescription().trim()+"\n", nf);

			doc.add(new Paragraph(p1));
			doc.add(new Paragraph("\n\n"));
			doc.add(sign_p);
            
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
	
	protected String getPrintableTitle(){
		return n.getName();
	}
}
