package mfo;

public class TSP {
	// each object of this class will store data of one .tsp file
	
	private int length; // number of cities

	private double distances[][]; // matrix n x n store distances of all cities

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public double[][] getDistances() {
		return distances;
	}

	public void setDistances(double[][] distances) {
		this.distances = distances;
	}

	public void printDistances() {
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances[0].length; j++) {
				System.out.print(distances[i][j]);
			}
			System.out.println();
		}
	}
}
