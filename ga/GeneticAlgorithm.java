package ga;

import game.Agent;
import game.EntityManager;
import game.World;
import java.util.LinkedList;
import java.util.Random;

/**
 * The top level GA class, used by other interested classes to interact with the GA.
 * The main GA algorithm is defined in newEpoch()
 * @author  Matt Oates
 */
public class GeneticAlgorithm {
	
	public static final int DATA_RUN_EPOCH_LIMIT = 100;
	public static final Random DATA_RUN_RANDOM = new Random(8589934592l);
	
	//Epoch time in milliseconds
	private int epochTime = 30000;
	private int goalFitness = 10;
	private int goalEpoch = 100;
	private int currentEpoch = 0;
	
	public static int populationSize = 10;
	public static int chromosomeSize = 7;
	
	public static double crossoverProbability = 0.3;
	public static double geneMutationProbability = 0.05;
	public static double alleleMutationProbability = 0.3;
	public static double symbioticProbability = 0.8;
	
	//Operators
	private Selection selection = null;
	private Crossover crossover = null;
	private Symbiosis symbiosis = null;
	
	private EntityManager manager = null;
	private LinkedList<Agent> population = null;
	
	private StringBuffer statistics = new StringBuffer();
	
	/**
	 * Create a GA with good default parameters and genetic operators.
	 * Other constructors used with user dialogue for tayloring the GA.
	 */
	public GeneticAlgorithm() {
		super();
		selection = GeneticAlgorithmFactory.getSelection("Tournament");
		crossover = GeneticAlgorithmFactory.getCrossover("SinglePoint");
		symbiosis = GeneticAlgorithmFactory.getSymbiosis("PerfectHorizontal");
		population = new LinkedList<Agent>();
		addStatisticsHeader();
	}
	
	/**
	* Construct a GA with known selection and crossover operators.
	* @param select The selection method to be used.
	* @param cross The crossover method to be used.
	* @see ga.GeneticAlgorithmFactory
	* @see ga.selection
	* @see ga.crossover
	*/
	public GeneticAlgorithm(Selection select, Crossover cross) {
		super();
		selection = select;
		crossover = cross;
		symbiosis = GeneticAlgorithmFactory.getSymbiosis("None");
		population = new LinkedList<Agent>();
		addStatisticsHeader();
	}
	
	/**
	 * Construct a GA with all operators defined.
	 * @param select The selection method to be used.
   	 * @param cross The crossover method to be used.
	 * @param symb The symbiosis method to be used.
	 * @see ga.GeneticAlgorithmFactory
	 * @see ga.selection
	 * @see ga.crossover 
	 * @see ga.symbiosis 
	 */
	public GeneticAlgorithm(Selection select, Crossover cross, Symbiosis symb) {
		super();
		selection = select;
		crossover = cross;
		symbiosis = symb;
		population = new LinkedList<Agent>();
		addStatisticsHeader();
	}
	
	/**
	 * Append a header row to the statistics string.
	 */
	private void addStatisticsHeader() {
		statistics.append("\"Generation #\",");
		statistics.append("\"Num Agents\",");
		statistics.append("\"Num Chromosomes\",");
		statistics.append("\"Min Fitness\",");
		statistics.append("\"Max Fitness\",");
		statistics.append("\"Avg Fitness\",");
		statistics.append("\"Tick Time\",");
		statistics.append("\"Avg Distance Traveled\",");
		statistics.append("\"Avg Missiles Fired\",");
		statistics.append("\"Avg Missiles Hit\",");
		statistics.append("\"Avg Missile Cost\",");
		statistics.append("\"Avg Enemy Damage\",");
		statistics.append("\"Avg End Life\",");
		statistics.append("\"Avg End Energy\",");
		statistics.append("\"Selection Type\",");
		statistics.append("\"Crossover Type\",");
		statistics.append("\"Symbiosis Type\",");
		statistics.append("\"P(Crossover)\",");
		statistics.append("\"P(Type Mutate)\",");
		statistics.append("\"P(Allele Mutate)\",");
		statistics.append("\"P(Symbiosis)\"\n");
	}
	
	/**
	 * Append a data row to the statistics string.
	 */
	private void addStatisticsRow() {
		//Generation #
		statistics.append(currentEpoch+",");
		//Num Agents
		statistics.append(population.size()+",");
		
		int numOfChromosomes = 0;
		double minFitness = population.getFirst().getFitness();
		double maxFitness = population.getFirst().getFitness();
		double avgFitness = 0.0;
		double avgDistanceTraveled = 0.0;
		double avgMissilesFired = 0.0;
		double avgMissilesHit = 0.0;
		double avgMissileCost = 0.0;
		double avgEnemyDamage = 0.0;
		double avgEndLife = 0.0;
		double avgEndEnergy = 0.0;
		
		for (Agent agent : population) {
			numOfChromosomes += agent.getChromosomes().size();
			if (agent.getFitness() < minFitness)
				minFitness = agent.getFitness();
			if (agent.getFitness() > maxFitness)
				maxFitness = agent.getFitness();
			avgFitness += agent.getFitness();
			avgDistanceTraveled += agent.getDistanceTraveled();
			avgMissilesFired += agent.getNumOfMissilesFired();
			avgMissilesHit += agent.getNumOfMissileHits();
			avgMissileCost += agent.getNumOfMissileCost();
			avgEnemyDamage += agent.getNumOfEnemyDamage();
			avgEndLife += agent.getLife();
			avgEndEnergy += agent.getEnergy();
		}
		avgFitness /= population.size();
		avgDistanceTraveled /= population.size();
		avgMissilesFired /= population.size();
		avgMissilesHit /= population.size();
		avgMissileCost /= population.size();
		avgEnemyDamage /= population.size();
		avgEndLife /= population.size();
		avgEndEnergy /= population.size();
		
		//Num Chromosomes
		statistics.append(numOfChromosomes+",");
		//Min Fitness
		statistics.append(minFitness+",");
		//Max Fitness
		statistics.append(maxFitness+",");
		//Avg Fitness
		statistics.append(avgFitness+",");
		//Tick Time
		statistics.append(manager.getWorld().getTicks()+",");
		//Avg Distance Traveled
		statistics.append(avgDistanceTraveled+",");
		//Avg Missiles Fired
		statistics.append(avgMissilesFired+",");
		//Avg Missiles Hit
		statistics.append(avgMissilesHit+",");
		//Avg Missile Cost
		statistics.append(avgMissileCost+",");
		//Avg Enemy Damage
		statistics.append(avgEnemyDamage+",");
		//Avg End Life
		statistics.append(avgEndLife+",");
		//Avg End Energy
		statistics.append(avgEndEnergy+",");
		//Selection Type
		statistics.append(selection.getClass().getSimpleName()+",");
		//Crossover Type
		statistics.append(crossover.getClass().getSimpleName()+",");
		//Symbiosis Type
		if (symbiosis == null)
			statistics.append("None,");
		else
			statistics.append(symbiosis.getClass().getSimpleName()+",");
		//P(Crossover)
		statistics.append(crossoverProbability+",");
		//P(Type Mutate)
		statistics.append(geneMutationProbability+",");
		//P(Allele Mutate)
		statistics.append(alleleMutationProbability+",");
		//P(Symbiosis)
		statistics.append(symbioticProbability+"\n");
	}
	
	/**
	 * Initialise the GA with a new random Agent population.
	 */
	public void initPopulation(EntityManager manager) {
		this.manager = manager;
		for (int index = 0; index < populationSize; index++) {
			population.add(new Agent(manager));
		}
	}
	
	/**
	 * Express the phenotype of all chromosomes in the GA on to their parent Agents.
	 */
	public void expressPopulation() {
		for (Agent agent : population) {
			agent.express();
		}
	}
	
	/**
	 * Generate a new GA epoch based on the current state of the game agents.
	 * This is the point at which all of the genetic operators are called.
	 */
	public void newEpoch() {
		
		if (World.IS_TEST)System.out.println("#### Creating a new Epoch:");
		if (World.IS_TEST)System.out.println("Ents before cleanup = "+ manager.getEnts().size());
		
		//Unregister everything from the previous epoch including missiles.
		manager.unregisterAll();
		
		if (World.IS_TEST)System.out.println("Ents after cleanup = "+ manager.getEnts().size());
		
		//Select parents for the new population.
		for (Agent agent : population) {
			agent.calcFitness();
			System.out.println(agent.getFitness());
		}
		
		//Create some statistics of the last epoch
		addStatisticsRow();
		
		System.out.println("Worked out fitness");
		LinkedList<Agent> selected = selection.select(this);
		
		if (World.IS_TEST)System.out.println("Original population ["+population.size()+"]:");
		if (World.IS_TEST)System.out.println(population);
		
		if (World.IS_TEST)System.out.println("Selected population ["+selected.size()+"]:");
		if (World.IS_TEST)System.out.println(selected);
		
		population = new LinkedList<Agent>();
		
		if (World.IS_TEST)System.out.println("Starting reproduction:");
		
		//Create a new generation from the selected population.
		for (Agent mother : selected) {
			if (population.size() < populationSize) {
				Agent father = mother;
				Chromosome[] children;
				
				/*Try to not select identical parents.
				  Can be a problem if we have a small number of agents and only
				  one has a fitness greater than 1. If this happens we will get
				  in to an infinite loop because the selected population only
				  contains one agent either once or multiple times.
				  In testing this came up with a population of 3 and testing
				  for the presence of one specific gene as a measure of fitness.
				  In practice this should never really be a problem.
				 */
				for (int i=0;(i<populationSize && mother == father);i++)father = getRandomAgent(selected);
					
				if (World.IS_TEST)System.out.println("\n----- New population size "+population.size()+" -----");
				if (World.IS_TEST)System.out.println("Mother: "+mother);
				if (World.IS_TEST)System.out.println("Father: "+mother);
				
				//Should we crossover?
				if (random() <= crossoverProbability) {					
										
					children = crossover.cross(mother.getChromosome(), father.getChromosome());
					
				}
				//If we don't crossover let the mother chromosomes through to the next generation
				else {
					if (World.IS_TEST)System.out.println("Not crossing over, parents going into new population.");
					children = new Chromosome[2];
					try {
						children[0] = (Chromosome)mother.getChromosome().clone();
					} catch (CloneNotSupportedException e) {
						System.err.println("Failed to clone and cast a Chromosome.");
						e.printStackTrace();
						System.exit(1);
					}
				}
				
				//Add the children to the new population
				if ((populationSize - population.size()) % 2 == 0) {
					
					if (World.IS_TEST)System.out.println("Might have mutated:");
					
					//Mutate the child chromosomes before creating the new agents
					children[0].mutate(alleleMutationProbability, geneMutationProbability);
					if (children[1] != null) children[1].mutate(alleleMutationProbability, geneMutationProbability);
					
					if (World.IS_TEST)System.out.println("Child 0: "+children[0]);
					if (World.IS_TEST)System.out.println("Child 1: "+children[1]);
					
					if (World.IS_TEST)System.out.println("Adding both as new agents.");
					population.add(new Agent(manager, children[0]));
					if (children[1] != null) population.add(new Agent(manager, children[1]));
				}
				else {
					//Mutate the child chromosomes before creating the new agents
					children[0].mutate(alleleMutationProbability, geneMutationProbability);
					if (World.IS_TEST)System.out.println("Child 0: "+children[0]);
					if (World.IS_TEST)System.out.println("Adding Child 0 as new agent.");
					population.add(new Agent(manager, children[0]));
				}
			}
			else break;
		}
		
		//Perform symbiosis from the selected population horizontally into the newly reproduced population
		symbiosis.symbiose(population, selected);
		
		//Have a go at trying to clean up the old epoch in memory.
		System.gc();
		currentEpoch++;
	}
	
	/**
	 * Return a randomized number when not doing a DATA_RUN, otherwise return a predictabel set.
	 * @return A double between 0 and 1.
	 */
	public static double random() {
		if (World.IS_DATA_RUN) {
			return DATA_RUN_RANDOM.nextDouble();
		}
		return Math.random();
	}
	
	/**
	 * Get a random Agent from the given population.
	 * @param agents Population to select from.
	 * @return A random Agent.
	 */
	private Agent getRandomAgent(LinkedList<Agent> agents) {
		return agents.get( (int) Math.round( random() * (agents.size() -1) ) );
	}

	/**
	 * @return  the population
	 */
	public LinkedList<Agent> getPopulation() {
		return population;
	}

	/**
	 * @param population  the population to set
	 */
	public void setPopulation(LinkedList<Agent> population) {
		this.population = population;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//return ""+crossoverProbability;
		return population.toString();
	}

	/**
	 * @return the crossover
	 */
	public Crossover getCrossover() {
		return crossover;
	}

	/**
	 * @param crossover the crossover to set
	 */
	public void setCrossover(Crossover crossover) {
		this.crossover = crossover;
	}

	/**
	 * @return the epochTime
	 */
	public int getEpochTime() {
		return epochTime;
	}

	/**
	 * @param epochTime the epochTime to set
	 */
	public void setEpochTime(int epochTime) {
		this.epochTime = epochTime;
	}

	/**
	 * @return the goalFitness
	 */
	public int getGoalFitness() {
		return goalFitness;
	}

	/**
	 * @param goalFitness the goalFitness to set
	 */
	public void setGoalFitness(int goalFitness) {
		this.goalFitness = goalFitness;
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
	 * @return the selection
	 */
	public Selection getSelection() {
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	/**
	 * @return the symbiosis
	 */
	public Symbiosis getSymbiosis() {
		return symbiosis;
	}

	/**
	 * @param symbiosis the symbiosis to set
	 */
	public void setSymbiosis(Symbiosis symbiosis) {
		this.symbiosis = symbiosis;
	}

	/**
	 * @return the statistics
	 */
	public StringBuffer getStatistics() {
		return statistics;
	}

	/**
	 * @param statistics the statistics to set
	 */
	public void setStatistics(StringBuffer statistics) {
		this.statistics = statistics;
	}

	/**
	 * @return the currentEpoch
	 */
	public int getCurrentEpoch() {
		return currentEpoch;
	}

	/**
	 * @param currentEpoch the currentEpoch to set
	 */
	public void setCurrentEpoch(int currentEpoch) {
		this.currentEpoch = currentEpoch;
	}

	/**
	 * @return the goalEpoch
	 */
	public int getGoalEpoch() {
		return goalEpoch;
	}

	/**
	 * @param goalEpoch the goalEpoch to set
	 */
	public void setGoalEpoch(int goalEpoch) {
		this.goalEpoch = goalEpoch;
	}
	
	
}
