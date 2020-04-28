package csomag;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Program extends JFrame {

	private String selectedFile = "";
	private File sourceFile, destFile;
	private JPanel contentPane;
	private JTextField textCurFolder;
	private java.io.File CurDir;
	private String CurDirText = "";
	private String separator = System.getProperty("file.separator");
	private JTextField textNewDirName;
	private JTextField textNewFileName;
	private JTextArea textNewFileContent;
	private JTextField textSource;
	private JTextField textDestination;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program frame = new Program();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public void copyFile(File source, File dest) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int lenght;
			while ((lenght = is.read(buffer)) > 0) {
				os.write(buffer, 0, lenght);
			}
			is.close();
			os.close();
		} catch (Exception e) {
			SM("copyFile: "+e.getMessage(), 0);
		}
	}
	
	public void SM(String msg, int type) {
		JOptionPane.showMessageDialog(Program.this, msg, "FileSystem Manager message", type);
	}
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	
	public void CreateFile(JTextArea jta, String fileName) {
		fileName = CurDir.getAbsolutePath()+separator+fileName;
		try {
			PrintStream out = new PrintStream(new FileOutputStream(fileName));
			out.print(jta.getText());
			out.close();
			SM("Data is written to file!", 1);
		} catch (IOException ioe) {
			SM("CreateFile Method: "+ioe.getMessage(), 0);
		}
		
	}
	
	public int leftCornerX() {
		Point bs = getLocation();
		int bsx = (int)bs.getX();
		return bsx;
	}
	
	public int leftCornerY() {
		Point bs = getLocation();
		int bsy = (int)bs.getY();
		return bsy;
	}
	
	public Program() {
		
		setTitle("FileSystem Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Current Folder:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 11, 94, 14);
		contentPane.add(lblNewLabel);
		
		CurDir = new File(System.getProperty("user.dir"));
		CurDirText = CurDir.getAbsolutePath();
		
		textCurFolder = new JTextField(CurDirText);
		textCurFolder.setBackground(Color.WHITE);
		textCurFolder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textCurFolder.setEditable(false);
		textCurFolder.setBounds(114, 9, 640, 20);
		contentPane.add(textCurFolder);
		textCurFolder.setColumns(10);
		
		JButton btnList = new JButton("List");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentList ctl1 = new ContentList(Program.this, CurDir, 1);
				ctl1.setVisible(true);
			}
		});
		btnList.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnList.setBounds(10, 36, 94, 23);
		contentPane.add(btnList);
		
	
		
		JButton btnChangeUpFolder = new JButton("Change Up \u2191 Folder");
		btnChangeUpFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CurDir = CurDir.getParentFile();
					CurDirText = CurDir.getAbsolutePath();
					textCurFolder.setText(CurDirText);
				} catch (Exception ex) {
					SM("You're on the top dir!\nYou can't go higher!", 0);
				}
			}
		});
		btnChangeUpFolder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnChangeUpFolder.setBounds(151, 36, 176, 23);
		contentPane.add(btnChangeUpFolder);
		
		JButton btnChangeChildFolder = new JButton("Change Child \u2193 Folder");
		btnChangeChildFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentList ctl2 = new ContentList(Program.this, CurDir, 2);
				ctl2.setVisible(true);
				String outDir = ctl2.getOutDir();
				CurDir = new File(CurDir.getPath() + separator + outDir);
				CurDirText = CurDir.getAbsolutePath();
				textCurFolder.setText(CurDirText);
			}
		});
		btnChangeChildFolder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnChangeChildFolder.setBounds(350, 36, 176, 23);
		contentPane.add(btnChangeChildFolder);
		
		JButton btnCreateNewFolder = new JButton("Create New Folder");
		btnCreateNewFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newDir = RTF(textNewDirName);
				if (newDir.length() == 0) SM("New Dir Name field is empty!", 0);
				else {
					File temp = new File(CurDir.getPath() + separator + newDir);
					if (temp.exists()) SM("A folder with this name already exists!", 0);
					else {
						temp.mkdir();
						SM("Folder created!", 1);
						textNewDirName.setText(null);
					}
				}
			}
		});
		btnCreateNewFolder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCreateNewFolder.setBounds(598, 87, 156, 23);
		contentPane.add(btnCreateNewFolder);
		
		textNewDirName = new JTextField("");
		textNewDirName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textNewDirName.setColumns(10);
		textNewDirName.setBackground(Color.WHITE);
		textNewDirName.setBounds(444, 88, 144, 20);
		contentPane.add(textNewDirName);
		
		JLabel lblNewFolderName = new JLabel("New Folder Name:");
		lblNewFolderName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewFolderName.setBounds(444, 70, 126, 14);
		contentPane.add(lblNewFolderName);
		
		JButton btnDeleteCurFolder = new JButton("Delete Cur. Folder");
		btnDeleteCurFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File newCurDir = CurDir.getParentFile();
				boolean ok = CurDir.delete();
				if (!ok) SM("Folder is not empty,\ncannot be deleted!", 0);
				else {
					CurDir = newCurDir;
					CurDirText = CurDir.getAbsolutePath();
					textCurFolder.setText(CurDirText);
					SM("The current folder has become the parent folder!", 1);
				}
			}
		});
		btnDeleteCurFolder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDeleteCurFolder.setBounds(598, 121, 156, 23);
		contentPane.add(btnDeleteCurFolder);
		
		JLabel lblNewFileName = new JLabel("New File Name:");
		lblNewFileName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewFileName.setBounds(444, 158, 126, 14);
		contentPane.add(lblNewFileName);
		
		textNewFileName = new JTextField("");
		textNewFileName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textNewFileName.setColumns(10);
		textNewFileName.setBackground(Color.WHITE);
		textNewFileName.setBounds(444, 179, 144, 20);
		contentPane.add(textNewFileName);
		
		JButton btnCreateNewFile = new JButton("Create New File");
		btnCreateNewFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fileName = textNewFileName.getText();
				if (fileName.length() == 0) SM("File name is missing!", 0);
				else {
					CreateFile(textNewFileContent, fileName);
					textNewFileName.setText(null);
					textNewFileContent.setText(null);
					SM("File created!", 1);
				}
			}
		});
		btnCreateNewFile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCreateNewFile.setBounds(598, 178, 156, 23);
		contentPane.add(btnCreateNewFile);
		
		JLabel lblNewFileContent = new JLabel("New File Content:");
		lblNewFileContent.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewFileContent.setBounds(10, 70, 126, 14);
		contentPane.add(lblNewFileContent);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 87, 394, 112);
		contentPane.add(scrollPane);
		
		JTextArea textNewFileContent = new JTextArea();
		scrollPane.setViewportView(textNewFileContent);
		
		JButton btnDeleteFile = new JButton("Delete File");
		btnDeleteFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentList ctl3 = new ContentList(Program.this, CurDir, 3);
				ctl3.setVisible(true);
			}
		});
		btnDeleteFile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDeleteFile.setBounds(598, 212, 156, 23);
		contentPane.add(btnDeleteFile);
		
		JLabel lblCopyFile = new JLabel("Copy file:");
		lblCopyFile.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCopyFile.setBounds(10, 210, 94, 14);
		contentPane.add(lblCopyFile);
		
		JLabel lblSelectThe = new JLabel("1. Select the source folder as the Current Dir");
		lblSelectThe.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelectThe.setBounds(10, 235, 394, 14);
		contentPane.add(lblSelectThe);
		
		JLabel lblSelectThe_2 = new JLabel("2. Select the file from folder, with Select File button");
		lblSelectThe_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelectThe_2.setBounds(10, 260, 394, 14);
		contentPane.add(lblSelectThe_2);
		
		JLabel lblSelectThe_2_1 = new JLabel("3. Select the destination folder for the current folder");
		lblSelectThe_2_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelectThe_2_1.setBounds(10, 285, 394, 14);
		contentPane.add(lblSelectThe_2_1);
		
		JLabel lblSelectThe_2_1_1 = new JLabel("4. Press the Select button to select the current folder as the destination folder");
		lblSelectThe_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelectThe_2_1_1.setBounds(10, 310, 463, 14);
		contentPane.add(lblSelectThe_2_1_1);
		
		JLabel lblSelectThe_2_1_1_1 = new JLabel("5. Press Copy File button to copy");
		lblSelectThe_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSelectThe_2_1_1_1.setBounds(10, 335, 394, 14);
		contentPane.add(lblSelectThe_2_1_1_1);
		
		JLabel lblSourceFolder = new JLabel("Source: Folder + File");
		lblSourceFolder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSourceFolder.setBounds(10, 370, 144, 14);
		contentPane.add(lblSourceFolder);
		
		textSource = new JTextField("");
		textSource.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textSource.setColumns(10);
		textSource.setBackground(Color.WHITE);
		textSource.setBounds(10, 395, 394, 20);
		contentPane.add(textSource);
		
		JLabel lblDestinationFolderfile = new JLabel("Destination Folder (+ File)");
		lblDestinationFolderfile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDestinationFolderfile.setBounds(10, 426, 176, 14);
		contentPane.add(lblDestinationFolderfile);
		
		textDestination = new JTextField("");
		textDestination.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textDestination.setColumns(10);
		textDestination.setBackground(Color.WHITE);
		textDestination.setBounds(10, 451, 394, 20);
		contentPane.add(textDestination);
		
		JButton btnSelectFile = new JButton("Select File");
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Choose a file to copy");
				int returnValue = jfc.showOpenDialog(Program.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					sourceFile = jfc.getSelectedFile();
					selectedFile = sourceFile.getName();
					textSource.setText(sourceFile.getAbsolutePath());
				}
				
				/*ContentList ctl4 = new ContentList(Program.this, CurDir, 4);
				ctl4.setVisible(true);
				selectedFile = ctl4.getOutFile();
				sourceFile = new File(CurDir.getPath()+separator+selectedFile);
				textSource.setText(sourceFile.getAbsolutePath());*/
			}
		});
		btnSelectFile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSelectFile.setBounds(414, 395, 176, 23);
		contentPane.add(btnSelectFile);
		
		JButton btnCopyFile = new JButton("Copy File");
		btnCopyFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textSource.getText().length() == 0) SM("No file selected!", 0);
				else if (textDestination.getText().length() == 0) SM("No destination folder selected!", 0);
				else {
					copyFile(sourceFile, destFile);
					SM("File copied!", 1);
				}
			}
		});
		btnCopyFile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCopyFile.setBounds(660, 422, 94, 23);
		contentPane.add(btnCopyFile);
		
		JButton btnSelectDestFolder = new JButton("Select Dest. Folder");
		btnSelectDestFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Choose a directory to save your file");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int returnValue = jfc.showSaveDialog(Program.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					destFile = new File(jfc.getSelectedFile()+separator+selectedFile);
					textDestination.setText(destFile.getAbsolutePath());
				}
				
				/*destFile = new File(CurDir.getPath()+separator+selectedFile);
				textDestination.setText(destFile.getAbsolutePath());*/
			}
		});
		btnSelectDestFolder.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSelectDestFolder.setBounds(414, 451, 176, 23);
		contentPane.add(btnSelectDestFolder);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Help he = new Help(leftCornerX(), leftCornerY());
				he.setVisible(true);
			}
		});
		btnHelp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnHelp.setBounds(679, 36, 75, 23);
		contentPane.add(btnHelp);
	}
}
