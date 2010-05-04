package ga;

import game.Agent;

import java.util.LinkedList;

/**
 * The top level interface that defines a Symbiosis operator.
 * @author Matt Oates
 * @see ga.symbiosis
 */
public interface Symbiosis {
	
	/**
	 * 
	 * @param newPopulation The next generation population after reproduction.
	 * @param selectedPopulation The previous unaltered generation.
	 */
	public void symbiose(LinkedList<Agent> newPopulation, LinkedList<Agent> selectedPopulation);
	
	/**
	 * All symbiosis operators are required to return an info string for use with the GUI.
	 */
	public String toInfoString();
	
}
