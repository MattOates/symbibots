package ga.genes;

import ga.Gene;
import game.Agent;

/**
 * Always inhibit the next gene along.
 * @author Matt Oates
 */
public class VoidInhibitorGene extends Gene {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public VoidInhibitorGene(){}

	@Override
	public void express(Agent agent, Gene next) {
		//next.setExpression((byte)Math.round((next.getExpression() * (getExpression() / Byte.MAX_VALUE))));
		next.setExpression(next.getExpression() - (int)(getExpression()*0.25));
		//System.out.println("Expressing " + toString());
	}
	
	@Override
	public String toInfoString() {
		return "Always inhibits the expression of the next gene.";
	}

}
