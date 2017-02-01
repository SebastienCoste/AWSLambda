package fr.sco.staticjo.graphics;

public interface DisplayPoint {

	public int getX();
	public void setX(int x);
	public int getY();
	public void setY(int y);
	public int getId();
	public void setId(int id);
	
	default boolean isSameCoord(DisplayPoint test){
		return getX() == test.getX() && getY() == test.getY();
	}
}
