package ga.genes;

import sim.Agent;
import sim.Entity;
import sim.Force;
import ga.Gene;

/**
 * Apply a force upwards on an Agent.
 * @author Matt Oates
 */
public class DownForceGene extends Gene implements Force {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public DownForceGene(){}
	
	/**
	 * @see ga.Gene#express(sim.Agent, ga.Gene)
	 */
	@Override
	public void express(Agent agent, Gene next) {
		if (agent.getDy() <= Entity.VELOCITY_MAX) {
			//What force are we applying
			double force = getYComponent();
			//If there is available energy apply a force to the agent
			if (agent.hasEnergyFor(force)) {
				//Alter the vertical velocity of the Agent
				applyForce(agent);
				//Remove some energy for this costly action
				agent.setEnergy((int)Math.round(agent.getEnergy() - force));
			}
		}
	}

	/**
	 * @see ga.Gene#toInfoString()
	 */
	@Override
	public String toInfoString() {
		return "Applies a force down screen on the parent Agent.";
	}

	/**
	 * @see sim.Force#getXComponent()
	 */
	public double getXComponent() {	
		//No X component
		return 0.0;
	}

	/**
	 * @see sim.Force#getYComponent()
	 */
	public double getYComponent() {
		//Increase the vertical velocity dy is -ve for upwards
		return getExpression() / 10;
	}

	/**
	 * @see sim.Force#applyForce(sim.Entity)
	 */
	public void applyForce(Entity entity) {
		//Only change the Y component of the Entity
		entity.setDy(entity.getDy() + getYComponent());
	}

}
