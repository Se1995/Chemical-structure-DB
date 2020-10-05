import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel  {
	private JButton logP;
	private JButton logD;
	
	private StringListener textListener;
	
	public Toolbar () {
		logP = new JButton ("logP: ");
		logD = new JButton ("logP: ");
		

		setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		add(logP);
		add(logD);

	}
		
		public void setStringListener(StringListener listener) {
			this.textListener = listener;
		
		
		
	}


	
	
}
