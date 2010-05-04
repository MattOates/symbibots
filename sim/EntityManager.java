package sim;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;


/**
 * The manager class used by World to deligate en mass Entity operations like update().
 * @author Matt Oates
 * @see sim.World
 */
public class EntityManager {
	
	private LinkedList<Entity> ents = new LinkedList<Entity>();
	
	private World world = null;
	
	public EntityManager(){}
	
	/**
	 * Construct an EntityManager with all Agents from a GA registered.
	 * @param ga
	 */
	public EntityManager (World world) {
		this.world = world;
	}
	
	/**
	 * Register an Entity to be dealt with by this Manager.
	 * @param ent
	 */
	public synchronized void register(Entity ent) {
		ent.setManager(this);
		ents.add(ent);
	}
	
	/**
	 * Unregister an Entity.
	 * @param ent
	 */
	public synchronized void unregister(Entity ent) {
		ents.remove(ent);
	}
	
	/**
	 * Unregister Entities that are set inactive.
	 *
	 */
	public synchronized void unregisterInactive() {
		Entity ent;
		for (int i = 0; i < ents.size(); i++) {
			ent = ents.get(i);
			if (ent.isInactive()) {
				ents.remove(ent);
				i--;
			}
		}
	}
	
	/**
	 * Register a list of Entities to be dealt with by this Manager.
	 * @param ents
	 */
	public synchronized void registerAll(LinkedList<? extends Entity> ents) {
		this.ents.addAll(ents);
	}
	
	/**
	 * Unegister a list of Entities.
	 * @param ents
	 */
	public synchronized void unregisterAll(LinkedList<? extends Entity> ents) {
		this.ents.removeAll(ents);
	}
	
	/**
	 * Unregister everything being managed!
	 */
	public synchronized void unregisterAll() {
		ents.clear();
	}
	
	/**
	 * Update the position and velocities of all the Entities
	 *
	 */
	public synchronized void update() {
		//System.out.println(ents.size());
		for (Entity ent : ents) {
			ent.update();
			if (ent instanceof Agent) {
				Agent temp = (Agent) ent;
				if (World.IS_TEST)System.out.println(temp);
			}
		}
	}
	
	/**
	 * Check every entity against every other entity for a collision.
	 * Where a Missile hits an Agent or similar only one will act on the collision.
	 * Where two Agents collide each agent will deal with its own half of the collision.
	 */
	public synchronized void checkCollision() {		
		/* O(n^2) this could be improved with using other data structures to hold the Agents
		 * such as trees that are balanced by position, but this makes the process more complicated
		 * than it needs to be for such a small number of ents. 
		 */
		for (Entity ent1 : ents) {
			//Don't collide with inactive entities.
			if (ent1.isInactive()) continue;
			for (Entity ent2 : ents) {
				//Don't collide with inactive entities
				if (ent2.isInactive()) continue;
				//Don't collide with self
				if (ent1 == ent2) continue;
				if (ent1.getShape().intersects(ent2.getShape().getBounds2D())) {
					ent1.collide(ent2);
				}
			}
		}
	}

	/**
	 * @return the world
	 */
	public synchronized World getWorld() {
		return world;
	}

	/**
	 * @param world the world to set
	 */
	public synchronized void setWorld(World world) {
		this.world = world;
	}

	/**
	 * @param graphics
	 */
	public synchronized void visualise(Graphics graphics) {
		for (Entity ent : ents) {
			ent.visualise(graphics);
		}
	}

	/**
	 * @return the ents
	 */
	public synchronized LinkedList<Entity> getEnts() {
		return ents;
	}

	/**
	 * @param ents the ents to set
	 */
	public synchronized void setEnts(LinkedList<Entity> ents) {
		this.ents = ents;
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public synchronized Agent getAgent(int x, int y) {
		Point point = new Point(x,y);
		for (Entity ent : ents) {
			if (ent instanceof Agent && ent.getShape().contains(point))
				return (Agent) ent;
		}
		return null;
	}

}
