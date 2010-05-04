package sim.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import sim.Agent;
import sim.Entity;
import sim.EntityManager;
import sim.agents.UserAgent;

/**
 * The Missile entity fired by agents, carries a certain amount of the Agents energy as a payload.
 * @author Matt Oates
 */
public class Missile extends Entity {
	
	private int energy;
	
	private Agent owner;
	
	private int steps;
	
	private ImageIcon graphic = new ImageIcon("images/missile.png");
	
	public Missile(){}
	
	public Missile(EntityManager manager, int energy, Agent owner) {
		super(manager);
		this.energy = energy;
		this.owner = owner;
		steps = energy / 2;
		dimension = new Dimension(3,3);
		shape = new Rectangle2D.Double();
		hasFriction = false;
	}

	/* (non-Javadoc)
	 * @see game.Entity#collide(game.Entity)
	 */
	@Override
	public void collide(Entity entity) {
		//Do nothing if the missile is in the inactive entity state
		if (inactive) return;
		
		//Missiles only hit Agents
		if (entity instanceof Agent) {
			//If the hit is on something other than the missile owner register it
			if (entity != owner) {
				Agent agent = (Agent)entity;
				//Register a hit to the agent
				agent.setLife(agent.getLife()-energy);
				
				if (entity instanceof UserAgent) {
					//Only register a full hit to the owner agent if it was a player who was hit
					owner.setNumOfMissileHits(owner.getNumOfMissileHits() + 1);
					owner.setNumOfEnemyDamage(energy);
					//Register a kill if one has been made
					if (agent.getLife() <= 0)
						owner.setNumOfPlayersKilled(owner.getNumOfPlayersKilled() + 1);
				}
			}
			//Otherwise let the owner get the energy back for the missile
			else {
				owner.setEnergy(owner.getEnergy() + energy);
				owner.setNumOfMissileCost(owner.getNumOfMissileCost() - energy);
			}
			//Set this missile inactive so we don't do another hit
			inactive = true;
		}
	}
	
	/* (non-Javadoc)
	 * @see game.Entity#update()
	 */
	@Override
	public void update() {
		super.update();
		if (inactive) return;
		
		if (steps <= 0) {
			steps = 0;
			inactive = true;
		}
		steps--;
	}

	/* (non-Javadoc)
	 * @see game.Entity#visualise(java.awt.Graphics)
	 */
	@Override
	public void visualise(Graphics graphics) {
		if (inactive) return;
		graphics.drawImage(graphic.getImage(),(int)(position.x - graphic.getIconWidth()/2),(int)(position.y - graphic.getIconHeight()/2),graphic.getImageObserver());
		Graphics2D graphics2d = (Graphics2D) graphics;
		
		//graphics.setClip(shape);
		try {
		graphics.setColor(new Color(255 - steps,255 - steps,0));
		}
		catch (IllegalArgumentException e) {
			System.err.println(steps);
			e.printStackTrace();
		}
		
		if (steps == 1) {
			graphics2d.fillOval((int)shape.getBounds2D().getMinX(),
								(int)shape.getBounds2D().getMinY(), 
								(int)shape.getBounds2D().getWidth() + 3,
								(int)shape.getBounds2D().getHeight() + 3);
		}
		else {
			graphics2d.fill(getShape());
		}
		
	}

}
