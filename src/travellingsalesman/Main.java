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
		for (int i = 0; i < 10; i++) {
			Incubator incubator = new Incubator("" + i);
			incubator.setGenerationSize(10);
			incubator.setDoubleKept(true);
			incubator.addIndividual(generator.createRandomIndividual());
			incubator.addIndividual(generator.createRandomIndividual());
			incubator.addIndividual(generator.createRandomIndividual());
			incubator.addIndividual(generator.createRandomIndividual());
			incubator.addIndividual(generator.createRandomIndividual());

			cultureRoom.add(incubator);
		}

		initCanvas();
		while (true) {
			for (Incubator incubator : cultureRoom.getIncubators()) {
				if (incubator.getStationaryTime() > 1000
						&& incubator.getGenerationCounter() > 10000) {
					Individual best = incubator.getBestIndividual();
					incubator.clean();
					incubator.addIndividual(best);
					for (Incubator source : cultureRoom.getIncubators()) {
						incubator.addIndividual(source.getBestIndividual());
					}
				}
				incubator.crossPopulation();
				incubator.makeSelection();
			}

			Individual best = cultureRoom.getBestIncubator()
					.getBestIndividual();

			displayResult(best);
		}
	}

	private static void displayResult(Individual best) {
		if (isBetterPath(best.getPath())) {
			canvas.setPath(best.getPath());
			String terminal = "";
			for (Incubator incubator : cultureRoom.getIncubators()) {
				Individual best2 = incubator.getBestIndividual();
				String str = incubator + " = "
						+ ((double) Math.round(best2.getLength() * 100) / 100);
				if (best == best2) {
					str = "[" + str + "]";
				}
				terminal += str + " ";
			}
			System.out.println(terminal);
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
