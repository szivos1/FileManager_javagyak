package csomag;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

public class Help extends JDialog {

	public Help(int xk, int yk) {
		setTitle("FileSystem Manager Help by Ádám");
		getContentPane().setBackground(new Color(60, 155, 111));
		setBounds(xk+790, yk, 450, 550);
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("EXIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(175, 488, 89, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Manage Folders");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 11, 128, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblManageFiles = new JLabel("Manage Files");
		lblManageFiles.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblManageFiles.setBounds(10, 252, 128, 14);
		getContentPane().add(lblManageFiles);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 414, 180);
		getContentPane().add(scrollPane);
		
		JTextArea textManFold = new JTextArea();
		textManFold.setBackground(new Color(230, 240, 230));
		scrollPane.setViewportView(textManFold);
		textManFold.setFont(new Font("Monospaced", Font.PLAIN, 11));
		
		textManFold.append("- When you start the program, the current folder, where you\n	started it\n");
		textManFold.append("- Press the List button to display the entire contents of the folder\n");
		textManFold.append("	- When you select a file, press View to view its contents\n");
		textManFold.append("	- The content viewer is the program associated with that extention\n	int the operating system\n");
		textManFold.append("- You can move up one level with the Change Up Folder button\n");
		textManFold.append("- Go down one level:\n");
		textManFold.append("	- Click the Change Child Folder button\n");
		textManFold.append("	- The subfolders appear in the list\n");
		textManFold.append("	- Select the appropriate subfolder\n");
		textManFold.append("	- Click the Change button\n\n");
		textManFold.append("- Create new folder:\n");
		textManFold.append("	- The new folder will always be created bellow the current folder, so the\n	appropriate parent folder should be selected\n");
		textManFold.append("	- Enter a name for the new folder\n");
		textManFold.append("	- Click the Create New Folder button\n");
		textManFold.append("	- If a folder exists, an Error Message will be displayed\n\n");
		textManFold.append("- Delete folder:\n");
		textManFold.append("	- Only the current folder can be deleted, so the appropriate folder must\n	be selected\n");
		textManFold.append("	- Only empty folders can be deleted, if not empty, Error Message will\n	be displayed\n");
		textManFold.append("	- To delete, press Delete Cur. Folder button");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 277, 414, 180);
		getContentPane().add(scrollPane_1);
		
		JTextArea textManFile = new JTextArea();
		textManFile.setBackground(new Color(230, 240, 230));
		scrollPane_1.setViewportView(textManFile);
		textManFile.setFont(new Font("Monospaced", Font.PLAIN, 11));
		
		textManFile.append("- Create new file:\n");
		textManFile.append("	- The file is created in teh current folder, so the appropriate\n	folder must be selected\n");
		textManFile.append("	- A new file can be created with content by typing the content into the\n	New File Content window\n");
		textManFile.append("	- New file can be created without content (blank), no need to specify\n	it's content\n");
		textManFile.append("	- Create: enter a name for teh new file and press the Create New File\n	button\n\n");
		textManFile.append("- Delete file\n");
		textManFile.append("	- A file can be deleted from the current folder, so the appropriate folder\n	must be selected\n");
		textManFile.append("	- Press the Delete File button\n");
		textManFile.append("	- The files in the folder are displayed on the panel\n");
		textManFile.append("	- A file must be selected\n");
		textManFile.append("	- Press the Delete key\n\n");
		textManFile.append("- Copy file\n");
		textManFile.append("	- The file can be copied from the current folder, so the appropriate\n	folder must be selected\n");
		textManFile.append("	- Press the Select File button\n");
		textManFile.append("	- In the panel, you need to select the file to copy\n");
		textManFile.append("	- Press the Select to Copy button\n");
		textManFile.append("	- The absolute name of the file is displayed in the Source field\n");
		textManFile.append("	- Select the destination folder for the current folder\n");
		textManFile.append("	- You have to press Select Dest. Folder button\n");
		textManFile.append("	- The absolute name of the file is displayed in the Destination Folder\n	field\n");
		textManFile.append("	- The copy is made when the Copy File button is pressed");
		
	}
}
