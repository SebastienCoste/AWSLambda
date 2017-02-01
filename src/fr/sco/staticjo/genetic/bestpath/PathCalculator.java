package fr.sco.staticjo.genetic.bestpath;

import java.util.stream.IntStream;

import fr.sco.staticjo.genetic.FitnessCalc;
import fr.sco.staticjo.genetic.Person;

public class PathCalculator implements FitnessCalc {

	@Override
	public int getFitness(Person person) {
		WorldMap map = (WorldMap) person;
		Point[] points = WorldMap.getPointList();
		double sum = IntStream.range(0, map.geneSize()-1).mapToDouble(e -> points[map.getGene(e).intValue()].getDistanceTo(points[map.getGene(e+1).intValue()])).sum();
		return Double.valueOf(sum + points[map.getGene(0).intValue()].getDistanceTo(points[map.getGene(map.geneSize()-1).intValue()])).intValue();
	}

	@Override
	public int getMaxFitness() {
		return 40;
	}

}
