package game.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class MainWindow {

	private JFrame frmMainWindow;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	private JButton btnNewButton;
	private JTextArea textArea;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnDown;
	private JLabel lblNewLabel_1;
	private JButton btnExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmMainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMainWindow = new JFrame();
		frmMainWindow.setTitle("Main Window");
		frmMainWindow.setBounds(100, 100, 600, 600);
		frmMainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMainWindow.getContentPane().setLayout(new MigLayout("", "[300.00px,grow][grow][grow]", "[][][][grow][][][][grow][]"));
		
		JLabel lblNumberOfOgres = new JLabel("Number of ogres:");
		frmMainWindow.getContentPane().add(lblNumberOfOgres, "cell 0 0,alignx left");
		
		textField = new JTextField();
		frmMainWindow.getContentPane().add(textField, "cell 0 0");
		textField.setColumns(10);
		
		lblNewLabel = new JLabel("Guard personality:");
		frmMainWindow.getContentPane().add(lblNewLabel, "flowx,cell 0 1");
		
		comboBox = new JComboBox();
		comboBox.setToolTipText("text\n");
		frmMainWindow.getContentPane().add(comboBox, "cell 0 1");
		
		btnNewButton = new JButton("New Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		textArea = new JTextArea();
		frmMainWindow.getContentPane().add(textArea, "cell 0 2 1 6,grow");
		frmMainWindow.getContentPane().add(btnNewButton, "cell 1 2 2 1,alignx center");
		
		btnUp = new JButton("Up");
		frmMainWindow.getContentPane().add(btnUp, "cell 1 4 2 1,alignx center");
		
		btnLeft = new JButton("Left");
		frmMainWindow.getContentPane().add(btnLeft, "flowx,cell 1 5,alignx center");
		
		btnRight = new JButton("Right");
		frmMainWindow.getContentPane().add(btnRight, "cell 2 5,alignx center");
		
		btnDown = new JButton("Down");
		frmMainWindow.getContentPane().add(btnDown, "cell 1 6 2 1,alignx center");
		
		lblNewLabel_1 = new JLabel("You can start a new game.");
		frmMainWindow.getContentPane().add(lblNewLabel_1, "cell 0 8");
		
		btnExit = new JButton("Exit");
		frmMainWindow.getContentPane().add(btnExit, "cell 1 8 2 1,alignx center");
	}

}
