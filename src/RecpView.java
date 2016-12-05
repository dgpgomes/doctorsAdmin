import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class RecpView extends JFrame implements ActionListener{


	// TableModel definition
	static JTable table = new JTable();
	
	
	public RecpView(){
		// Setting proprieties for the jframe
		setSize(460,500);
		setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().setLayout(new FlowLayout());
		setResizable(false);

		// Starts connection
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
  	
    	try {

    		conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gui_proj?" + "user=root&password=root");
    	    stmt = conn.createStatement();
    	    // Selects all appointments
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

        		for(int x = 0; x < col ; x++){
        			objects[x] = rs.getObject (x + 1);
        		}
        		
        		aModel.addRow(objects);
        	}
        	table.setModel(aModel);
	
        	getContentPane().add(new JScrollPane(table));
        	
        	// Adjusts column size
        	TableColumn column = null;
            for (int i = 0; i < 5; i++) {
                column = table.getColumnModel().getColumn(i);
                if (i == 0) {
                    column.setPreferredWidth(20); //sport column is bigger
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
    		// Error handling
    	    String Err1 = "SQLException: " + Err.getMessage() + " \n";
    	    String Err2 = "SQLState: " + Err.getSQLState() + " \n";
    	    String Err3 = "VendorError: " + Err.getErrorCode() + " ";
    		String FullErrorMsg = Err1 + Err2 + Err3;
    	    
    		JOptionPane.showMessageDialog(null, FullErrorMsg);
    	}
		

		//Buttons
		
    	JButton create = new JButton("Create");
		create.addActionListener(this);
		create.setActionCommand("create");
		getContentPane().add(create);
		
    	JButton delete = new JButton("Delete");
		delete.addActionListener(this);
		delete.setActionCommand("delete");
		getContentPane().add(delete);		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("create")){
			CreateApp p = new CreateApp();			
		}	
		
		
		if(e.getActionCommand().equals("delete")){
					
			int row;
			row = table.getSelectedRow();
			// Error handling
            if (row == -1){
            	JOptionPane.showMessageDialog(null, "Select a record to delete!");  
            }
            else {	 
            	// Confirmation dialog
            	int selectedOption = JOptionPane.showConfirmDialog(null,"Delete entry?" , "Confirmation", 
            						 JOptionPane.YES_NO_OPTION); 

            	if (selectedOption == JOptionPane.YES_OPTION) {
            	
                	int id;
    				Connection conn = null;
    		    	Statement stmt = null;
    		    	ResultSet rs = null;
            	
    		        try{
    	        	 	// Starts connection and deletes the selected entry
    	            	id  = (Integer) table.getValueAt(row, 0); 
    	            	System.out.print(id);
    	            	
    	        		conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gui_proj?" + "user=root&password=root");
    	        	    stmt = conn.createStatement();
    	        	    
    	        	    String sql = "delete from appointments where id = " + id;
    	        	    System.out.println(sql);	        	    
    	        	    stmt.execute(sql);
    	            	            	
    	        	    // Updates after delete           	

    	        	    sql = "select * from appointments;";
    	        	    System.out.println(sql);
    	        	    
    	        	    rs = stmt.executeQuery(sql);
    	      	    
    	            	String[] columnNames = {"ID", "Name", "Age", "Date", "Address", "Number"};  
    	        		
                	
    	            	DefaultTableModel aModel = (DefaultTableModel) table.getModel();
    	            	aModel.setColumnIdentifiers(columnNames);
    	            	aModel.setRowCount(0);	
            	
            	
    	            	// Loop through the ResultSet and transfer in the Model
    	            	java.sql.ResultSetMetaData rsmd = rs.getMetaData();
    	            	int colNo = rsmd.getColumnCount();
    	            	while(rs.next()){
    	            		Object[] objects = new Object[colNo];

    	            		for(int i = 0; i < colNo ; i++){
    	            			objects[i] = rs.getObject (i + 1);
    	            		}
    	            		
    	            		aModel.addRow(objects);
    	            	}
    	            	table.setModel(aModel);	 
    	            	
    	            	// Adjust columns
    	            	TableColumn column = null;
    	                for (int i = 0; i < 5; i++) {
    	                    column = table.getColumnModel().getColumn(i);
    	                    if (i == 0) {
    	                        column.setPreferredWidth(20); //sport column is bigger
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
    	                       	            	
    		          } 
    			      catch (SQLException Err) {
    			    	  	// Error handling
    			    	    String Err1 = "SQLException: " + Err.getMessage() + " \n";
    			    	    String Err2 = "SQLState: " + Err.getSQLState() + " \n";
    			    	    String Err3 = "VendorError: " + Err.getErrorCode() + " ";
    			    		String FullErrorMsg = Err1 + Err2 + Err3;
    			    	    
    			    		JOptionPane.showMessageDialog(null, FullErrorMsg);
    		          }	           	
            	  	
            	}		
		              
					
            }
		
		}	
				
	}
}
