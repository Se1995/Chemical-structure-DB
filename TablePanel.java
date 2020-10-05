import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TablePanel extends JPanel {

	private JTable table;
	private TableModel tableModel;

	public TablePanel() {
		table = new JTable();
		tableModel = new TableModel();

		setLayout(new BorderLayout());

		add(table, BorderLayout.CENTER);
	}

	public void SettingTable(ArrayList<ArrayList> data) {
		String[] columNames = new String[] {"Mol name", "logP","logD","NumAtoms", "NumBonds", "MolWeight", "AccCount","FusedRingCount",
				"Bioavailability","Refractivity" };
		TableColumn tableColumn = new TableColumn();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (int i=0;i<model.getRowCount();i++) {
			model.removeRow(0); 
			System.out.println("Hello two");
		}
		
		model.setColumnIdentifiers(columNames);
		for (int i = 0; i < data.size(); i++) {
			model.addRow(data.get(i).toArray());
		}

	}
}
