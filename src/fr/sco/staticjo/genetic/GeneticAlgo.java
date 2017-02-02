package fr.sco.staticjo.genetic;

import java.io.IOException;
import java.net.MalformedURLException;

import fr.sco.staticjo.genetic.multithread.PoolOfThreads;

public class GeneticAlgo<P extends Person> {


	public static double uniformRate = 0.5;
	public static double mutationRate = 0.015;
	public static int tournamentSize = 5;
	public static int numberOfThreads = 8;
	public static boolean elitism = true;
	private Class<P> classPerson;
	private PoolOfThreads<P> pool;

	public GeneticAlgo(int populationSize)  throws MalformedURLException, IOException{
		pool = new PoolOfThreads<P>(numberOfThreads, populationSize, this);
	}

	public GeneticAlgo(int populationSize, Class<P> clazz) throws MalformedURLException, IOException{
		this(populationSize);
		setClassPerson(clazz);
	}

	public Population<P> evolvePopulation(Population<P> pop) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterruptedException  {
		Population<P> newPopulation = new Population<P>(pop.size(), false, getClassPerson());

		if (elitism) {
			newPopulation.savePerson(0, pop.getFittest());
		}

		for (int i = (elitism?1:0); i < pop.size(); i++) {
			pool.execute(pop);
//			Person newIndiv = generateChild(pop);
			
		}
		int consumed = (elitism?1:0);
		
		while (consumed < pop.size()){
			Person child = pool.getResult();
			if (child != null){
				newPopulation.savePerson(consumed, child);
				consumed++;
			}
		}

		return newPopulation;
	}

	public Person generateChild(Population<P> pop)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Person indiv1 = tournamentSelection(pop);
		Person indiv2 = tournamentSelection(pop);
		Person newIndiv = crossover(pop, indiv1, indiv2);
		mutate(newIndiv);
		return newIndiv;
	}

	protected Person crossover(Population<P> pop, Person indiv1, Person indiv2) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Person newSol = pop.instanciatePerson(getClassPerson());
		// Loop through genes
		for (int i = 0; i < indiv1.geneSize(); i++) {
			// Crossover
			if (Math.random() <= uniformRate) {
				newSol.setGene(i, indiv1.getGene(i));
			} else {
				newSol.setGene(i, indiv2.getGene(i));
			}
		}
		return newSol;
	}

	protected void mutate(Person indiv) {
		// Loop through genes
		for (int i = 0; i < indiv.geneSize(); i++) {
			if (Math.random() <= mutationRate) {
				// Create random gene
				Long gene = Math.round(Math.random());
				indiv.setGene(i, gene);
			}
		}
	}

	protected Person tournamentSelection(Population<P> pop) {
		// Create a tournament population
		Population<P> tournament = new Population<P>(tournamentSize, false, getClassPerson());
		// For each place in the tournament get a random individual
		for (int i = 0; i < tournamentSize; i++) {
			int randomId = (int) (Math.random() * pop.size());
			tournament.savePerson(i, pop.getPerson(randomId));
		}
		// Get the fittest
		Person fittest = tournament.getFittest();
		return fittest;
	}



	public Class<P> getClassPerson(){
		return this.classPerson;
	}
	public void setClassPerson(Class<P> clazz){
		this.classPerson = clazz;
	}









}

