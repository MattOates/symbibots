package ga.genes;

import sim.Agent;
import ga.Gene;

/**
 * A gene that does nothing apart from take up space. This is important for allowing 
 * crossover to be less destructive by putting "junk" in between important series
 * of interacting genes. Void is also important when you consider the search for
 * mutualistic chromosomes with the Symbiosis operator.
 * @author Matt Oates
 */
public class VoidGene extends Gene {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public VoidGene(){}
	
	@Override
	public void express(Agent agent, Gene next) {
		// TODO Auto-generated method stub
		//System.out.println("Expressing " + toString());
	}
	
	@Override
	public String toInfoString() {
		return "Does nothing.";
	}
	
}
