package travellingsalesman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

public class JCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private List<Location> path = new ArrayList<Location>();

	public void setPath(Location... locations) {
		path = Arrays.asList(locations);
		repaint();
	}

	public Location[] getPath() {
		return path.toArray(new Location[0]);
	}

	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());

		if (path.isEmpty()) {
			return;
		}
		
		int xMax = 1;
		int yMax = 1;
		for (Location location : path) {
			xMax = Math.max(location.getX(), xMax);
			yMax = Math.max(location.getY(), yMax);
		}

		final int radius = 5;
		final int xRate = (getWidth() - radius) / xMax;
		final int yRate = (getHeight() - radius) / yMax;

		Color originalColor = g.getColor();

		g.setColor(Color.RED);
		List<Location> temp = new ArrayList<Location>(path);
		Location start = temp.remove(0);
		while (!temp.isEmpty()) {
			Location end = temp.remove(0);
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
