package ga.genes;

import ga.Gene;
import game.Agent;

/**
 * A gene to test the modularity of gene loading
 * @author Matt Oates
 */
public class NewGene extends Gene {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public NewGene(){}
	
	@Override
	public void express(Agent agent, Gene next) {
	}
	
	@Override
	public String toInfoString() {
		return "Does nothing.";
	}
	
}
