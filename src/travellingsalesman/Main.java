package travellingsalesman;

import travellingsalesman.mutation.GeneMutation;

public class Main {
	private static JCanvas canvas = new JCanvas();
	private static CultureRoom cultureRoom = new CultureRoom();
	private static GeneMutation littleMutation;
	private static GeneMutation bigMutation;
	private static IndividualFactory factory;

	public static void main(String[] args) {
		factory = new IndividualFactory();
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				factory.addLocation(new Location(x, y));
			}
		}

		for (int i = 0; i < 1; i++) {
			Incubator incubator = new Incubator("" + i);
			incubator.setGenerationSize(10);
			incubator.setDoubleKept(true);
			incubator.addIndividual(factory.createRandomIndividual());
			incubator.addIndividual(factory.createRandomIndividual());

			cultureRoom.add(incubator);
		}

		initMutations();
		while (true) {
			for (Incubator incubator : cultureRoom.getIncubators()) {
				if (incubator.getStationaryTime() > 100) {
					factory.setMutation(bigMutation);
				} else {
					factory.setMutation(littleMutation);
				}

				incubator.crossPopulation();
				incubator.makeSelection();
			}

			Individual best = cultureRoom.getBestIncubator()
					.getBestIndividual();

			displayResult(best);
		}
	}

	private static void initMutations() {
		littleMutation = new GeneMutation() {

			@Override
			public double getRate() {
				return 0.01;
			}

			@Override
			public void mutates(Integer[] genes, int index) {
				int newIndex = Util.randomIndex(genes.length);
				while (index != newIndex) {
					int tempIndex = index < newIndex ? index + 1 : index - 1;
					int temp = genes[index];
					genes[index] = genes[tempIndex];
					genes[tempIndex] = temp;
					index = tempIndex;
				}
			}
		};

		bigMutation = new GeneMutation() {

			@Override
			public double getRate() {
				return littleMutation.getRate();
			}

			@Override
			public void mutates(Integer[] genes, int index) {
				if (Math.random() < 0.1) {
					littleMutation.mutates(genes, index);
				} else {
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
			}
		};
	}

	private static void displayResult(Individual best) {
		if (isBetterPath(best.getPath())) {
			canvas.setPath(best.getPath());
			String terminal = String.format("%8d - ",
					factory.getFactoredCounter());
			for (Incubator incubator : cultureRoom.getIncubators()) {
				Individual localBest = incubator.getBestIndividual();
				String str = incubator
						+ " = "
						+ ((double) Math.round(localBest.getLength() * 100) / 100);
				if (localBest.getFactory().getMutation() == bigMutation) {
					str = "+" + str;
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

}
