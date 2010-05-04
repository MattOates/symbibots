package ga.symbiosis;

import java.util.LinkedList;

import ga.Symbiosis;
import game.Agent;

/**
 * Added to allow the None menu selection, does not perform any symbiosis on the population.
 * @author Matt Oates
 */
public class NoneSymbiosis implements Symbiosis {
	
	public NoneSymbiosis(){}

	/**
	 * @see ga.Symbiosis#symbiose(java.util.LinkedList, java.util.LinkedList)
	 */
	public void symbiose(LinkedList<Agent> newPopulation, LinkedList<Agent> selectedPopulation) {
		//Does nothing.
		return;
	}

	/**
	 * @see ga.Symbiosis#toInfoString()
	 */
	public String toInfoString() {
		return "Do not perform any symbiotic operations.";
	}

}
