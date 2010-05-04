package vis;

import ga.GeneticAlgorithm;
import game.World;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import ui.MainWindow;

/**
 * The GUI component that renders a Symbibots game model, can render any class that implements Visualisable.
 * @author Matt Oates
 */
public class VisualisationPanel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -901206076912227294L;
	
	private World world;
	
	private MainWindow ui;
	
	public VisualisationPanel(MainWindow ui) {
		super();
		setMinimumSize(new Dimension(World.DEFAULT_WIDTH, World.DEFAULT_HEIGHT));
		setPreferredSize(new Dimension(World.DEFAULT_WIDTH, World.DEFAULT_HEIGHT));
		this.ui = ui;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		
		if (world != null && World.IS_DATA_RUN) {
			graphics.drawImage(World.TITLE.getImage(),getWidth()/2 - World.TITLE.getIconWidth()/2, getHeight()/2 - World.TITLE.getIconHeight()/2, World.TITLE.getImageObserver());
			graphics.setColor(Color.BLUE);
			graphics.drawString("Data Run ["+world.getEpochs()+"/"+GeneticAlgorithm.DATA_RUN_EPOCH_LIMIT+"]",getWidth()/2,2 * (getHeight()/3));
		}
		else {
			if (world != null) {
				world.visualise(graphics);
				graphics.setColor(Color.BLACK);
				world.getEntities().visualise(graphics);
			}
			else {
				graphics.drawImage(World.TITLE.getImage(),getWidth()/2 - World.TITLE.getIconWidth()/2, getHeight()/2 - World.TITLE.getIconHeight()/2, World.TITLE.getImageObserver());
			}
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.awt.Component#setBounds(int, int, int, int)
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		if (world != null) {
			world.setWidth(width);
			world.setHeight(height);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (true) {
			while (!ui.paused) {
				this.repaint();
				System.out.println("yielded");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				System.out.println("back");
			}
			Thread.yield();
		}
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @param world the world to set
	 */
	public void setWorld(World world) {
		this.world = world;
		if (world != null) {
			world.setWidth(this.getWidth());
			world.setHeight(this.getHeight());
		}
	}

}
