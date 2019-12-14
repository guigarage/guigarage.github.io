package de.guigarage.jgrid.demos.simple;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.jgrid.JGrid;

public class JGridTutorial1 extends JFrame {

	private static final long serialVersionUID = 1L;

	public JGridTutorial1() {
		setTitle("Simple JGrid Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JGrid grid = new JGrid();
		getContentPane().add(new JScrollPane(grid));
		
		DefaultListModel model = new DefaultListModel();
		for(int i=0; i < 100; i++) {
			model.addElement(new Integer(i));
		}
		grid.setModel(model);
		grid.setFont(grid.getFont().deriveFont(40.0f));

		setSize(400, 300);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new JGridTutorial1().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
