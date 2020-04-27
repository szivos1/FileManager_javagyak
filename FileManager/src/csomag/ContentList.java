package csomag;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.table.TableColumn;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.Font;

public class ContentList extends JDialog {
	private JTable table;
	private ListTM ltm;
	private String outDir = "";
	private String outFile = "";
	private String separator = System.getProperty("file.separator");
	
	public String getOutFile() {
		return outFile;
	}
	
	public void SM(String msg, int type) {
		JOptionPane.showMessageDialog(ContentList.this, msg, "FileSystem Manager message", type);
	}
	
	public String getOutDir() {
		return outDir;
	}

	public ContentList(JFrame f, File inDir, int mod) {
		super(f, " List of folder entries", true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		JButton btnExit = new JButton("EXIT");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		JButton btnSelectToCopy = new JButton("Select to Copy");
		btnSelectToCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int db = 0, jel = 0, x = 0;
				for (x = 0; x < ltm.getRowCount(); x++)
				if ((Boolean)ltm.getValueAt(x, 0)) {db++; jel = x;}
				if (db == 0) SM("No file selected!", 0);
				
				if (db > 1) SM("Multiple rows selected!\nSelect only one!", 0);
				if (db == 1) {
					outFile = ltm.getValueAt(jel, 1).toString();
					dispose();
				}
			}
		});
		btnSelectToCopy.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSelectToCopy.setBounds(10, 227, 130, 23);
		getContentPane().add(btnSelectToCopy);
		btnExit.setBounds(335, 227, 89, 23);
		getContentPane().add(btnExit);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 205);
		getContentPane().add(scrollPane);

		Object emptmn[] = { "Check", "Object name", "Dir" };
		ltm = new ListTM(emptmn, 0);

		File[] list = inDir.listFiles();
		for (int i = 0; i < list.length; i++) {
			boolean isd = list[i].isDirectory();
			if (mod == 1) {
				ltm.addRow(new Object[] { false, list[i].getName(), isd });
			}
			if (mod == 2 && isd) {
				ltm.addRow(new Object[] {false, list[i].getName(), isd});
			}
			if (mod == 3 && !isd) {
				ltm.addRow(new Object[] {false, list[i].getName(), isd});
			}
			if ((mod == 3 || mod == 4) && !isd) {
				ltm.addRow(new Object[] {false, list[i].getName(), isd});
			}
		}

		table = new JTable(ltm);
		scrollPane.setViewportView(table);
		
		JButton btnChange = new JButton("Change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int db = 0, jel = 0, x = 0;
				for (x = 0; x < ltm.getRowCount(); x++)
				if ((Boolean)ltm.getValueAt(x, 0)) {db++; jel = x;}
				if (db == 0) SM("No folder selected!", 0);
				if (db > 1) SM("Multiple folders selected!\nOnly one is allowed", 0);
				if (db == 1) {
					outDir = ltm.getValueAt(jel, 1).toString();
					dispose();
				}
			}
		});
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnChange.setBounds(229, 227, 89, 23);
		getContentPane().add(btnChange);
		
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int db = 0, jel = 0, x = 0;
				for (x = 0; x < ltm.getRowCount(); x++)
				if ((Boolean)ltm.getValueAt(x, 0)) {db++; jel = x;}
				if (db == 0) SM("No data row selected!", 0);
				
				if (db > 1) SM("Multiple rows selected!\nSelect only one!", 0);
				if (db == 1) {
					if ((Boolean)ltm.getValueAt(jel, 2)) SM("The selected entry is not a file!", 0);
					else {
						String fileName = ltm.getValueAt(jel, 1).toString();
						File file = new File(inDir.getAbsolutePath()+separator+fileName);
						try {
							Desktop.getDesktop().open(file);
						} catch (IOException ex) {
							SM("No proper application to open this file", 0);
						}
					}
				}
			}
		});
		btnView.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnView.setBounds(117, 227, 89, 23);
		getContentPane().add(btnView);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int db = 0, jel = 0, x = 0;
				for (x = 0; x < ltm.getRowCount(); x++)
				if ((Boolean)ltm.getValueAt(x, 0)) {db++; jel = x;}
				if (db == 0) SM("No file selected!", 0);
				
				if (db > 1)
					SM("Multiple rows selected!\nSelect only one!", 0);
				if (db == 1) {
					String fileName = ltm.getValueAt(jel, 1).toString();
					File delFile = new File(inDir.getAbsolutePath() + separator + fileName);
					boolean ok = delFile.delete();
					if (ok) {
						ltm.removeRow(jel);
						SM("File deleted!", 1);
					}
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDelete.setBounds(10, 227, 89, 23);
		getContentPane().add(btnDelete);
		
		if (mod == 1) btnView.setVisible(true);
		else btnView.setVisible(false);
		
		if (mod == 2) btnChange.setVisible(true);
		else btnChange.setVisible(false);
		
		if (mod == 3) btnDelete.setVisible(true);
		else btnDelete.setVisible(false);
		
		if (mod == 4) btnSelectToCopy.setVisible(true);
		else btnSelectToCopy.setVisible(false);
		
		TableColumn tc = null;
		for (int i = 0; i < 3; i++) {
			tc = table.getColumnModel().getColumn(i);
			if (i == 0 || i == 2) {
				tc.setPreferredWidth(20);
			} else {
				tc.setPreferredWidth(250);
			}
		}

	}
}
