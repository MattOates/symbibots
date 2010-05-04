package game;

import ga.GeneticAlgorithm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

/**
 * An energy packet found in the Symbibots world.
 * @author Matt Oates
 */
public class EnergyPacket extends Entity {
	
	private int energy;
	
	private ImageIcon graphic = new ImageIcon("images/energy.png");
	
	public EnergyPacket(){}
	
	/**
	 * @return the energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @param energy the energy to set
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * @return the graphic
	 */
	public ImageIcon getGraphic() {
		return graphic;
	}

	/**
	 * @param graphic the graphic to set
	 */
	public void setGraphic(ImageIcon graphic) {
		this.graphic = graphic;
	}

	public EnergyPacket(EntityManager manager) {
		super(manager);
		energy = (int)(GeneticAlgorithm.random() * 1000);
		position = new Point2D.Double(GeneticAlgorithm.random() * manager.getWorld().getWidth(), GeneticAlgorithm.random() * manager.getWorld().getHeight());
		dimension = new Dimension(graphic.getIconWidth(),graphic.getIconHeight());
		shape = new Rectangle2D.Double(position.x - dimension.width/2, position.y - dimension.height/2, dimension.width, dimension.height);
	}
	
	public EnergyPacket(EntityManager manager, int energy) {
		super(manager);
		this.energy = energy;
		position = new Point2D.Double(GeneticAlgorithm.random() * manager.getWorld().getWidth(), GeneticAlgorithm.random() * manager.getWorld().getHeight());
		dimension = new Dimension(graphic.getIconWidth(),graphic.getIconHeight());
		shape = new Rectangle2D.Double(position.x - dimension.width/2, position.y - dimension.height/2, dimension.width, dimension.height);
	}

	/* (non-Javadoc)
	 * @see game.Entity#collide(game.Entity)
	 */
	@Override
	public void collide(Entity entity) {
		//Do nothing if the energy packet is in the inactive state
		if (inactive) return;
		
		//Give the agent some energy and then remove this packet from the game
		if (entity instanceof Agent) {
			Agent agent = (Agent)entity;
			agent.setNumOfEnergyCollected(agent.getNumOfEnergyCollected()+energy);
			agent.setEnergy(agent.getEnergy() + energy);
			inactive = true;
		}
	}

	/* (non-Javadoc)
	 * @see game.Entity#visualise(java.awt.Graphics)
	 */
	@Override
	public void visualise(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D)graphics;
		
		graphics2d.drawImage(graphic.getImage(), (int)shape.getMinX(), (int)shape.getMinY(), graphic.getImageObserver());
	}

}
