package fr.sco.staticjo.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SimpleSurface extends JPanel implements ActionListener {

	private volatile SimpleDrawer parent;
	private final int DELAY = 150;
	private Timer timer;
	private int pixel;
	private int pixelmargin = 0;

	public SimpleSurface(SimpleDrawer parent) {
		this.parent = parent;
		pixel = parent.getPixel();
		pixelmargin = parent.MARGIN /2;
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

		parent.points.stream().forEach(p -> g2d.drawOval(p.getX()*pixel + pixelmargin, p.getY()*pixel + pixelmargin, 5, 5));
		if (parent.lines != null){
			for (int i = 0; i < parent.lines.size(); i++) {
				DisplayLine l = parent.lines.get(i);
				
				g2d.drawLine(l.getSource().getX()*pixel + pixelmargin, 
						l.getSource().getY()*pixel + pixelmargin, 
						l.getDest().getX()*pixel + pixelmargin, 
						l.getDest().getY()*pixel + pixelmargin);
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
