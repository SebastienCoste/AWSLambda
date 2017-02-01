package fr.sco.staticjo.graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;

public class SimpleDrawer extends JFrame {

	public List<DisplayPoint> points;
	private int mapSize;
	private int pixel = 1;
	public static int MARGIN = 20;
	public SimpleSurface surface;
	public volatile List<DisplayLine> lines;

	public SimpleDrawer(List<DisplayPoint> points, int mapSize, int worldSize) {
		this.points = points;
		this.mapSize = mapSize;
		setPixel(mapSize / worldSize);
		surface = new SimpleSurface(this);
		initUI();
	}
	private void initUI() {
		initUI(surface);
	}
	private void initUI(SimpleSurface s) {


//		s.setPoints(points);
		add(s);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Timer timer = surface.getTimer();
				timer.stop();
			}
		});

		setTitle("path");
		setSize(mapSize + MARGIN, mapSize + MARGIN);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	static DisplayLine l(int s, int d, List<DisplayPoint> p){
		return new DrawLine(p.get(s), p.get(d));
	}

	public SimpleSurface getSurface(){
		return surface;
	}
	public List<DisplayLine> getLines() {
		return lines;
	}
	public int getPixel() {
		return pixel;
	}
	public void setPixel(int pixel) {
		this.pixel = pixel;
	}
}
