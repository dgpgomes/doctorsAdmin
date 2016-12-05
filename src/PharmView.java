import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PharmView extends JFrame implements ActionListener{

	// TableModel definition
	JTable table = new JTable();
	static String Presc;
	
	public PharmView(){

		// Setting proprieties for the jframe
		setSize(460,500);
		setLocationRelativeTo(null);
		setVisible(true);
		FlowLayout fl = new FlowLayout();
		this.setLayout(fl);
		setResizable(false);
		
		// Starts connection
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
		
    	try {

    		conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gui_proj?" + "user=root&password=root");
    	    stmt = conn.createStatement();
    	    
    	    // Selects all prescriptions and the name of the patient
    	    String sql = "select pre.id, app.name, pre.prescription from prescription pre " +
    	    			 "join appointments app on app.id = pre.app_id";
    	    System.out.println(sql);
    	    
    	    rs = stmt.executeQuery(sql);
  	    
        	String[] columnNames = {"ID", "Patient Name", "Prescription"};  
    		
       	
        	// JTable Edit OFF
        	DefaultTableModel dtm = new DefaultTableModel(0, 0) {
        	    public boolean isCellEditable(int row, int column) {
        	        return false;
        	    }
        	};
        	table.setModel(dtm);
        	// JTable Edit OFF
        	
        	DefaultTableModel aModel = (DefaultTableModel) table.getModel();
        	aModel.setColumnIdentifiers(columnNames);
        			
	
        	// Loop through the ResultSet and transfer in the Model
        	java.sql.ResultSetMetaData rsmd = rs.getMetaData();
        	int col = rsmd.getColumnCount();
        	while(rs.next()){
        		Object[] objects = new Object[col];

        		for (int x = 0; x < col ; x++){
        			objects[x] = rs.getObject (x + 1);
        		}
        		
        		aModel.addRow(objects);
        	}
        	
        	table.setModel(aModel);
        	this.add(new JScrollPane(table));
        	
  	    
    	} catch (SQLException Err) {
    		// Error handling
    	    String Err1 = "SQLException: " + Err.getMessage() + " \n";
    	    String Err2 = "SQLState: " + Err.getSQLState() + " \n";
    	    String Err3 = "VendorError: " + Err.getErrorCode() + " ";
    		String FullErrorMsg = Err1 + Err2 + Err3;
    	    
    		JOptionPane.showMessageDialog(null, FullErrorMsg);
    	}
		
		// Button
    	JButton expand = new JButton("Expand prescription");
		expand.addActionListener(this);
		expand.setActionCommand("exp");
		this.add(expand);
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
				
		int row;
		
		row = table.getSelectedRow();
        // Error handling
        if (row == -1){
        	JOptionPane.showMessageDialog(null, "Select a prescription!");  
        }else{	            	
        	Presc  = (String) table.getValueAt(row, 2); 
    		Presc p = new Presc();
        }
		
	}
}
