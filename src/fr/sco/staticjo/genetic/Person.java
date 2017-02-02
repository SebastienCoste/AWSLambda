package fr.sco.staticjo.genetic;

public interface Person{

	void generatePerson();

	Long getGene(int index);
	
	Long[] getGenes();

	void setGene(int index, Long value);
	
	void setGenes(Long[] genes);

	int getFitness();
	
	int geneSize();

}
