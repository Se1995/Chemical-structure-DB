import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {

	private static TextPanel textPanel;

	private static JButton select;
	private static FormPanel formPanel;
	private static Toolbar toolbar;
	private static TablePanel tablePanel;

	public MainFrame() {
		super("Chemoinformatics");

		setLayout(new BorderLayout());

		toolbar = new Toolbar();
		toolbar.setStringListener(new StringListener() {
			public void textEmitted(String text) {
				System.out.println(text);
			}

		});

		textPanel = new TextPanel();
		select = new JButton("Click me");
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		// toolbar.setTextPanel(textPanel);
		setJMenuBar(createMenubar());

		select.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				textPanel.appendText("Chemoinformatics\n");
				SqlConnector.FilterlogP(5);
				System.out.println("Hello three");
			}

		});

		// add(toolbar, BorderLayout.EAST);
		add(tablePanel, BorderLayout.CENTER);
		add(formPanel, BorderLayout.WEST);

		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private static JMenuBar createMenubar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu windowMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Export Molecule");
		JMenuItem importDataItem = new JMenuItem("Import Molecule");
		JMenuItem exportItem = new JMenuItem("Exit");

		windowMenu.add(exportDataItem);
		windowMenu.addSeparator();
		windowMenu.add(importDataItem);
		windowMenu.addSeparator();
		windowMenu.add(exportItem);
		windowMenu.addSeparator();

		JMenu fileMenu = new JMenu("Filter");
		JMenuItem LepinskisItem = new JMenuItem("Lepinski's rule");
		JMenuItem bioavailabilityItem = new JMenuItem("Bioavailability");
		JMenuItem leadlikenessItem = new JMenuItem("Lead-likeness");
		JMenuItem ghostFilterItem = new JMenuItem("Ghost-filter");

		fileMenu.add(LepinskisItem);
		fileMenu.addSeparator();
		fileMenu.add(bioavailabilityItem);
		fileMenu.addSeparator();
		fileMenu.add(leadlikenessItem);
		fileMenu.addSeparator();
		fileMenu.add(ghostFilterItem);
		fileMenu.addSeparator();

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		return menuBar;
	}

	public static TablePanel getTablePanel() {
		return tablePanel;
	}
}
