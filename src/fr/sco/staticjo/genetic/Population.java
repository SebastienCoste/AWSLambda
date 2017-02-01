package fr.sco.staticjo.genetic;

public class Population<P extends Person>{

	Person[] people;
	
	
	
	public Population(int populationSize, boolean initialise, Class<P> classPerson) { 
		people = new Person[populationSize];
		// Initialise population
		if (initialise) {
			for (int i = 0; i < people.length; i++) {
				Person person = null;
				try {
					person = instanciatePerson(classPerson);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				person.generatePerson();
				savePerson(i, person);
			}
		}
	}

	public Person instanciatePerson(Class<P> classPerson) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (Person)classPerson.getClassLoader().loadClass(classPerson.getName()).newInstance();
	}

	public Person getFittest() {
		Person fittest = people[0];
		for (int i = 0; i < size(); i++) {
			if (fittest.getFitness() >= getPerson(i).getFitness()) {
				fittest = getPerson(i);
			}
		}
		return fittest;
	}

	public int size() {
		return people.length;
	}

	
	
	public Person getPerson(int index) {
		return people[index];
	}

	public void savePerson(int index, Person indiv) {
		people[index] = indiv;
	}



}
