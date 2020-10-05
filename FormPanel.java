import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	private JLabel logPLabel;
	private JLabel logDLabel;
	private JLabel AcceptorAtomLabel;
	private JLabel DonorAtomLabel;
	private JLabel BondCountLabel;
	private JLabel TPSALabel;
	private JLabel RefractivityLabel;
	private JLabel AtomCountLabel;
	private JLabel RingCountLabel;
	private JLabel FusedAromaticRingLabel;

	private JTextField logPField;
	private JTextField logDField;
	private JTextField AcceptorAtomField;
	private JTextField DonorAtomField;
	private JTextField BondCountField;
	private JTextField TPSAField;
	private JTextField RefractivityField;
	private JTextField AtomCountField;
	private JTextField RingCountField;
	private JTextField FusedAromaticRingField;

	private JButton select;
	private JButton clear;

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 400;
		setPreferredSize(dim);
		logPLabel = new JLabel("logP: ");
		logDLabel = new JLabel("logD: ");
		AcceptorAtomLabel = new JLabel("Acceptor Atom Count: ");
		DonorAtomLabel = new JLabel("Donor Atom Count: ");
		BondCountLabel = new JLabel("Rotatable Bond Count: ");
		TPSALabel = new JLabel("TPSA: ");
		RefractivityLabel = new JLabel("Refractivity : ");
		AtomCountLabel = new JLabel("Atom Count: ");
		RingCountLabel = new JLabel("Ring Count: ");
		FusedAromaticRingLabel = new JLabel("Fused Aromatic Ring: ");

		logPField = new JTextField(10);
		logDField = new JTextField(10);
		AcceptorAtomField = new JTextField(10);
		DonorAtomField = new JTextField(10);
		BondCountField = new JTextField(10);
		TPSAField = new JTextField(10);
		RefractivityField = new JTextField(10);
		AtomCountField = new JTextField(10);
		RingCountField = new JTextField(10);
		FusedAromaticRingField = new JTextField(10);

		select = new JButton("Select");
		clear = new JButton("Clear");

		select.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SqlConnector.FilterlogP(5);
				System.out.println("Hello three");
			}

		});
		Border innerBorder = BorderFactory.createTitledBorder("Filter Parameters");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0.5;
		gc.weighty = 0.5;

		// First Row
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		gc.gridx = 0;
		gc.gridy = 0;
		add(logPLabel, gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(logPField, gc);

		gc.gridy = 1;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		add(logDLabel, gc);

		gc.gridy = 1;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(logDField, gc);

		gc.gridy = 2;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		add(AcceptorAtomLabel, gc);

		gc.gridy = 2;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(AcceptorAtomField, gc);

		gc.gridy = 3;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		add(DonorAtomLabel, gc);

		gc.gridy = 3;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(DonorAtomField, gc);

		gc.gridy = 4;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		add(BondCountLabel, gc);

		gc.gridy = 4;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(BondCountField, gc);

		gc.gridy = 5;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		add(TPSALabel, gc);

		gc.gridy = 5;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(TPSAField, gc);

		gc.gridy = 6;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		add(RefractivityLabel, gc);

		gc.gridy = 6;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(RefractivityField, gc);

		gc.gridy = 7;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		add(AtomCountLabel, gc);

		gc.gridy = 7;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(AtomCountField, gc);

		gc.gridy = 8;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		add(RingCountLabel, gc);

		gc.gridy = 8;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(RingCountField, gc);

		gc.gridy = 9;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 2);
		add(FusedAromaticRingLabel, gc);

		gc.gridy = 9;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(FusedAromaticRingField, gc);

		gc.gridy = 10;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_START;
		add(select, gc);

		gc.gridy = 10;
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		add(clear, gc);

	}

}