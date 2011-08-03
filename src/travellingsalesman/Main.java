package travellingsalesman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Main {
	private static JCanvas canvas;
	private static CultureRoom cultureRoom = new CultureRoom();

	public static void main(String[] args) {
		IndividualFactory generator = new IndividualFactory();
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 10; y++) {
				generator.addLocation(new Location(x, y));
			}
		}

		generator.setMutationRate(0.001);
		for (int i = 0; i < 2; i++) {
			Incubator incubator = new Incubator();
			incubator.setGenerationSize(10);
			incubator.setDoubleKept(true);

			cultureRoom.add(incubator);
		}

		initCanvas();
		for (Incubator incubator : cultureRoom.getIncubators()) {
			incubator.addIndividual(generator.createRandomIndividual());
			incubator.addIndividual(generator.createRandomIndividual());
		}
		while (true) {
			for (Incubator incubator : cultureRoom.getIncubators()) {
				incubator.crossPopulation();
				incubator.makeSelection();
			}
			
			int incubatorIndex = getBestIncubator();
			
			displayResult(incubatorIndex);
		}
	}

	private static void displayResult(int incubatorIndex) {
		Individual bestIndividual2 = cultureRoom.getIncubator(incubatorIndex).getBestIndividual();
		if (isBetterPath(bestIndividual2.getPath())) {
			canvas.setPath(bestIndividual2.getPath());
			System.out.println(String.format("(%d) %7d", incubatorIndex,
					cultureRoom.getIncubator(0).getGenerationCounter())
					+ " : "
					+ bestIndividual2);
		}
	}

	private static int getBestIncubator() {
		Individual bestIndividual = null;
		int incubatorIndex = -1;
		for (int i = 0; i < cultureRoom.getIncubatorCounter(); i++) {
			Incubator incubator = cultureRoom.getIncubator(i);
			Individual individual = incubator.getBestIndividual();
			if (bestIndividual == null
					|| Util.getPathLength(bestIndividual.getPath()) > Util
							.getPathLength(individual.getPath())) {
				bestIndividual = individual;
				incubatorIndex = i;
			}
		}
		return incubatorIndex;
	}

	private static boolean isBetterPath(Location[] path) {
		return Util.getPathLength(path) < Util.getPathLength(canvas.getPath());
	}

	private static void initCanvas() {
		JFrame frame = new JFrame("Travelling Salesman");
		WindowAdapter wa = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		canvas = new JCanvas();
		canvas.setBackground(Color.WHITE);
		canvas.setPreferredSize(new Dimension(1000, 500));
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setVisible(true);
	}

}
