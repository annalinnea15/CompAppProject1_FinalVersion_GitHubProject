import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Choice;

import javax.swing.JToolBar;

import java.awt.GridLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.JMenu;

import net.miginfocom.swing.MigLayout;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class GUI extends JFrame {

	static GUI frame = new GUI();
	private JPanel contentPane;
	private final Action action = new OpenEQ();
	private JTable table;
	private JTabbedPane tabbedPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
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
	public GUI() {
		//set size of the window
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0,screen.width,screen.height - 30);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 499, 321);
		
		//add a menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File"); //menu option is to load files
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenEqFile = new JMenuItem("Open EQ and SSH file");
		mntmOpenEqFile.setAction(action);
		
		//set main picture
		mnFile.add(mntmOpenEqFile);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		Image img0 = new ImageIcon(this.getClass().getResource("/back.jpg")).getImage();
		
		//add tabs to window (Home and Ybus)
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		contentPane.add(tabbedPane, gbc_tabbedPane);
		
		//add labels to tabs
		JLabel label_1 = new JLabel("");
		tabbedPane.addTab("Home", null, label_1, null);
		label_1.setIcon(new ImageIcon(img0));
		
		//add table to Ybus tab
		table = new JTable();
		table.setToolTipText("Y Bus Matrix");
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		tabbedPane.addTab("Y Bus Matrix", null, table, null);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

	}

	
//select EQ file
	private class OpenEQ extends AbstractAction {
		//File EQinput ;
		
		//writing on menu option
		public OpenEQ() {
			putValue(NAME, "Open EQ and SSH file");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		//when menu option is clicked
		public void actionPerformed(ActionEvent e) {
			//OpenSSH SSHfile = new OpenSSH();
			JFileChooser fileChooserEQ = new JFileChooser();
			fileChooserEQ.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooserEQ.showOpenDialog(frame);

			if (result == JFileChooser.APPROVE_OPTION) {
				// user selects a file
				File EQinput = fileChooserEQ.getSelectedFile(); //open the file
				
				//read in the SSH
				JFileChooser fileChooserSSH = new JFileChooser();
				fileChooserSSH.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result1 = fileChooserSSH.showOpenDialog(frame);

				//if the files are read in, calculate the Ybus
				if (result1 == JFileChooser.APPROVE_OPTION) {
					// user selects a file
					File SSHinput = fileChooserSSH.getSelectedFile();

					//get the Ybus value
					Complex[][] Ybus = ParsingTotal.mainOperations(EQinput, SSHinput);
					showYbus(Ybus); //show the Ybus matrix in next tab
				}
			}
				
			
		}

		
		private void showYbus(Complex[][] ybus){

			Object[][] rowStr = new Object[ybus.length][ybus.length];
			String[] colStr = new String[ybus.length];
			
//			tabbedPane.addTab("Y Bus Matrix", null, grid, null);
			for(int m=0; m<ybus.length; m++){
	        	 for(int n=0; n<ybus.length; n++){
	        		 String totStr=matrixEle(m, n, ybus);
	        		 rowStr[m][n]=totStr;
	        	 }
	        	 colStr[m]="New column";
	         }
			table.setModel(new DefaultTableModel(rowStr, colStr));
			
		}
		
		private String matrixEle(int m, int n, Complex [] [] Ybus){
			//convert the complex value to a string
		Complex matComplex = Ybus[m][n];
   		 double re1 = matComplex.re();
   		 double im1 = matComplex.im();
   		 
   		 String reStr = Double.toString(re1);
   		 reStr = reStr.substring(0, Math.min(reStr.length(), 5));
   		 String imStr = Double.toString(im1);
   		 imStr = imStr.substring(0, Math.min(imStr.length(), 5));
   		 
   		 String totStr;
   		 if (im1 == 0) totStr = reStr + "";
   		 else if (re1 == 0) totStr = imStr + "i";
   		 else if (im1 <  0) totStr = reStr + imStr + "i";
   		 else if (im1==0 && re1==0) totStr = "0";
   	     else totStr = reStr + " + " + imStr + "i";
			return(totStr);
		}
	}
	
	
	
}
