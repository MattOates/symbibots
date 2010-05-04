package ga.crossover;

import ga.Chromosome;
import ga.Crossover;

/**
 * Added to allow the None menu selection, does not perform any crossing of parents.
 * Children are cloned copies of the parents.
 * @author Matt Oates
 */
public class NoneCrossover implements Crossover {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public NoneCrossover() {}

	/**
	 * Implemented crossover method.
	 * @see ga.Crossover#cross(ga.Chromosome, ga.Chromosome)
	 */
	public Chromosome[] cross(Chromosome mother, Chromosome father) {
		Chromosome[] children = new Chromosome[2];
			try {
				children[0] = (Chromosome) mother.clone();
				children[1] = (Chromosome) father.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
				
		return children;
	}

	/** 
	 * A description of this crossover method.
	 * @see ga.Crossover#toInfoString()
	 */
	public String toInfoString() {
		return "Do not apply any crossover.";
	}

}
