package mfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
	public static int defaultPopLength = 100; // default length of population
	// Population is an array list of individuals
	public ArrayList<Individual> population = new ArrayList<Individual>(defaultPopLength);
	public static Random rand = new Random(5);

	public Population() {
	};

	public void initPopulation() {
		for (int i = 0; i < defaultPopLength; i++) {
			Individual indiv = new Individual();
			indiv.initGene();
			population.add(indiv);
		}
		calculateScalarFitness(population, defaultPopLength);
		printPop(population);
	}

	public static ArrayList<Individual> crossOver(Individual indiv1, Individual indiv2) {
		ArrayList<Individual> child = new ArrayList<Individual>();
		if (indiv1.getSkillFactor() == indiv2.getSkillFactor()) {
			child = pMX(indiv1, indiv2);
			child.get(0).setFitness();
			child.get(1).setFitness();
			return child;
		} else {
			child.add(mutation(indiv1));
			child.add(mutation(indiv2));
			return child;
		}
	}

	public static Individual mutation(Individual indiv) {
		Individual ind = new Individual();
		int left, right; // mutation point
		left = rand.nextInt(Individual.defaultGeneLength);
		do {
			right = rand.nextInt(Individual.defaultGeneLength);
		} while (right == left);
		for (int i = 0; i < Individual.defaultGeneLength; i++) {
			if (i == left) {
				ind.addGene(indiv.getGene().get(right), left);
			} else if (i == right) {
				ind.addGene(indiv.getGene().get(left), right);
			} else {
				ind.addGene(indiv.getGene().get(i), i);
			}
		}
		ind.setFitness();
		return ind;
	}

	public void calculateScalarFitness(ArrayList<Individual> pop, int popLength) {
		double[] scalarFitness = new double[popLength];
		ArrayList<Individual> temp_pop = new ArrayList<Individual>(popLength);
		// printPop(pop);
		temp_pop = pop;
		// System.out.println();
		for (int i = 0; i < popLength; i++) {
			scalarFitness[i] = 0;
			pop.get(i).setScalarFitness(0);
		}
		for (int i = 0; i < Individual.numberOfFiles; i++) {
			Collections.sort(temp_pop);
			for (int j = 0; j < popLength; j++) {
				if (1.0 / (j + 1) > pop.get(j).getScalarFitness()) {
					pop.get(j).setScalarFitness(1.0 / (j + 1));
					pop.get(j).setSkillFactor(i + 1);
				}
				temp_pop.get(j).swap();
			}
		}
	}

	public void printPop(ArrayList<Individual> pop) {
		for (int i = 0; i < defaultPopLength; i++) {
			pop.get(i).printIndiv();
		}
		System.out.println();
	}

	public static ArrayList<Individual> pMX(Individual indiv1, Individual indiv2) {
		ArrayList<Individual> child = new ArrayList<Individual>();
		Individual ind1 = new Individual();
		Individual ind2 = new Individual();
		int left, right; // cross over point
		left = rand.nextInt(Individual.defaultGeneLength);
		do {
			right = rand.nextInt(Individual.defaultGeneLength);
		} while (right == left); // left, right must be different number
		if (left > right) {
			int temp = right;
			right = left;
			left = temp;
		} // left always must be smaller than right
			// generate child1
			// System.out.println(left + " " + right);
		for (int i = 0; i < Individual.defaultGeneLength; i++) {
			int temp = -1;
			// if i out of two cross over point, find the mapped of gene(i)
			if (i < left || i > right) {
				temp = indiv2.getGene().get(i);
				for (int k = left; k < right + 1; k++) {
					for (int j = left; j < right + 1; j++) {
						if (indiv1.getGene().get(j) == temp) {
							temp = indiv2.getGene().get(j);
						}
					}
				}
				// else add gene i to child
				ind1.addGene(temp, i);
				continue;
			}
			ind1.addGene(indiv1.getGene().get(i), i);
		}
		// generate child2
		child.add(ind1);
		for (int i = 0; i < Individual.defaultGeneLength; i++) {
			int temp = -1;
			if (i < left || i > right) {
				temp = indiv1.getGene().get(i);
				for (int k = left; k < right + 1; k++) {
					for (int j = left; j < right + 1; j++) {
						if (indiv2.getGene().get(j) == temp) {
							temp = indiv1.getGene().get(j);
						}
					}
				}
				ind2.addGene(temp, i);
				continue;
			}
			ind2.addGene(indiv2.getGene().get(i), i);
		}
		child.add(ind2);
		return child;
	}

}
