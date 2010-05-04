package ga;

/**
 * Top level interface for defining Crossover GA operators.
 * @author Matt Oates
 */

public interface Crossover {
	
	/**
	 * Takes two chromosomes and returns the two sexualy crossed children.
	 * @param mother One parent chromosome.
	 * @param father Another parent chromosome.
	 * @return The two child chromosomes.
	 */
	public Chromosome[] cross(Chromosome mother, Chromosome father);
	
	/**
	 * Give an information string about the specifics of this Crossover method, must be suitable
	 * for use in the user interface.
	 * @return Information about this specific Crossover method.
	 */
	public String toInfoString();

}
