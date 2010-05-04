package ga.selection;

import java.util.LinkedList;

import ga.GeneticAlgorithm;
import ga.Selection;
import game.Agent;

/**
 * An implementation of Truncation Selection.
 * @author Matt Oates
 */
public class TruncationSelection implements Selection {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public TruncationSelection(){}
	
	/**
	 * Trunc size used in selection
	 */
	public static int truncSize = (int)(GeneticAlgorithm.populationSize /3);
	
	/**
	 * @see ga.Selection#select(ga.GeneticAlgorithm)
	 */
	public LinkedList<Agent> select(GeneticAlgorithm ga) {
		LinkedList<Agent> population = ga.getPopulation();
		LinkedList<Agent> selected = new LinkedList<Agent>();
				
		while (selected.size() < GeneticAlgorithm.populationSize) {
			//Select here
		}
		
		return selected;
	}

	/**
	 * @see ga.Selection#toInfoString()
	 */
	public String toInfoString() {
		return "The population is sorted by fitness quality and truncated leaving the top third to reproduce. This leads to elitism in the population.";
	}

}
