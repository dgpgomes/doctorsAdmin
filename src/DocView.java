import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.table.TableColumn;

public class DocView extends JFrame implements ActionListener{

	static int IDApp = 0;
	static String NameApp = "";
	
	// TableModel definition
	JTable table = new JTable();
	
	public DocView(){
		// Set proprieties for jframe
		setSize(460,500);
		setLocationRelativeTo(null);
		setVisible(true);
		FlowLayout fl = new FlowLayout();
		this.setLayout(fl);
		setResizable(false);
		
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
  	
    	try {
    		// Starts connection
    		conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gui_proj?" + "user=root&password=root");
    	    stmt = conn.createStatement();
    	    
    	    // Selects everything from appointments and moves data into the jtable
    	    String sql = "select * from appointments;";
    	    System.out.println(sql);
    	    
    	    rs = stmt.executeQuery(sql);
  	    
        	String[] columnNames = {"ID", "Name", "Age", "Date", "Address", "Number"};  
    		      	
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

        		for (int x = 0; x < col; x++) {
        			objects[x] = rs.getObject (x + 1);
        		}       		
        		aModel.addRow(objects);
        	}
        	
        	table.setModel(aModel);
        	this.add(new JScrollPane(table));
        	
        	// Adjusts columns
        	TableColumn column = null;
            for (int i = 0; i < 5; i++) {
                column = table.getColumnModel().getColumn(i);
                if (i == 0) {
                    column.setPreferredWidth(20);
                } 
                if (i == 1) {
                    column.setPreferredWidth(75);
                }
                if (i == 2) {
                    column.setPreferredWidth(30);
                }
                if (i == 3) {
                    column.setPreferredWidth(80);
                }
                if (i == 4) {
                    column.setPreferredWidth(110);
                }
                if (i == 5) {
                    column.setPreferredWidth(100);
                }
            } 
        	

  	    
    	} catch (SQLException Err) {
    		// Exception Handling
    	    String Err1 = "SQLException: " + Err.getMessage() + " \n";
    	    String Err2 = "SQLState: " + Err.getSQLState() + " \n";
    	    String Err3 = "VendorError: " + Err.getErrorCode() + " ";
    		String FullErrorMsg = Err1 + Err2 + Err3;
    	    
    		JOptionPane.showMessageDialog(null, FullErrorMsg);
    	}
		
    	// Button
    	JButton create = new JButton("Attach prescription");
		create.addActionListener(this);
		// manually adding an ID
		create.setActionCommand("create");
		this.add(create);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int row;
	
		row = table.getSelectedRow();
        // Error handling
        if (row == -1){
        	JOptionPane.showMessageDialog(null, "Select an appointment!");  
        }else{	            	
        	IDApp  = (Integer) table.getValueAt(row, 0); 
        	NameApp = (String) table.getValueAt(row, 1);
    		CreatePres p = new CreatePres();
        }	

		
	}


}
