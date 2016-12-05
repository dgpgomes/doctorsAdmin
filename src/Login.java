import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;






import javax.swing.JTextField;

import java.sql.ResultSet;
import java.awt.Font;

public class Login extends JFrame implements ActionListener{
	
	// Declaring fields
	JTextField username = null;
	JTextField pass = null;
	
	public Login(){
		// Setting proprieties for the jframe
		setSize(205,140);
		setTitle("Super Doctors Ltda.");
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		validate();
		repaint();
		
		GridLayout gr = new GridLayout(4,1);
		getContentPane().setLayout(gr);
		
		JLabel msg = new JLabel(" Enter with your login details:");
		msg.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		getContentPane().add(msg);
		
		
		// Username field
		username = new JTextField(20);
		username.setToolTipText("");
		getContentPane().add(username);
		username.requestFocus();
		// Password field		
		pass = new JPasswordField(20);
		getContentPane().add(pass);
		
		// Button
		JButton loginBtn = new JButton("Login!");
		loginBtn.addActionListener(this);
		getContentPane().add(loginBtn);
		getRootPane().setDefaultButton(loginBtn);
		
		validate();
		repaint();
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
              new Login();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	    
	    
	    // Starts connection
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	try {
    		String Username = username.getText();
    		String Password = pass.getText();

    		conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gui_proj?" + "user=root&password=root");

    	    stmt = conn.createStatement();

    	    String sql = "select * from guilogin where username = '"+ Username +"' and password = '" + Password + "';";
    	    
    	    rs = stmt.executeQuery(sql);
 	    
    	    // Error handling
    	    if (Username.isEmpty() || Password.isEmpty()) {
    	    	JOptionPane.showMessageDialog(null,"You must fill both fields!", "Attention!", JOptionPane.WARNING_MESSAGE);	
    	    }    	        	    
    	    else if (!rs.next() ) {
    	    	JOptionPane.showMessageDialog(null,"Username or Password incorrect!", "Attention!", JOptionPane.WARNING_MESSAGE);	
    	    }
    	    
    	    rs.beforeFirst();
    	    // Loops over results
    	    while(rs.next()){
    	    	 	        
    	        String type = rs.getString("type");
    	        // Opens one of the windows depending on whoch type the user is
    	        if(type.equals("doc")){    	      
    	        	DocView d = new DocView();
    	        }
    	        else if(type.equals("pharm")){
    	        	PharmView p = new PharmView();
    	        }
    	        else if(type.equals("recp")){
    	        	RecpView r = new RecpView();
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

	}
 
}
