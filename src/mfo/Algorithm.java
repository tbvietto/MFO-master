package mfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Algorithm {
	public static Random rand = new Random(100); // random seed
	private static ArrayList<Individual> childs;

	public static void main(String[] args) {
		System.out.println("Before MFO");
		Population pop = new Population();
		pop.initPopulation();
		for (int i = 0; i < 500; i++) {
			pop = run(pop);
		}
		System.out.println("After MFO");
		pop.printPop(pop.population);
	}

	public static Population run(Population pop) {
		Population temp_pop = new Population(); // population with 200
												// individuals
		Population new_pop = new Population(); // population to store 100
												// fittest individuals
		temp_pop = pop;
		for (int i = 0; i < Population.defaultPopLength / 2; i++) {
			// ramdomly choose parents to crossover or mutation
			int parentIndex1, parentIndex2;
			parentIndex1 = rand.nextInt(Population.defaultPopLength);
			do {
				parentIndex2 = rand.nextInt(Population.defaultPopLength);
			} while (parentIndex1 == parentIndex2);
			childs = new ArrayList<Individual>();
			// crossover/mutation
			childs = Population.crossOver(pop.population.get(parentIndex1), pop.population.get(parentIndex2));
			// add to temp_pop
			temp_pop.population.add(childs.get(0));
			temp_pop.population.add(childs.get(1));
		}
		// calculate scalarFitness and skillFactor to temp_pop
		temp_pop.calculateScalarFitness(temp_pop.population, temp_pop.population.size());
		// sort temp_pop by scalarFitness
		Collections.sort(temp_pop.population, Individual.compareByScalarFitness);
		// add 100 fittest individuals to new_pop;
		for (int i = 0; i < Population.defaultPopLength; i++) {
			new_pop.population.add(temp_pop.population.get(i));
		}
		return new_pop;
	}
}
