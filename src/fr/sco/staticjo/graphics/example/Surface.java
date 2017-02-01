package fr.sco.staticjo.graphics.example;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import fr.sco.staticjo.graphics.DisplayLine;

public class Surface extends JPanel implements ActionListener {

	private volatile Drawer parent;
	private final int DELAY = 150;
	private Timer timer;

	//	public List<DrawPoint> points;
	public List<DisplayLine> lines;



	public Surface(Drawer parent) {
		this.parent = parent;
		initTimer();
	}

	private void initTimer() {

		timer = new Timer(DELAY, this);
		timer.start();
	}

	public Timer getTimer() {

		return timer;
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setPaint(Color.blue);

		int w = getWidth();
		int h = getHeight();
		Random r = new Random();

		//		points = new ArrayList<>();
		lines = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			int x = Math.abs(r.nextInt()) % w;
			int y = Math.abs(r.nextInt()) % h;
			//			points.add(new DrawPoint(x, y, i));

			g2d.drawLine(x, y, x, y);
		}
		if (parent.lines != null){
			for (int i = 0; i < parent.lines.size(); i++) {
				DisplayLine l = parent.lines.get(i);
				g2d.drawLine(l.getSource().getX(), l.getSource().getY(), l.getDest().getX(), l.getDest().getY());
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}


}
