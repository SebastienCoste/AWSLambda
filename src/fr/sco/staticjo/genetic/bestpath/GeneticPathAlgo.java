package fr.sco.staticjo.genetic.bestpath;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.sco.staticjo.genetic.GeneticAlgo;
import fr.sco.staticjo.genetic.Person;
import fr.sco.staticjo.genetic.Population;

public class GeneticPathAlgo extends GeneticAlgo<WorldMap> {


	
	private double genomeMutationRate;
	
	public GeneticPathAlgo(int populationSize){
		super(populationSize);
		genomeMutationRate = Math.pow(1 - GeneticAlgo.mutationRate, WorldMap.numberOfCities);
	}
	
	@Override
	protected Person crossover(Population<WorldMap> pop, Person indiv1, Person indiv2) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		WorldMap newborn = new WorldMap();
		WorldMap parent1 = (WorldMap) indiv1;
		WorldMap parent2 = (WorldMap) indiv2;
		boolean useParent1 = ThreadLocalRandom.current().nextInt(0,1) == 0;
		WorldMap parentref = parent1;
		WorldMap parentbis = parent2;
		if (useParent1){
			newborn.setGenes(new Long[parent1.geneSize()]);
		} else {
			newborn.setGenes(new Long[parent2.geneSize()]);
			parentref = parent2;
			parentbis = parent1;
		}

		int length = ThreadLocalRandom.current().nextInt(0,Double.valueOf(uniformRate*parentref.geneSize()).intValue());

		int start = ThreadLocalRandom.current().nextInt(0, parentref.geneSize() - length);
		Long[] parentRefGenotype = Arrays.copyOfRange(parentref.getGenes(), start, start + length);
		List<Long> parentRefGenotypeList = IntStream.range(0, parentRefGenotype.length).mapToObj(e -> parentRefGenotype[e]).collect(Collectors.toList());
		int range = 0;
		int rangeParOff = 0;
		while (range < start){
			Long testGene = parentbis.getGene(rangeParOff);
			if (!parentRefGenotypeList.contains(testGene)){
				newborn.setGene(range, testGene);
				range++;
			}
			rangeParOff++;
		}
		while (range < start+length){
			newborn.setGene(range, parentref.getGene(range));
			range++;
		}
		while (rangeParOff < parentbis.geneSize()){
			Long testGene = parentbis.getGene(rangeParOff);
			if (!parentRefGenotypeList.contains(testGene)){
				newborn.setGene(range, testGene);
				range++;
			}
			rangeParOff++;
		}
		return newborn;
	}

	@Override
	protected void mutate(Person person) {
		WorldMap indiv = (WorldMap) person;
		Map<Long, Integer> timesSeen = new HashMap<>();
		for (int i = 0; i < indiv.geneSize(); i++) {
			Long gene = indiv.getGene(i);
			Integer seen = timesSeen.get(gene);
			if (seen == null){
				seen = Integer.valueOf(1);
			} else {
				seen = seen +1;
			}
			timesSeen.put(gene, seen);
			if (Math.random() <= mutationRate) {
				int end = ThreadLocalRandom.current().nextInt(0, indiv.geneSize());
				indiv.setGene(i, indiv.getGene(end));
				indiv.setGene(end, gene);
			}
		}
		
		if (Math.random() <= genomeMutationRate){
			int start = ThreadLocalRandom.current().nextInt(0, indiv.geneSize()-1);
			int end = ThreadLocalRandom.current().nextInt(start, indiv.geneSize());
			Long[] save = new Long[end-start +1];
			IntStream.range(start, end +1).forEach(e ->  {
				save[e-start] = indiv.getGene(end + start - e);
			});
			IntStream.range(start, end +1).forEach(e ->  {
				indiv.setGene(e, save[e-start]);
			});
		}
	}
}
