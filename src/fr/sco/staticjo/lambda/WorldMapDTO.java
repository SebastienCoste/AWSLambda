package fr.sco.staticjo.lambda;

import java.io.Serializable;

import fr.sco.staticjo.genetic.Person;
import fr.sco.staticjo.genetic.bestpath.WorldMap;

public class WorldMapDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long[] genes;

	public WorldMapDTO(Person wm){
		genes = wm.getGenes();
	}
	public Long[] getGenes() {
		return genes;
	}

	public void setGenes(Long[] genes) {
		this.genes = genes;
	}
}
