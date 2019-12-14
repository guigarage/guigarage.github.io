package de.guigarage.jgrid.demos.simple;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.text.NumberFormat;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import de.jgrid.JGrid;
import de.jgrid.renderer.GridCellRenderer;

public class JGridTutorial5 extends JFrame {

	private static final long serialVersionUID = 1L;

	public JGridTutorial5() {
		setTitle("Simple JGrid Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JGrid grid = new JGrid();
		getContentPane().add(new JScrollPane(grid));
		
		DefaultListModel model = new DefaultListModel();
		Random random = new Random();
		for(int i=0; i <= 100; i++) {
			if(random.nextBoolean()) {
				model.addElement(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
			} else {
				model.addElement(new Float(random.nextFloat()));
			}
		}
		grid.setModel(model);
		
		grid.setFixedCellDimension(56);
		grid.setHorizonztalMargin(4);
		grid.setVerticalMargin(4);
		grid.setCellBackground(Color.LIGHT_GRAY);
		grid.setSelectionBackground(Color.LIGHT_GRAY);
		
		grid.getCellRendererManager().addRendererMapping(Color.class, new GridColorCellRenderer());
		grid.getCellRendererManager().addRendererMapping(Float.class, new GridPercentCellRenderer());
		
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
	
	public class GridPercentCellRenderer extends JLabel implements GridCellRenderer {
		private static final long serialVersionUID = 1L;

		private float f = 0.0f;
		
		public GridPercentCellRenderer() {
			setHorizontalAlignment(SwingConstants.CENTER);
			setBackground(Color.white);
			setForeground(Color.black);
		}
		
		@Override
		public Component getGridCellRendererComponent(JGrid grid,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if(value != null && value instanceof Float) {
				this.f = ((Float) value).floatValue();
				setText(NumberFormat.getPercentInstance().format(f));
			}
			return this;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.LIGHT_GRAY);
			int height = (int)((float)getHeight() * f);
			g.fillRect(0, getHeight() - height, getWidth(), height);
			super.paintComponent(g);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new JGridTutorial5().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
