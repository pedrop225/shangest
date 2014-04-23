package com.desktop.rhinos.gui.table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.shangest.base.Consulta;
import com.shangest.connector.Connector.App;
import com.shangest.graphics.forms.EditorConsulta;

public class QueriesTable extends RhTable {

	private static final long serialVersionUID = 1L;
	
	public QueriesTable() {
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

			
			if (JOptionPane.showConfirmDialog(null, "Desea eliminar la consulta \""+name+" ("+date+")\"? ", "Eliminando consulta ..", 
											  JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				
				App.onlineConn.deleteQuery(name, date);
				updateTableData();
			}
		}
	}
	
	protected void lookUpSelected() {
		if (table.getSelectedRowCount() > 0) {
			int r = table.convertRowIndexToModel(table.getSelectedRow());
			int c = table.convertColumnIndexToModel(0);
			int _c = table.convertColumnIndexToModel(1);
			
			String name = ((String)tm.getValueAt(r, c));
			String date = ((String)tm.getValueAt(r, _c));
			
			EditorConsulta ec = new EditorConsulta(App.onlineConn.querySearch(name, date));
			ec.setLocationRelativeTo(null);
			ec.setVisible(true);
		}
	}
	
	public void updateTableData() {
		tm.setRowCount(0);
		
		ArrayList<Consulta> aq = (App.getConnectorMode() == App.ONLINE) ? App.onlineConn.getQueries() : App.offlineConn.getQueries();
		filterBackUp = new Object[aq.size()][];
		
		for (int i = 0; i < aq.size(); i++) {
			Consulta c = aq.get(i);
			Object [] o = {c.getName(), new SimpleDateFormat("dd-MM-yyyy").format(c.getDate())};
			
			tm.addRow(filterBackUp[i] = o);
		}
	}
	
	protected float[] getWidthsPrintableView() {
		float[] i={25f, 25f};
		return i;
	}
	
	@Override
	protected String getPrintableTitle() {
		return "Clientes";
	}
}