package ga.genes;

import java.awt.geom.Point2D;

import ga.Gene;
import game.Agent;
import game.Missile;

/**
 * Create and fire a missile Entity on the current heading of the parent Agent.
 * @author Matt Oates
 */
public class FireMissileAheadGene extends Gene {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public FireMissileAheadGene(){}
	
	/**
	 * @see ga.Gene#express(game.Agent, ga.Gene)
	 */
	@Override
	public void express(Agent agent, Gene next) {
		//If there is available energy create a missile.
		if (agent.hasEnergyFor(getExpression())) {
			Missile missile = new Missile(agent.getManager(), getExpression(), agent);
			//Remove some energy for this costly action
			agent.setEnergy((int)Math.round(agent.getEnergy() - getExpression()));
			
			//Create the missiles position just ahead of the agent
			Point2D.Double misspos = new Point2D.Double();
			misspos.setLocation(
						agent.getPosition().getX() + agent.getDx()
					, 	agent.getPosition().getY() + agent.getDy()
					);
			missile.setPosition(misspos);
			
			//Set the missile velocity to be a little bit more than the agent
			missile.setDx(agent.getDx());
			missile.setDy(agent.getDy());
			agent.setNumOfMissilesFired(agent.getNumOfMissilesFired() + 1);
			agent.setNumOfMissileCost(getExpression());
		}
	}

	/**
	 * @see ga.Gene#toInfoString()
	 */
	@Override
	public String toInfoString() {
		return "Convert a portion of an Agents energy into a destructive missile fired on the current heading.";
	}
}
