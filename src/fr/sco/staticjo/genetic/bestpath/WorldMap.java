package fr.sco.staticjo.genetic.bestpath;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.sco.staticjo.genetic.FitnessCalc;
import fr.sco.staticjo.genetic.Person;

public class WorldMap implements Person, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -357322492597291683L;
	public static int numberOfCities;
	private Long[] genes;
	private static FitnessCalc calc;
	private static Point[] pointList;
	
	@Override
	public void generatePerson() {
		
		List<Point> sortedYpoints = new ArrayList<>(Arrays.asList(pointList));
		
		Collections.sort(sortedYpoints, getComparatorY(sortedYpoints.size()));
		
		setGenes(new Long[numberOfCities]);
		if (ThreadLocalRandom.current().nextInt(0,numberOfCities/2 +1)%(numberOfCities/2) != 0){
			
		List<Integer> allNumbers = IntStream.range(0, numberOfCities).boxed().collect(Collectors.toList());
		IntStream.range(0, numberOfCities).forEach(e -> {
			int rand = ThreadLocalRandom.current().nextInt(0, allNumbers.size());
			setGene(e, Long.valueOf(allNumbers.get(rand).toString()));
			allNumbers.remove(rand);
		});
		} else {
			IntStream.range(0, numberOfCities).forEach(e -> {
				setGene(e, Long.valueOf(sortedYpoints.get(e).getId()));
			});
		}
		if (geneSize() > numberOfCities){
			IntStream.range(numberOfCities, geneSize()).forEach(e ->
			setGene(e, Long.valueOf(Integer.valueOf(ThreadLocalRandom.current().nextInt(0, numberOfCities)).toString())));
		}
	}

	public Comparator<Point> getComparatorY(int size){
		
		int sqrt = Double.valueOf(Math.sqrt(size)).intValue();
		return new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				int id1 = o1.getX()+1;
				int id2 = o2.getX()+1;
				int x1 = (id1-id1%sqrt)/sqrt;
				int x2 = (id2-id2%sqrt)/sqrt;
				return x1 == x2 ? Integer.compare(o1.getY(), o2.getY()) : Integer.compare(x1, x2);
			}
		};
	}
	
	
	public static void setDefaultGeneLength(int length) {
		numberOfCities = length;
	}

	@Override
	public Long getGene(int index) {
		return getGenes()[index];
	}

	@Override
	public void setGene(int index, Long value) {
		getGenes()[index] = value;
	}

	@Override
	public int getFitness() {
		return getCalc().getFitness(this);
	}

	@Override
	public int geneSize() {
		return getGenes().length;
	}

	public static void addPoint(Point p, int index){
		pointList[index] = p;
	}
	public static Point[] getPointList() {
		return pointList;
	}

	public static void setPointList(Point[] points) {
		pointList = points;
	}

	@Override
	public Long[] getGenes() {
		return genes;
	}

	public void setGenes(Long[] genes) {
		this.genes = genes;
	}

	public WorldMap withGenes(Long[] genes) {
		this.genes = genes;
		return this;
	}
	
	public static FitnessCalc getCalc() {
		return calc;
	}

	public static void setCalc(FitnessCalc calc) {
		WorldMap.calc = calc;
	}

	@Override
	public String toString() {
		return "WorldMap [genes=" + Arrays.toString(genes) + ", fit=" + getFitness() + "]";
	}


}
