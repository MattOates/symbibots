package ga;

import game.Agent;

import java.util.LinkedList;

/**
 * The top level interface that defines Selection operators.
 * @author Matt Oates
 */
public interface Selection {
	
	/**
	 * Select Agents to be taken through to the next epoch.
	 * @param population
	 * @return Then new population.
	 */
	public LinkedList<Agent> select(GeneticAlgorithm ga);
	
	/**
	 * All methods of selection are required to implement an information string for use in the GUI.
	 * @return
	 */
	public String toInfoString();

}
