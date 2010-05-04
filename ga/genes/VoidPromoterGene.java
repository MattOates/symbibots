package ga.genes;

import ga.Gene;
import game.Agent;

/**
 * Always promote the expression of the next gene.
 * @author Matt Oates
 */
public class VoidPromoterGene extends Gene {
	
	/**
	 * Default constructor used in XML serialization.
	 */
	public VoidPromoterGene(){}

	@Override
	public void express(Agent agent, Gene next) {
		//next.setExpression((byte)Math.round((next.getExpression() * (1 + (getExpression() / Byte.MAX_VALUE)))));
		next.setExpression(next.getExpression() + (int)(getExpression()*0.25));
		//System.out.println("Expressing " + toString());
	}
	
	@Override
	public String toInfoString() {
		return "Always promotes the expression of the next gene.";
	}

}
