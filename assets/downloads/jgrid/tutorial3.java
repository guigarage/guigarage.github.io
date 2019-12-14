package de.guigarage.jgrid.demos.simple;

import java.awt.Color;
import java.awt.Component;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.jgrid.JGrid;
import de.jgrid.renderer.GridCellRenderer;

public class JGridTutorial3 extends JFrame {

	private static final long serialVersionUID = 1L;

	public JGridTutorial3() {
		setTitle("Simple JGrid Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JGrid grid = new JGrid();
		getContentPane().add(new JScrollPane(grid));
		
		DefaultListModel model = new DefaultListModel();
		Random random = new Random();
		for(int i=0; i <= 100; i++) {
			model.addElement(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		}
		grid.setModel(model);
		
		grid.setFixedCellDimension(56);
		grid.setHorizonztalMargin(4);
		grid.setVerticalMargin(4);
		grid.setCellBackground(Color.LIGHT_GRAY);
		grid.setSelectionBackground(Color.LIGHT_GRAY);
		
		grid.getCellRendererManager().setDefaultRenderer(new GridColorCellRenderer());
		
		setSize(400, 300);
	}
	
	public class GridColorCellRenderer extends JPanel implements GridCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getGridCellRendererComponent(JGrid grid,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if(value != null && value instanceof Color) {
				this.setBackground((Color) value);
			}
			return this;
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new JGridTutorial3().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
