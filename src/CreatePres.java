import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;



public class CreatePres extends JFrame implements ActionListener{

												 
	JTextArea textArea = new JTextArea();
	
	public CreatePres() {
		// Proprieties of the jframe
		setSize(394,257);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

		FlowLayout fl = new FlowLayout();
		
		getContentPane().setLayout(fl);
		
		// Gets the name of the patient selected on previous screen
		JLabel name = new JLabel("Patient: " + DocView.NameApp);
		getContentPane().add(name);
		// Creates text area for prescription
		textArea.setLineWrap(true);
		getContentPane().add(textArea);
		textArea.setRows(10);
		textArea.setColumns(30);
		
		
		// Buttons
		JButton ok = new JButton("Ok");
		getContentPane().add(ok);
		ok.addActionListener(this);
		ok.setActionCommand("ok");
		
		JButton cancel = new JButton("Cancel");
		getContentPane().add(cancel);
		cancel.addActionListener(this);
		cancel.setActionCommand("cancel");
				
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Closes
		if (e.getActionCommand().equals("cancel")) {
			dispose();				
		}		
		
		if (e.getActionCommand().equals("ok")) {
			Connection conn = null;
			Statement stmt = null;
			// Gets ID from appointment selected on previous screen
			int ID = DocView.IDApp;
			// Gets Patient from the appointment selected on previous screen
			String Name = DocView.NameApp;
			String Presc = textArea.getText();
			
			try {
				// Starts connection and insert into the prescription table, using the IDApp as Foreign Key
	    		conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gui_proj?" + "user=root&password=root");
		    	stmt = conn.createStatement();
		    	String qr = ("insert into prescription (id, app_id, prescription) VALUES " +
		    				 "(null,'" + ID + "','" + Presc + "');");
		    	stmt.execute(qr);
		    	// Notifies success and closes
		    	JOptionPane.showMessageDialog(null, "Prescription attached to patient " + Name);
		    	dispose();		    	
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
}
