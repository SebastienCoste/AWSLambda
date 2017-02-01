package fr.sco.staticjo.graphics.example;

import fr.sco.staticjo.graphics.DisplayPoint;

public class DrawPoint implements DisplayPoint {

	private int x;
	private int y;
	private int id;
	
	public DrawPoint(int xx, int yy, int id){
		x = xx;
		y = yy;
		this.id = id;
	}

	@Override
	public int getX() {
		return x;
	}
	@Override
	public void setX(int x) {
		this.x = x;
	}
	@Override
	public int getY() {
		return y;
	}
	@Override
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
}
