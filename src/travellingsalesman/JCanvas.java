package travellingsalesman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JFrame frame;
	private List<Location> path = new ArrayList<Location>();
	private int radius;
	private int xRate;
	private int yRate;

	public JCanvas() {
		frame = new JFrame("Travelling Salesman");
		WindowAdapter wa = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(1000, 600));
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
	}

	public void setPath(Location... locations) {
		boolean wasEmpty = path.isEmpty();
		path = Arrays.asList(locations);
		if (wasEmpty) {
			resize();
		} else {
			repaint();
		}
	}

	private void resize() {
		int xMax = 1;
		int yMax = 1;
		for (Location location : path) {
			xMax = Math.max(location.getX(), xMax);
			yMax = Math.max(location.getY(), yMax);
		}

		radius = 5;
		xRate = (getWidth() - radius) / xMax;
		yRate = (getHeight() - radius) / yMax;
		xRate = Math.min(xRate, yRate);
		yRate = xRate;
		int dx = frame.getWidth() - getWidth();
		int dy = frame.getHeight() - getHeight();
		frame.setSize(xRate * xMax + radius + dx, yRate * yMax + radius + dy);
	}

	public Location[] getPath() {
		return path.toArray(new Location[0]);
	}

	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		Color originalColor = g.getColor();

		g.setColor(Color.RED);
		List<Location> remains = new ArrayList<Location>(path);
		remains.add(remains.get(0));
		Location start = remains.remove(0);
		while (!remains.isEmpty()) {
			Location end = remains.remove(0);
			int xStart = start.getX() * xRate;
			int yStart = start.getY() * yRate;
			int xEnd = end.getX() * xRate;
			int yEnd = end.getY() * yRate;
			int[] xPoints = new int[] { xStart, xEnd, xEnd, xStart };
			int[] yPoints = new int[] { yStart, yEnd, yEnd, yStart };
			if (xStart == xEnd) {
				xPoints[0]--;
				xPoints[1]--;
				xPoints[2]++;
				xPoints[3]++;
			} else {
				yPoints[0]++;
				yPoints[1]++;
				yPoints[2]--;
				yPoints[3]--;
			}
			g.fillPolygon(xPoints, yPoints, xPoints.length);
			start = end;
		}

		g.setColor(Color.BLUE);
		for (Location location : path) {
			int x = location.getX() * xRate - radius;
			int y = location.getY() * yRate - radius;
			int diameter = 2 * radius;
			g.fillOval(x, y, diameter, diameter);
		}

		g.setColor(originalColor);
	}

}
