import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;


/**
 * GUI Implementation of this program. making it look good. ;)
 * @author dany
 *
 */
public class Gui {

	private JFrame frame;
	private JTextField fileInput_textField;
	private JTextField destination_textField;
	private JTextField fileOutput_textField;
	private Magic task;
	
	private JFrame progressFrame;
	private JPanel contentPane;
	private JTextArea textArea;
	private JButton ok_btn;
	
	private TextAreaPrintStream taps;
	
	//Launch
	public static void startGui(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Gui() {
		initialize();
	}

	//Initialize everything!
	private void initialize() {
		//Main Frame
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//The Labels
		JLabel lbl1 = new JLabel("Address Distance-Time Calculator");
		lbl1.setFont(new Font("Dialog", Font.BOLD, 18));
		JLabel lbl2 = new JLabel("File containing list of addresses:");
		JLabel lbl3 = new JLabel("Destination address:");
		JLabel lbl4 = new JLabel("Output file:");

		
		//The Text Fields
		fileInput_textField = new JTextField();
		fileInput_textField.setColumns(10);
		
		destination_textField = new JTextField();
		destination_textField.setColumns(10);
		
		fileOutput_textField = new JTextField();
		fileOutput_textField.setColumns(10);
		
		//The Buttons
		JButton btn_open = new JButton("Open");
		btn_open.addActionListener(new ActionListener() {
			private JFileChooser fc = new JFileChooser();

			public void actionPerformed(ActionEvent e) {
				int returnVal = fc .showOpenDialog(frame);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            fileInput_textField.setText(file.getAbsolutePath());
		        } else {
		        	
		        }
			}
		});
		
		JButton btn_doMagic = new JButton("Calculate");
		btn_doMagic.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String inputFile = fileInput_textField.getText();
				String outputFile = fileOutput_textField.getText();
				String address2 = destination_textField.getText();;
				
				taps = new TextAreaPrintStream(textArea, System.out);
				
		        task = new Magic(inputFile, outputFile, address2, taps);
		        task.setOKBtn(ok_btn);
		        task.execute();
		        
				ok_btn.setEnabled(false);
		        progressFrame.setVisible(true);

			}
		});
			
		JButton btn_saveTo = new JButton("Save to");
		btn_saveTo.addActionListener(new ActionListener() {
			private JFileChooser fc = new JFileChooser();

			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc .showSaveDialog(frame);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	String fileName = fc.getSelectedFile().getAbsolutePath();
		        	if(!fileName.substring(fileName.length()-4, fileName.length()).equals(".csv"))
		        		fileName = fileName + ".csv";
		            File file = new File(fileName);
		            fileOutput_textField.setText(file.getAbsolutePath());
		        } else {
		        }
			}
		});
		
		//The GroupLayout logic (Thanks WindowBuilder)
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(fileOutput_textField, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
						.addComponent(lbl1)
						.addComponent(lbl2)
						.addComponent(fileInput_textField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lbl4)
							.addPreferredGap(ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
							.addComponent(btn_open))
						.addComponent(btn_doMagic, Alignment.TRAILING)
						.addComponent(destination_textField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lbl3)
							.addPreferredGap(ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
							.addComponent(btn_saveTo)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lbl1)
					.addGap(18)
					.addComponent(lbl2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(fileInput_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btn_open)
						.addComponent(lbl4))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(fileOutput_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
							.addComponent(lbl3)
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_saveTo)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(destination_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btn_doMagic)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
		
		//***** Create Second JFrame (Progress Monitor) *****
		
		//The Frame
		progressFrame = new JFrame();
		progressFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		progressFrame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {}

			@Override
			public void windowClosed(WindowEvent arg0) {
				textArea.setText("");
			}

			@Override
			public void windowClosing(WindowEvent arg0) {}

			@Override
			public void windowDeactivated(WindowEvent arg0) {}

			@Override
			public void windowDeiconified(WindowEvent arg0) {}

			@Override
			public void windowIconified(WindowEvent arg0) {}

			@Override
			public void windowOpened(WindowEvent arg0) {}
		});
		progressFrame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		progressFrame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		//The textField (Console-output)
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		contentPane.add(textArea, BorderLayout.CENTER);

		//The ok button
		ok_btn = new JButton("Ok");
		ok_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				task.cancel(true);
				progressFrame.dispose();
			}
		});
		contentPane.add(ok_btn, BorderLayout.SOUTH);

	}

}


