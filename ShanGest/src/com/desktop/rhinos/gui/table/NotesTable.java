package com.desktop.rhinos.gui.table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.shangest.base.Note;
import com.shangest.connector.Connector.App;
import com.shangest.graphics.forms.EditorNota;

public class NotesTable extends RhTable {

	private static final long serialVersionUID = 1L;
	
	public NotesTable() {
		tm.addColumn("Nombre");
		tm.addColumn("Fecha");
	}
	
	protected void removeSelected() {
		if (table.getSelectedRowCount() > 0) {
			
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			int _c = table.convertColumnIndexToModel(1);
			String name = (String)table.getValueAt(r, c);
			String date = (String)table.getValueAt(r, _c);

			
			if (JOptionPane.showConfirmDialog(null, "Desea eliminar la nota \""+name+" ("+date+")\"? ", "Eliminando nota ..", 
											  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				
				App.onlineConn.deleteNote(name, date);
				updateTableData();
			}
		}
	}
	
	protected void lookUpSelected() {
		if (table.getSelectedRowCount() > 0) {
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			
			String name = ((String)tm.getValueAt(r, c));
		
			EditorNota en = new EditorNota(name);
			en.setLocationRelativeTo(null);
			en.setVisible(true);
		}
	}
	
	public void updateTableData() {
		tm.setRowCount(0);
		
		ArrayList<Note> an = (App.getConnectorMode() == App.ONLINE) ? App.onlineConn.getNotes() : App.offlineConn.getNotes();
		filterBackUp = new Object[an.size()][];
		
		for (int i = 0; i < an.size(); i++) {
			Note n = an.get(i);
			Object [] o = {n.getName(), new SimpleDateFormat("dd-MM-yyyy [HH:mm:ss]").format(n.getDate())};
			
			tm.addRow(filterBackUp[i] = o);
		}
	}
	
	protected float[] getWidthsPrintableView() {
		float[] i={10f, 25f, 7f, 15f};
		return i;
	}
	
	@Override
	protected String getPrintableTitle() {
		return "Notas";
	}
}