package ga.genes;

import ga.Gene;
import game.Agent;
import game.Entity;
import game.Force;

/**
 * Apply a force left on the parent agent.
 * @author Matt Oates
 */
public class LeftForceGene extends Gene implements Force {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public LeftForceGene(){}

	/**
	 * @see ga.Gene#express(game.Agent, ga.Gene)
	 */
	@Override
	public void express(Agent agent, Gene next) {
		if (agent.getDx() >= Entity.VELOCITY_MIN) {
			//What force are we applying
			double force = getXComponent();
			//If there is available energy apply a force to the agent
			if (agent.hasEnergyFor(force)) {
				//Alter the vertical velocity of the Agent
				applyForce(agent);
				//Remove some energy for this costly action
				agent.setEnergy((int)Math.round(agent.getEnergy() + force));
			}
		}
	}

	/**
	 * @see ga.Gene#toInfoString()
	 */
	@Override
	public String toInfoString() {
		return "Applies a force on the parent agent to the left of the screen.";
	}

	/**
	 * @see game.Force#getXComponent()
	 */
	public double getXComponent() {	
		//Decrease the horizontal velocity dx is -ve for left
		return getExpression() / -10;
	}

	/**
	 * @see game.Force#getYComponent()
	 */
	public double getYComponent() {
		//No Y component
		return 0.0;
	}

	/**
	 * @see game.Force#applyForce(game.Entity)
	 */
	public void applyForce(Entity entity) {
		//Only change the X component of the Entity
		entity.setDx(entity.getDx() + getXComponent());
	}

}
