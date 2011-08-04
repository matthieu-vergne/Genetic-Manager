package travellingsalesman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import travellingsalesman.mutation.GeneMutation;

public class Main {
	private static JCanvas canvas;
	private static CultureRoom cultureRoom = new CultureRoom();
	private static GeneMutation fastMutation;
	private static GeneMutation bigMutation;

	public static void main(String[] args) {
		IndividualFactory factory = new IndividualFactory();
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 10; y++) {
				factory.addLocation(new Location(x, y));
			}
		}
		fastMutation = new GeneMutation() {

			@Override
			public double getRate() {
				return 0.01;
			}

			@Override
			public void mutates(Integer[] genes, int index) {
				int newIndex = Util.randomIndex(genes.length);
				int temp = genes[index];
				genes[index] = genes[newIndex];
				genes[newIndex] = temp;
			}
		};
		bigMutation = new GeneMutation() {

			@Override
			public double getRate() {
				return 0.01;
			}

			@Override
			public void mutates(Integer[] genes, int index) {
				int start = index;
				int end = Util.randomIndex(genes.length);
				if (start > end) {
					int temp = start;
					start = end;
					end = temp;
				}
				while (start < end) {
					int temp = genes[start];
					genes[start] = genes[end];
					genes[end] = temp;
					start++;
					end--;
				}
			}
		};

		for (int i = 0; i < 1; i++) {
			Incubator incubator = new Incubator("" + i);
			incubator.setGenerationSize(10);
			incubator.setDoubleKept(true);
			incubator.addIndividual(factory.createRandomIndividual());
			incubator.addIndividual(factory.createRandomIndividual());

			cultureRoom.add(incubator);
		}

		initCanvas();
		while (true) {
			for (Incubator incubator : cultureRoom.getIncubators()) {
				if (incubator.getStationaryTime() > 100) {
					factory.setMutation(bigMutation);
				} else {
					factory.setMutation(fastMutation);
				}
				incubator.addIndividual(factory.createRandomIndividual());
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
				Individual localBest = incubator.getBestIndividual();
				String str = incubator
						+ " = "
						+ ((double) Math.round(localBest.getLength() * 100) / 100);
				if (localBest.getFactory().getMutation() == bigMutation) {
					str = "!" + str;
				}
				if (best == localBest) {
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
