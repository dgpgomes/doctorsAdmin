import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;



public class CreateApp extends JFrame implements ActionListener{

												 
	FlowLayout fl_p1 = new FlowLayout();
	JPanel p1 = new JPanel(fl_p1);
	FlowLayout fl_p2 = new FlowLayout();
	JPanel p2 = new JPanel(fl_p2);
	JPanel p3 = new JPanel(new FlowLayout());
	FlowLayout fl_p4 = new FlowLayout();
	JPanel p4 = new JPanel(fl_p4);
	JPanel p5 = new JPanel(new FlowLayout());
	FlowLayout fl_p6 = new FlowLayout();
	JPanel p6 = new JPanel(fl_p6);
	JPanel p7 = new JPanel(new FlowLayout());
	FlowLayout fl_p8 = new FlowLayout();
	JPanel p8 = new JPanel(fl_p8);
	JPanel p9 = new JPanel(new FlowLayout());
	FlowLayout fl_p10 = new FlowLayout();
	JPanel p10 = new JPanel(fl_p10);
	JPanel p11 = new JPanel(new FlowLayout());
	JPanel p12 = new JPanel(new FlowLayout());
	
	// Declaring text fields
	JTextField name_tf = null;
	JTextField age_tf = null;
	JTextField date_tf = null;
	JTextField address_tf = null;
	JTextField contact_tf = null;
	
	
	
	public CreateApp() {
		fl_p10.setAlignment(FlowLayout.LEFT);
		fl_p8.setAlignment(FlowLayout.LEFT);
		fl_p6.setAlignment(FlowLayout.LEFT);
		fl_p4.setAlignment(FlowLayout.LEFT);
		fl_p2.setAlignment(FlowLayout.LEFT);
		
		// Setting size and proprieties for the jframe
		setSize(486,286);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

		GridLayout g = new GridLayout(6,2);
		getContentPane().setLayout(g);
		
		
		// Adding labels and fields into the panels
		getContentPane().add(p1);		
		JLabel name = new JLabel("Name:");
		p1.add(name);		
		
		getContentPane().add(p2);
		name_tf = new JTextField(17);
		p2.add(name_tf);				
		
		getContentPane().add(p3);
		JLabel age = new JLabel("Age:");
		p3.add(age);		
		
		getContentPane().add(p4);
		age_tf = new JTextField(3);
		p4.add(age_tf);
		
		getContentPane().add(p5);
		JLabel date = new JLabel("App. date:");
		p5.add(date);	

		getContentPane().add(p6);
		date_tf = new JTextField(10);
		p6.add(date_tf);
				
		getContentPane().add(p7);
		JLabel address = new JLabel("Address:");
		p7.add(address);	
		
		getContentPane().add(p8);
		address_tf = new JTextField(17);
		p8.add(address_tf);
		
		getContentPane().add(p9);
		JLabel contact = new JLabel("Contact:");
		p9.add(contact);		
		
		getContentPane().add(p10);
		contact_tf = new JTextField(12);
		p10.add(contact_tf);

		
		// Buttons
		getContentPane().add(p11);
		JButton ok = new JButton("Ok");
		ok.addActionListener(this);
		// manually adding an ID
		ok.setActionCommand("ok");
		p11.add(ok);
		
		getContentPane().add(p12);
    	JButton cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		// manually adding an ID
		cancel.setActionCommand("cancel");
		p12.add(cancel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")) {
						
			int id = 0;
			// Get the text from the fields and put into variables
			String name = name_tf.getText(); 
			String age = age_tf.getText();
			String date = date_tf.getText();
			String address = address_tf.getText();
			String contact_num = contact_tf.getText();
			
		
			Connection conn = null;
			Statement stmt = null;
	    	ResultSet rs = null;
			
	    	// Error handling, if fields are empty
			if (name.isEmpty() || age.isEmpty() || date.isEmpty() || address.isEmpty() || contact_num.isEmpty() ) {
    	    	JOptionPane.showMessageDialog(null,"You must fill all fields!", "Attention!", JOptionPane.WARNING_MESSAGE);	
			} 
			else {
				// Starts connection and insert
				try {
		    		conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gui_proj?" + "user=root&password=root");
			    	stmt = conn.createStatement();
			    	String qr = ("insert into appointments (id, name, age, date, address, contact_num) VALUES " +
			    				 "(null,'" + name + "','" + age + "','" + date + "','" + address + 
			    				 "','" + contact_num + "');");
			    	System.out.print(qr);
			    	stmt.execute(qr);
			    	
			    	// Clear fields
					name_tf.setText(""); 
					age_tf.setText("");
					date_tf.setText("");
					address_tf.setText("");
					contact_tf.setText("");
			    	
		        	JOptionPane.showMessageDialog(null, "Appointment added!"); 
					
					// Starts updating jtable after insert
	        	    String sql = "select * from appointments;";
	        	    System.out.println(sql);
	        	    
	        	    rs = stmt.executeQuery(sql);
	      	    
	            	String[] columnNames = {"ID", "Name", "Age", "Date", "Address", "Number"};  
	        		
            	
	            	DefaultTableModel aModel = (DefaultTableModel) RecpView.table.getModel();
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
	            	RecpView.table.setModel(aModel);	 
	            	
	            	// Adjusts column size
	            	TableColumn column = null;
	                for (int i = 0; i < 5; i++) {
	                    column = RecpView.table.getColumnModel().getColumn(i);
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
	     	        	    			    		    			    	
		    	} 
				catch(SQLException Err ) {
		    	    // Exception handling
		    	    String Err1 = "SQLException: " + Err.getMessage() + " \n";
		    	    String Err2 = "SQLState: " + Err.getSQLState() + " \n";
		    	    String Err3 = "VendorError: " + Err.getErrorCode() + " ";
		    		String FullErrorMsg = Err1 + Err2 + Err3;
		    	    
		    		JOptionPane.showMessageDialog(null, FullErrorMsg);
		    	}							
			}
		
		}
		// Closes
		if (e.getActionCommand().equals("cancel")) {
			dispose();				
		}	

		 
	}
}
