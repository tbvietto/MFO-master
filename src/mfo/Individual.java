package mfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Individual implements Comparable<Individual> {

	// each object of this class will store one solution
	// read from

	private ArrayList<Integer> gene = new ArrayList<Integer>();
	private int skillFactor;
	private double scalarFitness;

	// arraylist to store fitness of individual with each problem
	public ArrayList<Double> fitness = new ArrayList<Double>();
	public static int numberOfFiles; // number of problems

	// arraylist to store tsp problem from file require.txt
	public static ArrayList<TSP> tsp = new ArrayList<TSP>();

	// max of Dimensions
	public static int defaultGeneLength = readFileRequire("require.txt");
	public static Random rand = new Random(1); // random seed

	public int getSkillFactor() {
		return skillFactor;
	}

	public void setSkillFactor(int skillFactor) {
		this.skillFactor = skillFactor;
	}

	public double getScalarFitness() {
		return scalarFitness;
	}

	public void setScalarFitness(double scalarFitness) {
		this.scalarFitness = scalarFitness;
	}

	// read file require.txt, return number of tsp problems
	public static int readFileRequire(String fileName) {
		int max = 0;
		String sCurrentLine = null;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			sCurrentLine = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] str = sCurrentLine.split(" "); // split by space
		numberOfFiles = Integer.parseInt(str[0]);

		for (int i = 1; i <= numberOfFiles; i++) {
			int temp = readFileDistances(str[i]); // read data of each tsp
													// problem
			double[][] distances = new double[temp][temp];
			distances = saveDistances(temp, str[i], distances);
			TSP t = new TSP();
			t.setDistances(distances);
			t.setLength(temp);
			tsp.add(t);
			if (max < temp)
				max = temp;
		}
		return max;
	}

	// read from .tsp file, return number of cities of each problem
	public static int readFileDistances(String fileName) {
		int num = 0; // number of cities
		BufferedReader br = null;
		try {
			String sCurrentLine = null;
			br = new BufferedReader(new FileReader(fileName));
			// read lines 1..4
			for (int j = 0; j < 4; j++) {
				sCurrentLine = br.readLine();
			}
			String[] str = sCurrentLine.split(": "); // split from ": " string
			num = Integer.parseInt(str[1]); // line 4 is containing number
											// of cities.
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return num; // return number of cities
	}

	// save distances of individual
	public static double[][] saveDistances(int num, String fileName, double[][] distances) {
		BufferedReader br = null;
		try {
			String sCurrentLine = null;
			br = new BufferedReader(new FileReader(fileName));
			// read lines 1..6
			for (int j = 0; j < 6; j++) {
				sCurrentLine = br.readLine();
			}
			City[] cities = new City[num];
			// read the coordinates of cities
			for (int j = 0; j < num; j++) {
				sCurrentLine = br.readLine();
				String[] str = sCurrentLine.split(" "); // split by space
				cities[j] = new City();
				// set coordinates
				cities[j].setX(Double.parseDouble(str[1]));
				cities[j].setY(Double.parseDouble(str[2]));
				// calculate distances
				for (int i = 0; i <= j; i++) {
					if (i == j) {
						distances[j][i] = 0;
					} else {
						distances[j][i] = distances[i][j] = Math.sqrt(Math.pow((cities[j].getX() - cities[i].getX()), 2)
								+ Math.pow((cities[j].getY() - cities[i].getY()), 2));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return distances;
	}

	// initialize
	public void initGene() {
		for (int i = 0; i < defaultGeneLength; i++) {
			getGene().add(i + 1);
		}
		Collections.shuffle(getGene(), rand);
		setFitness(); // set fitness with each problem
	}

	// decode and get fitness with one problem
	public double getFitness(TSP tsp, int length) {
		double fitness = 0;
		ArrayList<Integer> decodeResult = new ArrayList<Integer>();
		decodeResult = decode(tsp);
		for (int i = 0; i < length - 1; i++) {
			fitness = fitness + tsp.getDistances()[decodeResult.get(i) - 1][decodeResult.get(i + 1) - 1];
		}
		fitness += tsp.getDistances()[decodeResult.get(length - 1) - 1][decodeResult.get(0) - 1];
		return fitness;
	}

	public void setFitness() {
		for (int i = 0; i < numberOfFiles; i++) {
			double d = 0;
			d = getFitness(tsp.get(i), tsp.get(i).getLength());
			fitness.add(d);
		}
	}

	public ArrayList<Integer> decode(TSP tsp) {
		ArrayList<Integer> decodeResult = new ArrayList<Integer>();
		int num = tsp.getLength();
		for (int i = 0; i < defaultGeneLength; i++) {
			if (gene.get(i) <= num) {
				decodeResult.add(gene.get(i));
			}
		}
		return decodeResult;
	}

	// compare to sort in Population by fitness.get(0)
	@Override
	public int compareTo(Individual o) {
		// TODO Auto-generated method stub
		int temp = fitness.get(0).compareTo(o.fitness.get(0));
		// swap();
		return temp;
	}

	// compare to sort in Population by scalarFitness
	public static Comparator<Individual> compareByScalarFitness = new Comparator<Individual>() {
		public int compare(Individual one, Individual other) {
			return Double.compare(other.scalarFitness, one.scalarFitness);
		}
	};

	//swap fitness list to sort
	public void swap() {
		double d = fitness.get(0);
		fitness.remove(0);
		fitness.add(d);
	}

	public void addGene(int value, int index) {
		gene.add(index, value);
	}

	//mutation by swap to point
	

	public ArrayList<Integer> getGene() {
		return gene;
	}

	public void printIndiv() {
		for (int i = 0; i < defaultGeneLength; i++) {
			System.out.print(gene.get(i) + " ");
		}
		for (int i = 0; i < numberOfFiles; i++) {
			System.out.print(fitness.get(i) + " ");
		}
		System.out.print(skillFactor + " " + scalarFitness);
		System.out.println();
	}

}
