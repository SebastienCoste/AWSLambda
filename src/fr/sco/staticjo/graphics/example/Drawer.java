package fr.sco.staticjo.graphics.example;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.Timer;

import fr.sco.staticjo.graphics.DisplayLine;
import fr.sco.staticjo.graphics.DisplayPoint;
import fr.sco.staticjo.graphics.DrawLine;

public class Drawer extends JFrame {

	public List<DisplayPoint> points;
	private int size;
	public Surface surface;
	public volatile List<DisplayLine> lines;

	public Drawer(List<DisplayPoint> points, int size) {
		this.points = points;
		this.size = size;
		surface = new Surface(this);
		initUI();
	}
	private void initUI() {
		initUI(surface);
	}
	private void initUI(Surface s) {


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
		setSize(size, size);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		int end = 200;
		int size = 400;
		List<DisplayPoint> points = IntStream.range(0, end).mapToObj(e -> p(e, size)).collect(Collectors.toList());


		Drawer ex = new Drawer(points, size);

		ex.setVisible(true);
		IntStream.range(0, end).forEach(a -> {
			try {
				Thread.sleep(1000);
			} catch (Exception e1) {
			}
			System.err.println("ex" + a);
			List<DisplayLine> lines = IntStream.range(1, ex.points.size()/2-2)
					.mapToObj(e -> l(ThreadLocalRandom.current().nextInt(0,e), 2*ThreadLocalRandom.current().nextInt(0,e), points))
					.collect(Collectors.toList());
			ex.lines = lines;
			ex.surface.lines = lines;
			ex.surface.updateUI();
			ex.surface.repaint();
		}
				);
	}

	static DisplayLine l(int s, int d, List<DisplayPoint> p){
		return new DrawLine(p.get(s), p.get(d));
	}

	private static DisplayPoint p(int id, int size){
		return new DrawPoint(ThreadLocalRandom.current().nextInt(0,size), ThreadLocalRandom.current().nextInt(0,size), id);
	}

	public Surface getSurface(){
		return surface;
	}
	public List<DisplayLine> getLines() {
		return lines;
	}
}
