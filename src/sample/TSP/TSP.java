package sample.TSP;

import geneticmanager.mutation.GeneMutation;

public class TSP {
	private static JCanvas canvas = new JCanvas();
	private static GeneMutation<Integer> littleMutation;
	private static GeneMutation<Integer> bigMutation;
	private static IndividualFactory factory;
	private static Incubator incubator;
	private static long startTime;

	public static void main(String[] args) {
		factory = new IndividualFactory();
		for (Location location : CsvReader.parse("src/sample/TSP/defi250.csv")) {
			factory.addLocation(location);
		}

		incubator = new Incubator();
		incubator.setFactory(factory);
		incubator.setGenerationSize(10);
		incubator.setDoubleKept(true);
		incubator.addIndividual(factory.createRandomIndividual());
		incubator.addIndividual(factory.createRandomIndividual());

		initMutations();
		startTime = System.currentTimeMillis();
		while (true) {
			if (incubator.getStationaryTime() > 100) {
				factory.setMutation(bigMutation);
			} else {
				factory.setMutation(littleMutation);
			}

			incubator.crossPopulation();
			incubator.makeSelection();

			displayResult();
		}
	}

	private static void initMutations() {
		littleMutation = new GeneMutation<Integer>() {

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

		bigMutation = new GeneMutation<Integer>() {

			@Override
			public double getRate() {
				return littleMutation.getRate();
			}

			@Override
			public void mutates(Integer[] genes, int index) {
				if (Math.random() < 0.5) {
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

	private static void displayResult() {
		Individual best = incubator.getBestIndividual();
		if (isBetterPath(best.getLocations())) {
			canvas.setPath(best.getLocations());
			double time = (double) (System.currentTimeMillis() - startTime) / 1000;
			String terminal = String.format("%8.3fs| %8d - ", time,
					factory.getFactoredCounter());
			if (best.getFactory().getMutation() == bigMutation) {
				terminal += "+";
			}
			terminal += ((double) Math.round(best.getLength() * 100) / 100);
			System.out.println(terminal);
		}
	}

	private static boolean isBetterPath(Location[] path) {
		return Util.getPathLength(path) < Util.getPathLength(canvas.getPath());
	}

}
