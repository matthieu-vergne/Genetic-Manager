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

		generator.setMutationRate(0.01);
		for (int i = 0; i < 5; i++) {
			Incubator incubator = new Incubator();
			incubator.setGenerationSize(10);
			incubator.setDoubleKept(true);
			incubator.addIndividual(generator.createRandomIndividual());
			incubator.addIndividual(generator.createRandomIndividual());

			cultureRoom.add(incubator);
		}

		initCanvas();
		while (true) {
			for (Incubator incubator : cultureRoom.getIncubators()) {
				incubator.crossPopulation();
				incubator.makeSelection();
			}

			Incubator incubator = cultureRoom.getBestIncubator();

			displayResult(incubator);
		}
	}

	private static void displayResult(Incubator incubator) {
		Individual bestIndividual2 = incubator.getBestIndividual();
		if (isBetterPath(bestIndividual2.getPath())) {
			canvas.setPath(bestIndividual2.getPath());
			System.out.println(String.format("%7d", cultureRoom.getIncubator(0)
					.getGenerationCounter())
					+ " : " + bestIndividual2);
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
