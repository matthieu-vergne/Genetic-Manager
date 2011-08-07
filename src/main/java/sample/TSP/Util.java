package sample.TSP;

abstract public class Util {

	/**
	 * 
	 * @param total
	 * @return a integer in [0,total-1]
	 */
	public static int randomIndex(int total) {
		return (int) Math.floor(Math.random() * total);
	}

	public static double getPathLength(Location... path) {
		if (path == null || path.length == 0) {
			return Double.POSITIVE_INFINITY;
		}

		double length = 0;
		for (int index = 0; index < path.length - 1; index++) {
			Location start = path[index];
			Location end = path[index + 1];
			double dx = end.getX() - start.getX();
			double dy = end.getY() - start.getY();
			// double distance = dx * dx + dy * dy;
			double distance = Math.sqrt(dx * dx + dy * dy);
			length += distance;
		}
		return length;
	}

}
