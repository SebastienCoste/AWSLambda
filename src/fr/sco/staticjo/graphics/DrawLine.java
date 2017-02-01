package fr.sco.staticjo.graphics;

public class DrawLine implements DisplayLine{

	private DisplayPoint source;
	private DisplayPoint dest;
	
	public DrawLine(DisplayPoint start, DisplayPoint end){
		source = start;
		dest = end;
	}
	
	@Override
	public DisplayPoint getSource() {
		return source;
	}
	@Override
	public void setSource(DisplayPoint source) {
		this.source = source;
	}
	@Override
	public DisplayPoint getDest() {
		return dest;
	}
	@Override
	public void setDest(DisplayPoint dest) {
		this.dest = dest;
	}
	
}
