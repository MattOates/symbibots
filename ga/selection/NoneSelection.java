package ga.selection;

import java.util.LinkedList;

import ga.GeneticAlgorithm;
import ga.Selection;
import game.Agent;

/**
 * Added to allow the None menu selection, does not perform any selection on the population.
 * @author Matt Oates
 */
public class NoneSelection implements Selection {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public NoneSelection(){}
	
	/**
	 * The implementing selection method, the population is returned unaltered.
	 * @see ga.Selection#select(ga.GeneticAlgorithm)
	 */
	public LinkedList<Agent> select(GeneticAlgorithm ga) {
		return ga.getPopulation();
	}

	/**
	 * The description of this selection method.
	 * @see ga.Selection#toInfoString()
	 */
	public String toInfoString() {
		return "Do not select with any bias, return the unaltered population.";
	}

}
