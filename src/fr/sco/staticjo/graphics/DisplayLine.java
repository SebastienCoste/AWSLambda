package fr.sco.staticjo.graphics;

public interface DisplayLine {

	
	public DisplayPoint getSource();
	public void setSource(DisplayPoint source);
	public DisplayPoint getDest();
	public void setDest(DisplayPoint dest);
	
	default DisplayPoint getLower(){
		return getSource().getId() > getDest().getId() ? getDest() : getSource();
	}
	default DisplayPoint getHigher(){
		return getSource().getId() > getDest().getId() ? getSource() : getDest();
	}
	default boolean isSameline(DisplayLine test){
		return getLower().isSameCoord(test.getLower()) && getHigher().isSameCoord(test.getHigher());
	}
	
}
