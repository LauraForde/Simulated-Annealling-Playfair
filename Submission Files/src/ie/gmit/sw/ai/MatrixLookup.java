// AI Project 2018 - Break a Playfair Cipher using Simulated Annealing
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

public class MatrixLookup {
	
	// Key was initially a string, very slow when performing operations. 
	// Much quicker to add to char array and get the x/y (row/col) position of the given char. 
	// String version took about a second per transition, new matrix array version takes approx. 10 seconds per 30k transitions.
	// 1 transition per second vs 3,000 transitions per second.

	private int rowNum; 
	private int colNum;

	
	private MatrixLookup(int rowNum, int colNum) {
		this.rowNum = rowNum;
		this.colNum = colNum;
	}

	public static MatrixLookup getPosition(char letter, char[][] cipherTable) {
		
		for (int row = 0; row < 5; row++) { // For each of the five "rows"
			for (int col = 0; col < 5; col++) { // For each item in the row (i.e. each column)
				if (letter == cipherTable[row][col]) { // If the given character is at this position
					return new MatrixLookup(row, col); // Return this position to the constructor, -> sets row/col -> access using gets
				}
			}
		}
		return null; // Default return null (i.e. not found)
	}
	
	// Gets for row / col
	public int getRowNum() {
		return this.rowNum;
	}
	
	public int getColNum() {
		return this.colNum;
	}
	
}
