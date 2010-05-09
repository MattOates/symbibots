package sim.agents;

import ga.Chromosome;
import ga.GeneticAlgorithm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import sim.Agent;
import sim.Entity;
import sim.EntityManager;
import sim.entities.EnergyPacket;
import sim.entities.Missile;
import ui.AgentPanel;

/**
 * An Agent acted upon by both the World model and the GA.
 * @author  Matt Oates 
 */
public class EpuckRobotAgent extends Agent {
	
	private LinkedList<Chromosome> chromosomes = new LinkedList<Chromosome>();
	
	private int numOfSymbionts = 0;
	
	private double fitness = 0;
	
	protected int numOfMissilesFired = 0;
	protected int numOfMissileCost = 0;
	protected int numOfMissileHits = 0;
	protected int numOfEnemyDamage = 0;
	protected int numOfLifeHealed = 0;
	protected int numOfEnergyCollected = 0;
	protected int numOfPlayersKilled = 0;
	protected double distanceTraveled = 0;
	
	protected static final int START_LIFE = 2000;
	protected int life = START_LIFE;
	
	protected static final int START_ENERGY = 5000;
	protected int energy = START_ENERGY;
	
	protected ImageIcon graphic = new ImageIcon("images/agent.png");
	
	/**
	 * Handle on a GUI panel for this Agent if it exists.
	 */
	private transient AgentPanel panel = null;
	
	/**
	 * Default constructor used in XML serialization
	 */
	public EpuckRobotAgent(){}
	
	public EpuckRobotAgent(EntityManager manager) {
		super(manager, Entity.WALL_WRAP);
		position = new Point2D.Double(GeneticAlgorithm.random() * manager.getWorld().getWidth(), GeneticAlgorithm.random() * manager.getWorld().getHeight());
		dimension = new Dimension(40,40);
		shape = new Ellipse2D.Double(position.x, position.y, dimension.getWidth(), dimension.getHeight());
		shape.setFrameFromCenter(position.x, position.y, position.x + dimension.width/2, position.y + dimension.height/2);
		chromosomes.add(new Chromosome(this));
	}
	
	public EpuckRobotAgent(EntityManager manager, Chromosome chromosome) {
		//TODO this(manager); BUG Cannot use this otherwise we get another random chromosome as the sexual chromosome and the inherited chromosome as a symbiont!
		super(manager, Entity.WALL_WRAP);
		position = new Point2D.Double(GeneticAlgorithm.random() * manager.getWorld().getWidth(), GeneticAlgorithm.random() * manager.getWorld().getHeight());
		dimension = new Dimension(40,40);
		shape = new Ellipse2D.Double(position.x, position.y, dimension.getWidth(), dimension.getHeight());
		shape.setFrameFromCenter(position.x, position.y, position.x + dimension.width/2, position.y + dimension.height/2);
		chromosome.setParent(this);
		chromosomes.add(chromosome);
	}
	
	/**
	 * Overridden to allow for distance traveled acounting. 
	 * @see sim.Entity#update()
	 */
	@Override
	public void update() {
		if (inactive) return;
		
		//Test for death
		if (life <= 0) {
			inactive = true;
		}
		
		Point2D before = (Point2D) position.clone();
		super.update();
		
		//Add on distance, but not from being nudged or wrapping around the world.
		double dist = before.distance(position);
		if (dist <= Entity.VELOCITY_MAX)
			distanceTraveled += dist;
		
		//Update the agent panel if there is one
		if (panel != null)
			panel.updateData();
	}
	
	/**
	 * Called by VisualisationPanel when a game screen needs to be rendered.
	 * @param graphics The graphics context this agent should visualise to.
	 * @see vis.VisualisationPanel
	 * @see vis.Visualisable
	 */
	public void visualise(Graphics graphics) {
		if (inactive) return;
		//graphics.setClip(shape);
		graphics.drawImage(graphic.getImage(),(int)(position.x - graphic.getIconWidth()/2),(int)(position.y - graphic.getIconHeight()/2),graphic.getImageObserver());
		graphics.setColor(Color.BLACK);
		graphics.drawLine((int)position.x,(int)position.y,(int)(position.x + dx),(int)(position.y + dy));
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.draw(getShape());
	}
	
	/**
	 * Used at the end of a GA epoch to calculate the fitness of an agents phenotype, minimum fitness possible is 1.
	 * @return The fitness value.
	 */
	public double calcFitness() {		
		//Agression factor between 1 and 2
		double agression = 1 + (numOfMissileHits/(1+numOfMissilesFired));
		//Exploration value, how much energy found per pixel distance explored.
		double exploration = 1 + (numOfEnergyCollected/(1+distanceTraveled));
		//Vitality, how much life and energy is left at the end of the epoch, include attempts to heal
		@SuppressWarnings("unused")
		int vitality = 1 + energy + life;
		//Killer multiplyer, give any agents that dealt the killing blow to a player a better chance
		@SuppressWarnings("unused")
		int killmultiplyer = (numOfPlayersKilled+1)*2;
		setFitness(exploration+(agression * numOfEnemyDamage));
		return vitality+exploration+(agression * numOfEnemyDamage);
	}
	
	/**
	 * Copy and add a given Chromosome to this agent. Used by symbiosis operators to introduce additional chromosomes.
	 * @param chromosome The chromosome object to clone and add.
	 */
	public Chromosome addChromosome(Chromosome chromosome) {
		Chromosome copy = null;
		try {
			copy = (Chromosome)chromosome.clone();
			copy.setParent(this);
			chromosomes.add(copy);
			return copy;
		} catch (CloneNotSupportedException e) {
			manager.getWorld().getUi().addLog("Failed to add chromosome to agent.");
		}
		numOfSymbionts = chromosomes.size() -1;
		return copy;
	}
	
	/**
	 * Add a symbiotic chromosome to the agent.
	 * @param agent The agent to add as a symbiont.
	 * @deprecated This method was replaced by addChromosome which encapsulates the call to clone and returns
	 *  a handle to the chromosome copy. Symbiosis was directly performed with Chromosomes instead of Agents.
	 */
	public void addSymbiont(Agent agent) {
		LinkedList<Chromosome> symbiont = agent.getChromosomes();
		for (Chromosome temp : symbiont) {
			chromosomes.add(temp);
		}
		numOfSymbionts++;
	}
	
	/**
	 * 
	 *
	 */
	public void express() {
		//Don't express anything if the agent is inactive
		if (inactive) return;
		for (Chromosome chromosome : chromosomes) chromosome.express();
	}
	
	/**
	 * @param force
	 * @return
	 */
	public boolean hasEnergyFor(double requirement) {
		if (energy - requirement >= 0)
			return true;
		return false;
	}

	/**
	 * Overridden for specific agent interactions.
	 * @see sim.Entity#collide(sim.Entity)
	 */
	@Override
	public void collide(Entity entity) {
		//Do nothing if the Agent is in the inactive state
		if (inactive || entity.isInactive()) return;
		
		if (entity instanceof Agent) {
			
			//Change direction of both agents
			dx *= -1;
			dy *= -1;
			entity.setDx(entity.getDx() * -1);
			entity.setDy(entity.getDy() * -1);
			
			//Bounce horizontaly
			if (position.x < entity.getPosition().getX()) {
				position.x -= dimension.width/2;
				entity.position.x += entity.dimension.width/2;
			}
			else if (position.x >= entity.getPosition().getX()) {
				position.x += dimension.width/2;
				entity.position.x -= entity.dimension.width/2;
			}
			//Bounce vertically
			if (position.y < entity.getPosition().getY()) {
				position.y -= dimension.height/2;
				entity.position.y += entity.dimension.height/2;
			}
			else if (position.y >= entity.getPosition().getY()) {
				position.y += dimension.height/2;
				entity.position.y -= entity.dimension.height/2;
			}
			
			shape.setFrameFromCenter(position.x, position.y, position.x + dimension.width/2, position.y + dimension.height/2);
			entity.shape.setFrameFromCenter(entity.position.x, entity.position.y, entity.position.x + entity.dimension.width/2, entity.position.y + entity.dimension.height/2);
			/*
			shape.setFrame(position.x - (dimension.width / 2), position.y - (dimension.height), dimension.width, dimension.height);
			entity.shape.setFrame(entity.position.x - (entity.dimension.width / 2), entity.position.y - (entity.dimension.height), entity.dimension.width, entity.dimension.height);
			*/
		}
		//If this Agent has collided with an energy packet let that deal with the collision 
		else if (entity instanceof EnergyPacket) {
			entity.collide(this);
		}
		//If this Agent has collided with a missile let that deal with the collision
		else if (entity instanceof Missile) {
			entity.collide(this);
		}
		
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer string = new StringBuffer();
		string.append("X: "+(int)position.x);
		string.append("\n");
		string.append("Y: "+(int)position.y);
		string.append("\n");
		string.append("Distance traveled: "+(int)distanceTraveled);
		string.append("\n\n");
		string.append("Life: "+life);
		string.append("\n");
		string.append("Energy: "+energy);
		string.append("\n\n");
		string.append("Missiles fired: "+numOfMissilesFired);
		string.append("\n");
		string.append("Missiles hit: "+numOfMissileHits);
		string.append("\n");
		string.append("Energy cost of Missiles: "+numOfMissileCost);
		string.append("\n");
		string.append("Damage dealt to Player: "+numOfEnemyDamage);
		string.append("\n");
		string.append("Players killed: "+numOfPlayersKilled);
		string.append("\n\n");
		string.append("Life healed: "+numOfLifeHealed);
		string.append("\n");
		string.append("Energy collected: "+numOfEnergyCollected);
		string.append("\n");
		return string.toString();
	}
	
	public int getNumOfGenes() {
		return chromosomes.size() * GeneticAlgorithm.chromosomeSize;
	}
	
	/**
	 * Get the sexual chromosome for this agent.
	 * @return the sexual Chromosome for this agent.
	 */
	public Chromosome getChromosome() {
		return chromosomes.getFirst();
	}
	
	/**
	 * Get a linked list of all the symbiotic chromosomes for this Agent
	 * @return the chromosomes
	 */
	@SuppressWarnings("unchecked")
	public List<Chromosome> getSymbioticChromosomes() {
		if (chromosomes.size() <= 1) return null;
		return chromosomes.subList(1, chromosomes.size());
		/*if (chromosomes.size() > 1) return null;
		LinkedList<Chromosome> symbionts = (LinkedList<Chromosome>) chromosomes.clone();
		symbionts.removeFirst();
		return symbionts;*/
	}
	
	/**
	 * @return  the chromosomes
	 */
	public LinkedList<Chromosome> getChromosomes() {
		return chromosomes;
	}

	/**
	 * @param chromosomes  the chromosomes to set
	 */
	public void setChromosomes(LinkedList<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}

	/**
	 * @return  the numofsymbionts
	 */
	public int getNumOfSymbionts() {
		return numOfSymbionts;
	}

	/**
	 * @param numOfSymbionts  the number of symbionts to set
	 */
	public void setNumOfSymbionts(int numOfSymbionts) {
		this.numOfSymbionts = numOfSymbionts;
	}

	/**
	 * @return  the fitness
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 * @param fitness  the fitness to set
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * @return  the energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * @param energy  the energy to set
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * @return  the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life  the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * @return the distanceTraveled
	 */
	public double getDistanceTraveled() {
		return distanceTraveled;
	}

	/**
	 * @param distanceTraveled the distanceTraveled to set
	 */
	public void setDistanceTraveled(double distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	/**
	 * @return the graphic
	 */
	public ImageIcon getGraphic() {
		return graphic;
	}

	/**
	 * @param graphic the graphic to set
	 */
	public void setGraphic(ImageIcon graphic) {
		this.graphic = graphic;
	}

	/**
	 * @return the numOfEnergyCollected
	 */
	public int getNumOfEnergyCollected() {
		return numOfEnergyCollected;
	}

	/**
	 * @param numOfEnergyCollected the numOfEnergyCollected to set
	 */
	public void setNumOfEnergyCollected(int numOfEnergyCollected) {
		this.numOfEnergyCollected = numOfEnergyCollected;
	}

	/**
	 * @return the numOfMissileHits
	 */
	public int getNumOfMissileHits() {
		return numOfMissileHits;
	}

	/**
	 * @param numOfMissileHits the numOfMissileHits to set
	 */
	public void setNumOfMissileHits(int numOfMissileHits) {
		this.numOfMissileHits = numOfMissileHits;
	}

	/**
	 * @return the numOfMissilesFired
	 */
	public int getNumOfMissilesFired() {
		return numOfMissilesFired;
	}

	/**
	 * @param numOfMissilesFired the numOfMissilesFired to set
	 */
	public void setNumOfMissilesFired(int numOfMissilesFired) {
		this.numOfMissilesFired = numOfMissilesFired;
	}

	/**
	 * @return the numOfLifeHealed
	 */
	public int getNumOfLifeHealed() {
		return numOfLifeHealed;
	}

	/**
	 * @param numOfLifeHealed the numOfLifeHealed to set
	 */
	public void setNumOfLifeHealed(int numOfTimesHealed) {
		this.numOfLifeHealed = numOfTimesHealed;
	}

	/**
	 * Get the UI representation of this Agent.
	 * @return the panel
	 */
	public AgentPanel getPanel() {
		//Create a panel if one doesn't exist, deals with it being transient
		if (panel == null) setPanel(new AgentPanel(this));
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(AgentPanel panel) {
		this.panel = panel;
	}

	/**
	 * @return the numOfPlayersKilled
	 */
	public int getNumOfPlayersKilled() {
		return numOfPlayersKilled;
	}

	/**
	 * @param numOfPlayersKilled the numOfPlayersKilled to set
	 */
	public void setNumOfPlayersKilled(int numOfKilledUsers) {
		this.numOfPlayersKilled = numOfKilledUsers;
	}

	/**
	 * @return the numOfEnemyDamage
	 */
	public int getNumOfEnemyDamage() {
		return numOfEnemyDamage;
	}

	/**
	 * @param numOfEnemyDamage the numOfEnemyDamage to set
	 */
	public void setNumOfEnemyDamage(int numOfEnemyDamage) {
		this.numOfEnemyDamage = numOfEnemyDamage;
	}

	/**
	 * @return the numOfMissileCost
	 */
	public int getNumOfMissileCost() {
		return numOfMissileCost;
	}

	/**
	 * @param numOfMissileCost the numOfMissileCost to set
	 */
	public void setNumOfMissileCost(int numOfMissileEnergy) {
		this.numOfMissileCost = numOfMissileEnergy;
	}
	
}
