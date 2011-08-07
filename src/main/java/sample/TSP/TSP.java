package sample.TSP;

import geneticmanager.mutation.IGeneMutation;

public class TSP {
	private static JCanvas canvas = new JCanvas();
	private static IGeneMutation<Integer> littleMutation;
	private static IGeneMutation<Integer> bigMutation;
	private static IGeneMutation<Integer> composedMutation;
	private static IndividualFactory factory;
	private static Incubator incubator;
	private static long startTime;

	public static void main(String[] args) {
		factory = new IndividualFactory();
		String filepath = TSP.class.getClassLoader().getResource("sample/TSP/defi250.csv").getFile();
		for (Location location : CsvReader.parse(filepath)) {
			factory.addLocation(location);
		}

		incubator = new Incubator();
		incubator.setFactory(factory);
		for (int i = 0; i < 50; i++) {
			incubator.addIndividual(factory.createRandomIndividual());
		}

		initMutations();
		startTime = System.currentTimeMillis();
		factory.setMutation(composedMutation);
		while (true) {
			incubator.crossPopulation();
			if(incubator.getGenerationSize() > 50){
				incubator.reducePopulationTo(10);
				for(int i = 0 ; i < incubator.getGenerationSize() ; i++){
					incubator.addIndividual(factory.createRandomIndividual());
				}
			}

			displayResult();
		}
	}

	private static void initMutations() {
		littleMutation = new IGeneMutation<Integer>() {

			@Override
			public double getRate() {
				return 1.0 / factory.getLocationsCounter();
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

		bigMutation = new IGeneMutation<Integer>() {

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

		composedMutation = new IGeneMutation<Integer>() {

			@Override
			public double getRate() {
				return littleMutation.getRate();
			}

			@Override
			public void mutates(Integer[] genes, int index) {
				if (Math.random() < 0.1) {
					littleMutation.mutates(genes, index);
				} else {
					bigMutation.mutates(genes, index);
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
			terminal += ((double) Math.round(best.getLength() * 100) / 100);
			System.out.println(terminal);
		}
	}

	private static boolean isBetterPath(Location[] path) {
		return Util.getPathLength(path) < Util.getPathLength(canvas.getPath());
	}

}
