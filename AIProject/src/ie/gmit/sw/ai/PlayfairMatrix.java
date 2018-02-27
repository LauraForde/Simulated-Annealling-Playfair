package ie.gmit.sw.ai;

import java.util.LinkedHashSet;
import java.util.Set;

public class PlayfairMatrix {
	
	// Based on Step 2 of PlayFair in spec
	
	/* Rule 1	Digraph Letters in different rows and columns
		Create a “box” inside the matrix with each diagraph letter as a corner
		read off the letter at the opposite corner of the same row 
		-> cipher(B, P)={matrix[row(B)][col(P)], matrix[row(P)][col(B)]}.
		Reverse to decrypt.
	*/
	
	/* Rule 2	Digraph Letters in Same Row 
	 	Replace any letters that appear on the same row with the letters to their immediate right
	 	Wrap around the matrix if necessary. 
	 	Decrypt by replacing cipher-text letters the with letters on their immediate left.
	*/
	
	/* Rule 3	Digraph Letters in Same Column
	 	Replace any letters that appear on the same column with the letters immediately below
		Wrap back around the top of the column if necessary
	 	Decrypt by replacing cipher text letters the with letters immediately above. 
	*/
	
	public static char[][] generateMatrix(String keystr) {
		char[][] matrix = new char[5][5];
		char[] key = keystr.toCharArray();
		int k = 0; // index in key
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				matrix[i][j] = key[k];
				k++;
			}
		}
		
		// TESTING ---------- loops through matrix array to output contents
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		
		return matrix;
    }

}
