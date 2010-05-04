package game;

/**
 * A convenient interface for dealing with genes and other objects that can apply a force.
 * @author Matt Oates
 */
public interface Force {
	public double getXComponent();
	public double getYComponent();
	public void applyForce(Entity entity);
}
