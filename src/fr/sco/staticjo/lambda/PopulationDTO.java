package fr.sco.staticjo.lambda;

import java.io.Serializable;
import java.util.stream.IntStream;

import fr.sco.staticjo.genetic.Population;
import fr.sco.staticjo.genetic.bestpath.Point;

public class PopulationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorldMapDTO[] person;
	private Integer popSize;
	private Point[] pointList;
	
	public PopulationDTO(){
		
	}
	public PopulationDTO(Population pop){
		popSize = pop.size();
		person = new WorldMapDTO[popSize];
		IntStream.range(0, popSize).forEach(e -> person[e] = new WorldMapDTO(pop.getPerson(e)));
	}

	public WorldMapDTO[] getPerson() {
		return person;
	}

	public void setPerson(WorldMapDTO[] person) {
		this.person = person;
	}

	public Integer getPopSize() {
		return popSize;
	}

	public void setPopSize(Integer popSize) {
		this.popSize = popSize;
	}
	public Point[] getPointList() {
		return pointList;
	}
	public void setPointList(Point[] pointList) {
		this.pointList = pointList;
	}
}
