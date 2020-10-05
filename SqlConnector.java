
import chemaxon.struc.PeriodicSystem;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import chemaxon.formats.MolExporter;
import chemaxon.formats.MolFormatException;
import chemaxon.formats.MolImporter;
import chemaxon.marvin.calculations.HBDAPlugin;
import chemaxon.marvin.calculations.RefractivityPlugin;
import chemaxon.marvin.calculations.TPSAPlugin;
import chemaxon.marvin.calculations.TopologyAnalyserPlugin;
import chemaxon.marvin.calculations.logDPlugin;
import chemaxon.marvin.calculations.logPPlugin;
import chemaxon.marvin.plugin.PluginException;
import chemaxon.struc.MolAtom;
import chemaxon.struc.MolBond;
import chemaxon.struc.Molecule;

public class SqlConnector {

	static Molecule mol = new Molecule();
	static String name;
	static Connection myConn = null;
	static Statement myStmt = null;
	static ResultSet myRs = null;
	static ResultSet myRs2 = null;
	static ArrayList<ArrayList> data;
	
	public static void main(String[] args) throws SQLException, IOException, PluginException {

		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://student@:3306/Chemolecules", "student@", "kratos95");
			myStmt = myConn.createStatement();
			// Statement myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from Storemolecule");
//			while (myRs.next()) {
//				String com = (myRs.getString("cd_structure"));
//				Scanner kiah = new Scanner(com);
//				name = kiah.nextLine();
//				mol = MolImporter.importMol(com);
//				double logp = getLogp(mol, name);
//				double logD = getLogD(mol);
//				double hbdapA = hbdapAcceptor(mol);
//				double hbdaD = hbdapDonor(mol);
//				double bond = bondCount(mol);
//				double polar = Tpsa(mol);
//				double ringCount = ringCount(mol);
//				double refractivity = refractivity(mol);
//				double atomCount = atomCount(mol);
//				double molWeight = myRs.getDouble("cd_molweight");
//				boolean lipinski = lipinskisRule(logp, hbdapA, hbdaD, molWeight);
//				boolean bioava = bioavailability(logp, hbdapA, hbdaD, bond, polar);
//				boolean leadLikeness = leadLikeness(logp, hbdapA, hbdaD, bond, polar, ringCount);
//				boolean ghoseFilter = ghoseFilter(molWeight, logp, refractivity, atomCount);
//			}
			// 2. Create a statement
			myStmt = myConn.createStatement();

			// 3. Execute SQL query
			// myRs = myStmt.executeQuery("select * from atom where CompoundID =
			// 'iodixanol'");

//			for (int i = 1; i < 100; i++) {
//				String com = "";
//				myRs = myStmt.executeQuery("select * from Storemolecule where cd_id=" + i);	

		} catch (Exception exc) {
			exc.printStackTrace();
		} 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();

			}
		});
		loadData();
	}

	public static void loadData() throws SQLException, PluginException, IOException {
		ArrayList<ArrayList> data = new ArrayList<>();
	try {	while (myRs.next()) {
			ArrayList<Object> row = new ArrayList<>();
			String com = (myRs.getString("cd_structure"));
			Scanner kiah = new Scanner(com);
			name = kiah.nextLine();
			mol = MolImporter.importMol(com);
			Double logp = getLogp(mol, name);
			Double logD = getLogD(mol);
			Double hbdapA = (double) hbdapAcceptor(mol);
			Double hbdaD = (double) hbdapDonor(mol);
			Double bond = (double) bondCount(mol);
			Double polar = Tpsa(mol);
			Double ringCount = ringCount(mol);
			Double refractivity = refractivity(mol);
			Double atomCount = atomCount(mol);
			Double molWeight = myRs.getDouble("cd_molweight");
			// boolean lipinski = lipinskisRule(logp, hbdapA, hbdaD, molWeight);
			Boolean bioava = bioavailability(logp, hbdapA, hbdaD, bond, polar);
			Boolean leadLikeness = leadLikeness(logp, hbdapA, hbdaD, bond, polar, ringCount);
			// boolean ghoseFilter = ghoseFilter(molWeight, logp, refractivity, atomCount);

			row.add(name);
			row.add(logp);
			row.add(logD);
			row.add(atomCount);
			row.add(bond);
			row.add(molWeight);
			row.add(hbdapA);
			row.add(ringCount);
			row.add(bioava);
			row.add(refractivity);

			data.add(row);

		}
		MainFrame.getTablePanel().SettingTable(data);
		SqlConnector.data= data;
		
	}finally {
		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}
	}
	
	public static void FilterlogP (double value) {
		ArrayList<ArrayList> filtered = new ArrayList<>();
		for (ArrayList row:data) {
			if (((Double)row.get(1))<=value){
				filtered.add(row);
			}
		}
		MainFrame.getTablePanel().SettingTable(filtered);

	}

	public static double getLogp(Molecule mol, String name) throws SQLException, PluginException, IOException {

		// Molecule mol = new Molecule();

		// fill parameters
		Properties params = new Properties();
		params.put("type", "logP");

		// create plugin
		logPPlugin plugin = new logPPlugin();

		// set parameters
		plugin.setCloridIonConcentration(0.2);
		plugin.setNaKIonConcentration(0.2);

		// set result types
		plugin.setUserTypes("logPTrue");

		// for each input molecule run the calculation and display the results

		// set the input molecule
		plugin.setMolecule(mol);

		// run the calculation
		plugin.run();

		// get the overall logP value
		double logp = plugin.getlogPTrue();

		// mi.close();
		String s1 = "";
		// Molexporter
		System.out.println(name);
		s1 = (String) MolExporter.exportToObject(mol, "smiles:a-H");
		System.out.println(s1);
		System.out.println();
		System.out.println("True logP : " + logp);
		System.out.println();
		// Molecule mol2 = MolImporter.importMol(s1);
		return logp;

		// set the input molecule
		// plugin.setMolecule(mol2);

		// run the calculation
		// plugin.run();

		// get the overall logP value
		// double logp2 = plugin.getlogPTrue();

		// System.out.println("True logP : " + logp2);
		// System.out.println();
		// return (logp2);

	}

	public static double getLogD(Molecule mol) throws SQLException, PluginException, IOException {
		// instantiate plugin
		logDPlugin plugin = new logDPlugin();

		// set pH
		plugin.setpH(7.4);

		// set the input molecule
		plugin.setMolecule(mol);

		// run the calculation
		plugin.run();

		// get result
		double logD = plugin.getlogD();

		// print result
		System.out.println();
		System.out.println("logD at pH 7.4: " + logD);
		return logD;
	}

	public static int hbdapAcceptor(Molecule mol) throws SQLException, PluginException, IOException {
		// create plugin
		HBDAPlugin plugin = new HBDAPlugin();

		// set plugin parameters
		plugin.setDoublePrecision(2);
		// plugin.setpHLower(2.0);
		// plugin.setpHUpper(12.0);
		// plugin.setpHStep(2.0);

		// optional: take major microspecies at pH=7.4
		// skip this if you want to calculate HBDA for the input molecule as it is
		plugin.setpH(7.4);

		// set target molecule
		plugin.setMolecule(mol);

		// run the calculation
		plugin.run();

		// get results

		// average acceptor/donor counts by microspecies distributions at given pH-s
//		 double[] pHs = plugin.getpHs();
//		 double[] msacc = plugin.getMsAcceptorCounts();
//		 double[] msdon = plugin.getMsDonorCounts();

		// without multiplicity
		int molecularAcceptorAtomCount = plugin.getAcceptorAtomCount();
		System.out.println();
		System.out.println("Acceptor count without multiplicity: " + molecularAcceptorAtomCount);
		return (molecularAcceptorAtomCount);

		// atomic data
		// System.out.println();
		// int count = mol.getAtomCount();
		// for (int i = 0; i < count; ++i) {

	}

	public static int hbdapDonor(Molecule mol) throws SQLException, PluginException, IOException {
		// create plugin
		HBDAPlugin plugin = new HBDAPlugin();

		// set plugin parameters
		plugin.setDoublePrecision(2);
		// plugin.setpHLower(2.0);
		// plugin.setpHUpper(12.0);
		// plugin.setpHStep(2.0);

		// optional: take major microspecies at pH=7.4
		// skip this if you want to calculate HBDA for the input molecule as it is
		plugin.setpH(7.4);

		// set target molecule
		plugin.setMolecule(mol);

		// run the calculation
		plugin.run();

		// get results

		// average acceptor/donor counts by microspecies distributions at given pH-s
//		 double[] pHs = plugin.getpHs();
//		 double[] msacc = plugin.getMsAcceptorCounts();
//		 double[] msdon = plugin.getMsDonorCounts();

		// without multiplicity
		int molecularDonorAtomCount = plugin.getDonorAtomCount();
		System.out.println();
		System.out.println("Donor count without multiplicity: " + molecularDonorAtomCount);
		return (molecularDonorAtomCount);

		// atomic data
		// System.out.println();
		// int count = mol.getAtomCount();
		// for (int i = 0; i < count; ++i) {

	}

	public static int bondCount(Molecule mol) throws SQLException, PluginException, IOException {

		// create plugin
		TopologyAnalyserPlugin plugin = new TopologyAnalyserPlugin();

		// set target molecule
		plugin.setMolecule(mol);

		// run the calculation
		plugin.run();

		// get molecular results
		int rotatableBondCount = plugin.getRotatableBondCount();
		System.out.println();
		System.out.println("Rotatable Bond Count: " + rotatableBondCount);
		return rotatableBondCount;
	}

	public static boolean lipinskisRule(double logP, double molecularAcceptorAtomCount, double molecularDonorAtomCount,
			double molWeight) throws SQLException, PluginException, IOException {

		if (logP <= 5 && molecularDonorAtomCount <= 5 && molecularAcceptorAtomCount <= 10 && molWeight < 2000) {
			System.out.println();
			// System.out.println("Log P value = " + logP);
			// System.out.println("molecular donor atom count = " +
			// molecularDonorAtomCount);
			// System.out.println("molecular acceptor atom count = " +
			// molecularAcceptorAtomCount);
			// System.out.println("Molecular Weight = " + queryResult);
			System.out.println("Lipinski's = TRUE");
			return true;
		} else {
			System.out.println();
			System.out.println("Lipinski's rule = FALSE");
			return false;

		}

		// evaluate -e (mass() <= 2000) && (logP() <= 5) && (donorCount() <= 5) &&
		// (acceptorCount() <= 10);

	}

	public static double Tpsa(Molecule mol) throws PluginException, IOException {

		// create plugin
		TPSAPlugin plugin = new TPSAPlugin();

		// optional take major microspecies at pH= 7.4
		plugin.setpH(7.4);

		// set target molecule
		plugin.setMolecule(mol);

		// run the calculation
		plugin.run();

		// get result
		double area = plugin.getSurfaceArea();

		return area;
	}

	public static int aromaticRing(Molecule mol) throws PluginException, IOException {
		// create plugin

		TopologyAnalyserPlugin plugin = new TopologyAnalyserPlugin();

		// set target molecule
		plugin.setMolecule(mol);

		// run the calculation
		plugin.run();

		// get result
		int FusedRingCount = plugin.getFusedAromaticRingCount();

		System.out.println("FusedAromatinRingCount = " + FusedRingCount);
		return FusedRingCount;
	}

	public static boolean bioavailability(double logP, double molecularAcceptorAtomCount,
			double molecularDonorAtomCount, double molWeight, double Tpsa)
			throws SQLException, PluginException, IOException {
		if (logP <= 5 && molecularDonorAtomCount <= 5 && molecularAcceptorAtomCount <= 10 && molWeight < 2000) {
			System.out.println();
			// System.out.println("Log P value = " + logP);
			// System.out.println("molecular donor atom count = " +
			// molecularDonorAtomCount);
			// System.out.println("molecular acceptor atom count = " +
			// molecularAcceptorAtomCount);
			// System.out.println("Molecular Weight = " + queryResult);
			// System.out.println("Polar surface aerea = " + polarFace);
			System.out.println("Bioavailability = TRUE");
			return true;
		} else {
			System.out.println();
			System.out.println("Bioavailability = FALSE");
			return false;
		}

		// evaluate -e (mass() <= 2000) && (logP() <= 5) && (donorCount() <= 5) &&
		// (acceptorCount() <= 10);

	}

	public static double ringCount(Molecule mol) throws SQLException, PluginException, IOException {
		// create plugin
		TopologyAnalyserPlugin pluginTopology = new TopologyAnalyserPlugin();

		// set target molecule
		pluginTopology.setMolecule(mol);

		// run the calculation
		pluginTopology.run();

		// get molecular results
		int ringCount = pluginTopology.getRingCount();

		// int smallestRingSize = pluginTopology.getSmallestRingSize();

		// System.out.println("Ring count: " + ringCount);

		return ringCount;
	}

	public static boolean leadLikeness(double logP, double molecularAcceptorAtomCount, double molecularDonorAtomCount,
			double molWeight, double Tpsa, double ringCount) throws SQLException, PluginException, IOException {
		if (logP <= 5 && molecularDonorAtomCount <= 5 && molecularAcceptorAtomCount <= 10 && molWeight < 2000) {
			System.out.println();
			// System.out.println("Log P value = " + logP);
			// System.out.println("molecular donor atom count = " +
			// molecularDonorAtomCount);
			// System.out.println("molecular acceptor atom count = " +
			// molecularAcceptorAtomCount);
			// System.out.println("Molecular Weight = " + queryResult);
			// System.out.println("Polar surface aerea = " + polarFace);
			System.out.println("LeadLikeness= TRUE");
			return true;
		} else {
			System.out.println();
			System.out.println("LeadLikeness = FALSE");
			return false;
		}

	}

	public static double atomCount(Molecule mol) throws SQLException, PluginException, IOException {

		// create plugin
		TopologyAnalyserPlugin plugin = new TopologyAnalyserPlugin();

		// set target molecule
		plugin.setMolecule(mol);

		// run the calculation
		plugin.run();

		// get molecular results
		int atomCount = plugin.getAtomCount();
		System.out.println();
		System.out.println("Atom Count: " + atomCount);
		return atomCount;
	}

	public static double refractivity(Molecule mol) throws SQLException, PluginException, IOException {
		// create plugin
		RefractivityPlugin plugin = new RefractivityPlugin();

		// set double precision for string conversion (optional)
		plugin.setDoublePrecision(3);

		// set target molecule
		plugin.setMolecule(mol);

		// run the calculation
		plugin.run();

		// get molecular refractivity
		double refractivity = plugin.getRefractivity();
		System.out.println("molecular refractivity: " + plugin.format(refractivity));
		return refractivity;
	}

	public static boolean ghoseFilter(double molWeight, double logP, double refractivity, double atomCount)
			throws SQLException, PluginException, IOException {
		if (logP <= 5.6 && logP >= -0.4 && molWeight >= 160 && molWeight <= 480 && atomCount >= 20 && atomCount <= 70
				&& refractivity >= 40 && refractivity <= 130) {
			System.out.println();
			System.out.println("ghoseFilter= TRUE");
			return true;
		} else {
			System.out.println();
			System.out.println("ghoseFilter = FALSE");
			return false;
		}
	}

}

// 4. Process the result set
// while (myRs.next()) {
// int atomicNumber =
// PeriodicSystem.findAtomicNumber(myRs.getString("AtomType"));
// double x = myRs.getDouble("xcoord");
// double y = myRs.getDouble("ycoord");
// double z = myRs.getDouble("zcoord");

// System.out.println(myRs.getString("CompoundID") + ", " +
// myRs.getString("xcoord") + ", "
// + myRs.getString("ycoord") + ", " + myRs.getString("zcoord"));

// MolAtom atom = new MolAtom(atomicNumber, x, y, z);
// mol.add(atom);
// }

// 3. Execute SQL query
// myRs2 = myStmt.executeQuery("select * from bond where CompoundID =
// 'iodixanol'");

// MolAtom[] atomList = mol.getAtomArray();
// 4. Process the result set
// while (myRs2.next()) {
// int bondO = myRs2.getInt("BondOrder");
// int atom1 = myRs2.getInt("Atom1Number");
// int atom2 = myRs2.getInt("Atom2Number");

// System.out.println(myRs2.getString("CompoundID") + ", " +
// myRs2.getString("BondOrder") + ", "
// + myRs2.getString("Atom1Number") + ", " + myRs2.getString("Atom2Number"));

// MolBond bond = new MolBond(atomList[atom1 - 1], atomList[atom2 - 1], bondO);
// mol.add(bond);
// }

//		}
