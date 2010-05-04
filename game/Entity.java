package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

import vis.Visualisable;

/**
 * The top level abstract definition of a game Entity.
 * @author  Matt Oates
 */
public abstract class Entity implements Visualisable {
	
	protected RectangularShape shape;
	protected Point2D.Double position;
	protected Dimension dimension;
	
	protected double dx;
	protected double dy;
	protected boolean hasFriction = true;

	protected EntityManager manager;
	protected boolean inactive = false;
	
	private World world;
	
	/**
	 * Apply wall bouncing behavior to this entity.
	 * @see #setWallBehavior(int)
	 */
	protected static final int WALL_BOUNCE = 1;
	
	/**
	 * Apply wall wrapping behavior to this entity.
	 * @see #setWallBehavior(int)
	 */
	protected static final int WALL_WRAP = 2;
	
	private int wallBehavior;
	
	/**
	 * The level of friction applied to all Entity objects
	 */
	public static final double FRICTION = 0.9;
	
	/**
	 * The maximum pixel velocity an Entity can obtain.
	 */
	public static final double VELOCITY_MAX = 25.0;
	
	/**
	 * The minimum pixel velocity an Entity can obtain.
	 */
	public static final double VELOCITY_MIN = -25.0;
	
	/**
	 * Default constructor used in XML serialization
	 */
	public Entity(){}
		
	/**
	 * @param manager
	 */
	public Entity (EntityManager manager) {
		this.manager = manager;
		manager.register(this);
		world = manager.getWorld();
		wallBehavior = WALL_BOUNCE; 
	}
	
	/**
	 * @param manager
	 * @param wallBehavior
	 */
	public Entity(EntityManager manager, int wallBehavior) {
		this.manager = manager;
		this.wallBehavior = wallBehavior;
		manager.register(this);
		world = manager.getWorld(); 
	}
	
	/**
	 * All entities are required to implement Visualisable, there is no generic entity visualisation.
	 * @param graphics The graphics context a descendant Entity should visualise to.
	 * @see vis.VisualisationPanel
	 * @see vis.Visualisable
	 */
	public abstract void visualise(Graphics graphics);
	
	public void update() {
		
		if (inactive) return;
		
		if (hasFriction) {
			dx *= Entity.FRICTION;
			dy *= Entity.FRICTION;
		}
		double x,y;
		x = position.x + dx;
		y = position.y + dy;
		
		//Update location according to velocity
		position.setLocation(x, y);
		shape.setFrameFromCenter(position.x, position.y, position.x + dimension.width/2, position.y + dimension.height/2);
		
		//Deal with hitting a world boundary
		switch (wallBehavior) {
			case WALL_BOUNCE : {
				//Hitting the left and right sides of the world
				if (x <= 0) {
						position.x = dimension.width/2;
						dx *= -1;
				}
				else if (x >= world.getWidth()) {
					position.x = world.getWidth() - dimension.width/2;
					dx *= -1;
				}
				
				//Hitting top and bottom sides of the world
				if (y <= 0) {
					position.y = dimension.height/2;
					dy *= -1;
				}
				else if (y >= world.getHeight()) {
					position.y = world.getHeight() - dimension.height/2;
					dy *= -1;
				}				
			} break;
			case WALL_WRAP : {
				//Hitting the left and right sides of the world
				if (shape.getMinX() <= 0) position.x = world.getWidth() - 1 - dimension.width/2;
				else if (shape.getMaxX() >= world.getWidth()) position.x = 1 + dimension.width/2;
				//Hitting top and bottom sides of the world
				if (shape.getMinY() <= 0) position.y = world.getHeight() - 1 - dimension.height/2;
				else if (shape.getMaxY() >= world.getHeight()) position.y = 1 + dimension.height/2;
			} break;
		}
		shape.setFrameFromCenter(position.x, position.y, position.x + dimension.width/2, position.y + dimension.height/2);
	}
	
	public abstract void collide(Entity entity);

	/**
	 * @return  the dx
	 */
	public double getDx() {
		return dx;
	}

	/**
	 * @param dx  the dx to set
	 */
	public void setDx(double dx) {
		this.dx = dx;
	}

	/**
	 * @return  the dy
	 */
	public double getDy() {
		return dy;
	}

	/**
	 * @param dy  the dy to set
	 */
	public void setDy(double dy) {
		this.dy = dy;
	}

	/**
	 * @return the shape
	 */
	public RectangularShape getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(RectangularShape shape) {
		this.shape = shape;
		position.setLocation(shape.getBounds2D().getCenterX(), shape.getBounds2D().getCenterY());
	}

	/**
	 * @return the position
	 */
	public Point2D.Double getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Point2D.Double position) {
		this.position = position;
		this.shape.getBounds2D().setFrameFromCenter(position.x, position.y, position.x - (dimension.width / 2), position.y - (dimension.height / 2));
	}

	/**
	 * @return the manager
	 */
	public EntityManager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * @return the inactive
	 */
	public boolean isInactive() {
		return inactive;
	}

	/**
	 * @param inactive the inactive to set
	 */
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * @param dimension the dimension to set
	 */
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	/**
	 * @return the hasFriction
	 */
	public boolean isHasFriction() {
		return hasFriction;
	}

	/**
	 * @param hasFriction the hasFriction to set
	 */
	public void setHasFriction(boolean hasFriction) {
		this.hasFriction = hasFriction;
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @param world the world to set
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * @return the wallBehavior
	 */
	public int getWallBehavior() {
		return wallBehavior;
	}

	/**
	 * @param wallBehavior the wallBehavior to set
	 */
	public void setWallBehavior(int wallBehavior) {
		this.wallBehavior = wallBehavior;
	}
	
}
