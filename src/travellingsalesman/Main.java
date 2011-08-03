package travellingsalesman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Main {
	private static travellingsalesman.JCanvas canvas;
	private static List<Incubator> incubators = new ArrayList<Incubator>();

	public static void main(String[] args) {
		IndividualGenerator generator = new IndividualGenerator();
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 10; y++) {
				generator.addLocation(new Location(x, y));
			}
		}

		generator.setMutationRate(0.001);
		for (int i = 0; i < 2; i++) {
			Incubator incubator = new Incubator();
			incubator.setGenerator(generator);
			incubator.setGenerationSize(10);
			incubator.setGenerationRenew(0);
			incubator.setDoubleKept(true);

			incubators.add(incubator);
		}

		initCanvas();
		for (Incubator incubator : incubators) {
			incubator.initGeneration();
		}
		while (true) {
			for (Incubator incubator : incubators) {
				incubator.generateNextGeneration();
				incubator.makeSelection();
			}
			displayResult();
		}
	}

	public static void displayResult() {
		Individual bestIndividual = null;
		int incubatorIndex = -1;
		for (int i = 0; i < incubators.size(); i++) {
			Incubator incubator = incubators.get(i);
			Individual individual = incubator.getBestIndividual();
			if (bestIndividual == null
					|| Util.getPathLength(bestIndividual.getPath()) > Util
							.getPathLength(individual.getPath())) {
				bestIndividual = individual;
				incubatorIndex = i;
			}
		}
		if (isBetterPath(bestIndividual.getPath())) {
			canvas.setPath(bestIndividual.getPath());
			System.out.println(String.format("(%d) %7d", incubatorIndex,
					incubators.get(0).getGenerationCounter())
					+ " : "
					+ bestIndividual);
		}
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
