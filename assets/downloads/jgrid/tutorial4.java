package de.guigarage.jgrid.demos.simple;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.jgrid.JGrid;
import de.jgrid.renderer.GridCellRenderer;

public class JGridTutorial4 extends JFrame {

	private static final long serialVersionUID = 1L;

	public JGridTutorial4() {
		setTitle("Simple JGrid Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JGrid grid = new JGrid();
		getContentPane().add(new JScrollPane(grid));
		
		DefaultListModel model = new DefaultListModel();
		Random random = new Random();
		for(int i=0; i <= 100; i++) {
			model.addElement(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		}
		grid.setModel(model);
		grid.setFont(grid.getFont().deriveFont(40.0f));
		
		grid.setFixedCellDimension(56);
		grid.setHorizonztalMargin(4);
		grid.setVerticalMargin(4);
		
		grid.getCellRendererManager().setDefaultRenderer(new GridColorCellRenderer());
		
		final JSlider slider = new JSlider(32, 256);
		slider.setValue(grid.getFixedCellDimension());
		slider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				grid.setFixedCellDimension(slider.getValue());
			}
		});
		
		getContentPane().add(slider, BorderLayout.SOUTH);
		
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
					new JGridTutorial4().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
