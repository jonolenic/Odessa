package odessaInterface;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import calculations.*;

import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import inventory.*;
import material.*;
import processing.ReadExcel;
import grades.*;

import javax.swing.ScrollPaneConstants;


/*
 * RunOdessa displays an interface that allows the user to attempt to find a solution for a 
 * requested number of grades. A table is dynamically filled in with all grade files from the
 * ExcelFiles folder. RunOdessa will attempt to find a solution as many times as the user requests.
 */
public class RunOdessa extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField totalMixNum;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField numIterations;
	
	String gradeName;
	int iterations = 0;
	int timesRun = 0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println("Initializing Odessa...");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RunOdessa frame = new RunOdessa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public RunOdessa() throws IOException {
		setTitle("Run Odessa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 370, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultTableModel tableModel = new DefaultTableModel(0, 2)  { 
			String[] columnNames = {"Grades", "Mixes"};

            @Override 
            public int getColumnCount() { 
                return columnNames.length; 
            } 

            @Override 
            public String getColumnName(int index) { 
                return columnNames[index]; 
            } 
            
            boolean[] canEdit = new boolean[]{
                    true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        }; ;
		
		Grade[] gradeList = ReadExcel.getGrades();
		for (int i = 0; i < gradeList.length; i++) {
			String gradeNum = gradeList[i].getGradeNum();
			String numMixes = Integer.toString(gradeList[i].getSize());
			
			Object[] data = {gradeNum, numMixes};
			tableModel.addRow(data);
		}
		
		JLabel lblTotalNumberOf = new JLabel("Total Number of Mixes");
		lblTotalNumberOf.setBounds(181, 11, 147, 14);
		contentPane.add(lblTotalNumberOf);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setBounds(10, 11, 138, 330);
		
		table.setModel(tableModel);
		table.setCellSelectionEnabled(true);
        table.setDefaultEditor(Object.class, null);
	
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 11, 147, 330);
		contentPane.add(scrollPane);
		
		totalMixNum = new JTextField();
		totalMixNum.setEditable(false);
		totalMixNum.setBounds(191, 36, 86, 20);
		contentPane.add(totalMixNum);
		totalMixNum.setColumns(10);
		
		JLabel lblMaximumMixes = new JLabel("Maximum Mixes");
		lblMaximumMixes.setBounds(181, 67, 147, 14);
		contentPane.add(lblMaximumMixes);
		
		textField_1 = new JTextField();
		textField_1.setBounds(191, 92, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblKeySize = new JLabel("Key Size");
		lblKeySize.setBounds(181, 123, 122, 14);
		contentPane.add(lblKeySize);
		
		textField_2 = new JTextField();
		textField_2.setBounds(191, 148, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNoSearchIteration = new JLabel("No. Search Iteration");
		lblNoSearchIteration.setBounds(181, 179, 147, 14);
		contentPane.add(lblNoSearchIteration);
		
		numIterations = new JTextField();
		numIterations.setText("1");
		numIterations.setBounds(191, 204, 86, 20);
		contentPane.add(numIterations);
		numIterations.setColumns(10);
		
		JCheckBox chckbxUpdateInvLevel = new JCheckBox("Update Inv. Level");
		chckbxUpdateInvLevel.setBounds(181, 231, 122, 23);
		contentPane.add(chckbxUpdateInvLevel);
		
		JCheckBox chckbxShutOff = new JCheckBox("Shut Off");
		chckbxShutOff.setBounds(181, 257, 97, 23);
		contentPane.add(chckbxShutOff);
		
		JButton btnOptimize = new JButton("Optimize");
		btnOptimize.setEnabled(false);
		btnOptimize.setBounds(188, 290, 89, 23);
		contentPane.add(btnOptimize);
        btnOptimize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optimize(gradeList);
            }
        });
		

		
		JButton btnMaterials = new JButton("Materials");
		btnMaterials.setEnabled(false);
		btnMaterials.setBounds(188, 337, 89, 23);
		contentPane.add(btnMaterials);
        btnMaterials.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                materials(chckbxShutOff.isSelected());
            }
        });
        
		JButton btnSolution = new JButton("Solution");
		btnSolution.setEnabled(false);
		btnSolution.setBounds(188, 313, 89, 23);
		contentPane.add(btnSolution);
        btnSolution.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	boolean solFound = false;
                solFound = solution(gradeList, iterations, chckbxUpdateInvLevel.isSelected());
                btnMaterials.setEnabled(solFound);
            }
        });
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel selectionModel = table.getSelectionModel();
				selectionModel.addListSelectionListener(new ListSelectionListener() {
			        public void valueChanged(ListSelectionEvent arg0) {
			        	int row = table.getSelectedRow();
			        	int col = table.getSelectedColumn()+1;
			        	if (col >= 2)
			        		col = table.getSelectedColumn();
			        	totalMixNum.setText((String) table.getModel().getValueAt(row, col));
			        	
			        	int gradeRow = table.getSelectedRow();
			        	int gradeCol = table.getSelectedColumn();
			        	if (gradeCol >= 1)
			        		gradeCol = gradeCol - 1;
			        	gradeName = (String) table.getModel().getValueAt(gradeRow, gradeCol);
			        	btnSolution.setEnabled(true);
			        }
				});

	}

	/*
	 * Optimize function. Stub for later use when search algorithms are implemented
	 */
	private void optimize(Grade[] grade) {
	}
	
	/*
	 * Solution function. Given the selected grade and number of iterations, attempts to find
	 * the cheapest possible mix that can make the grade
	 */
	private boolean solution(Grade[] grade, int runs, boolean updateInv) {
		int i = 0;
		while (grade[i].getGradeNum() != gradeName) {
			if (grade[i].getGradeNum() == gradeName)
				break;
			i++;
		}
		boolean checkInt = numIterations.getText().matches("-?\\d+(\\\\d+)?");
		if (checkInt == false) {
			JOptionPane.showMessageDialog(new JFrame(), "Must enter an integer");
			if (timesRun >= 1)
				return true;
			return false;
		}
		if (Integer.parseInt(numIterations.getText()) <= 0) {
			JOptionPane.showMessageDialog(new JFrame(), "Must run 1 or more iterations");
			if (timesRun >= 1)
				return true;
			return false;
		}
		
		runs = Integer.parseInt(numIterations.getText());
		CostCalculations.chooseFunction(grade[i], runs, gradeName);
		if (updateInv = true)
			CostCalculations.updateRealInventory();
		timesRun++;
		return true;
	}
	
	/*
	 * Final display. Prints out final costs and statistics of all recipes created, and prints to a log file.
	 */
	private void materials(boolean checkShutOff) {
		RecipeDisplay.printResults(timesRun);
		if (checkShutOff == true) {
			System.exit(0);
		}
	}

}
