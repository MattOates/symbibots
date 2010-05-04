package ga.genes;

import sim.Agent;
import ga.Gene;

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
