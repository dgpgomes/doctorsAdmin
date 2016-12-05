import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;



public class Presc extends JFrame{

	// Creates a text area										 
	JTextArea textArea = new JTextArea();
	
	public Presc() {
		// Setting size and proprieties for jframe
		setSize(400,270);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

		FlowLayout fl = new FlowLayout();
		
		getContentPane().setLayout(fl);
		
		// Sets proprieties for the text area (will only show)
		textArea.setEditable(false);						
		textArea.setLineWrap(true);
		getContentPane().add(textArea);
		textArea.setRows(14);
		textArea.setColumns(30);
		textArea.setText(PharmView.Presc);
				
	}

}
