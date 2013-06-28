package com.desktop.rhinos.gui.table;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.shangest.base.Signo;
import com.shangest.connector.Connector.App;
import com.shangest.graphics.forms.EditorSigno;

public class SignsTable extends RhTable {

	private static final long serialVersionUID = 1L;
	
	public SignsTable() {
		tm.addColumn("Nombre");
	}
	
	protected void removeSelected() {
		if (table.getSelectedRowCount() > 0) {
			
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			String name = (String)table.getValueAt(r, c);
			
			if (JOptionPane.showConfirmDialog(null, "Desea eliminar el signo \""+name+"\"? ", "Eliminando signo ..", 
											  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				
				App.onlineConn.deleteSign(name);
				updateTableData();
			}
		}
	}
	
	protected void lookUpSelected() {
		if (table.getSelectedRowCount() > 0) {
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			
			EditorSigno es = new EditorSigno((String)tm.getValueAt(r, c));
			es.setLocationRelativeTo(null);
			es.setVisible(true);
		}
	}
	
	public void updateTableData() {
		tm.setRowCount(0);
		
		ArrayList<Signo> as = (App.getConnectorMode() == App.ONLINE) ? App.onlineConn.getSigns() : App.offlineConn.getSigns();
		filterBackUp = new Object[as.size()][];
		
		for (int i = 0; i < as.size(); i++) {
			Signo s = as.get(i);
			Object [] o = {s.getNombre()};
			
			tm.addRow(filterBackUp[i] = o);
		}
	}
	
	protected float[] getWidthsPrintableView() {
		float[] i={30f};
		return i;
	}
	
	@Override
	protected String getPrintableTitle() {
		return "Signos";
	}
}