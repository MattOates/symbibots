package ga.genes;

import ga.Gene;
import game.Agent;

/**
 * Convert some of the parent agents energy into life.
 * @author Matt Oates
 */
public class HealGene extends Gene {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public HealGene(){}

	/**
	 * @see ga.Gene#express(game.Agent, ga.Gene)
	 */
	@Override
	public void express(Agent agent, Gene next) {
		
		//If there is available energy heal the agent
		if (agent.hasEnergyFor(getExpression())) {
			//Heal the agent the expression amount
			agent.setLife(agent.getLife() + getExpression());
			//Register how much health was recovered
			agent.setNumOfLifeHealed(agent.getNumOfLifeHealed()+getExpression());
			//Remove some energy for this costly action
			agent.setEnergy((int)Math.round(agent.getEnergy() - getExpression()));
		}
	}

	/**
	 * @see ga.Gene#toInfoString()
	 */
	@Override
	public String toInfoString() {
		return "Heal the agent by converting energy into life.";
	}
}
