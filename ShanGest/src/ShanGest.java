import java.io.File;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.shangest.connector.Connector.App;
import com.shangest.graphics.SGFrame;
import com.shangest.graphics.forms.EditorAcceso;

public class ShanGest {

	public static void main(String [] args) {
	
		try {
		    com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Green", "", "");
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		} 
		catch (Exception e) {}
		
		new File(App.EXPORT_FOLDER).mkdir();
		
		/**
		 * Open Application
		 * */
		final SGFrame sgf = new SGFrame();
		sgf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sgf.setLocationRelativeTo(null);		
		
		new Thread() {
			public void run () {
				/**
				 * Login block
				 * */
				EditorAcceso eacc = new EditorAcceso();
				eacc.setLocationRelativeTo(null);
				eacc.setVisible(true);
				
				try {
					while (eacc.isVisible()) {
						Thread.sleep(500);
					}
					
					sgf.setConnectorMode(App.getConnectorMode());
					sgf.updateTableData();
					sgf.setVisible(true);
				}
				catch (InterruptedException e) {interrupt();}
			}
		}.start();
	}
}
