package ie.gmit.sw.ai;

import java.util.LinkedList;
import java.util.List;

public class PlayfairImpl{

	//private char[][] keyMatrix;
	//private List<MatrixLookup> positions; // Holds list of all row,column positions of digraphs (row 2, col 4 etc etc)
	
	//public PlayfairImpl() {
		//super();
		//this.positions = new LinkedList<MatrixLookup>(); // Might need to use Linked, array faster for now
		//this.keyMatrix = new char[5][5];
	//}
	
	// Step 1 - Prime the plain text 
	public String primePlainTxt(String string) {
		// Pass the given string (uppercase and with no spaces ) to a new stringbuilder var
		// Using StringBuilder because standard String does not allow appending / is immutable
        // Why use SB? - https://stackoverflow.com/q/5234147
        StringBuilder str = new StringBuilder(string.toUpperCase().replaceAll("[^A-Za-z0-9 ]", "")); // 

        // "Parse any double letters and replace the second occurrence with X" - e.g. LETTERKENNY becomes LETXERKENXY
        for (int i = 0; i < string.length() - 1; i += 2) {
            if (str.charAt(i) == str.charAt(i + 1))
            	str.insert(i + 1, "x");
        }
        
        // "If the plaintext has an odd number of characters, append the letter X to make it even"
        if (str.length() % 2 == 1) {
        	str.append("x");
        }
		return str.toString();
	}

	// Step 2 - Break the plain text into digraphs
	// Again, working with strings etc slows process down... easier to just work with indexes in SA/decrypt methods. Leaving here just in case.
	// "ARTIFICIAL" becomes [AR, TI, FI, CI, AL]
	public String[] makeDigraphs(String string) {
    
		// Create a new string array of length [half of given string] - String of length 100 becomes array of 50 (2 chars per digraph)
        String digraphs[] = new String[string.length() / 2];
        int index = 0;

        for (int i = 0; i < string.length(); i = i + 2) { // Using String length and increments of 2 (Instead of digraphs[] length and increments of 1)
        	digraphs[index] = string.substring(i, i + 2); // Assign substring (current char -> +2) to digraph[] element at current index  
        	index++; // Move to next index
        }
        
        return digraphs;
	}


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
	
	// Print matrix, nothing important just prints the string in 5 x 5 format.
	public void printMatrix(String keystr) {
		int k = 0, k2 = 0; // index of string
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(keystr.substring(k, k + 1) + "  ");
				k++;
			}
			System.out.print("\t\t");
			for(int l = 0; l < 5; l++) { // Printing out indexes
				if(k2 < 10)
					System.out.print(" " + k2 + "  ");
				else
					System.out.print(k2 + "  ");
				k2++;
			}
			System.out.println();
		}
		
		System.out.println();
    }
	
	// Gets key and encrypted text from SA, creates a matrix and passes matrix and text to actual decrypting method.
	public String decryptPF(String key, String encryptedTxt) throws Exception {
		// Make a matrix for the key. Was using strings initially but might be the reason for slowness?
		char[][] keyMatrix = new char[5][5];
		int index = 0;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				keyMatrix[i][j] = key.charAt(index);
				index++;
			}
		}
		StringBuilder decryptedText = new StringBuilder();
		return breakPlayfair(keyMatrix, encryptedTxt, 0, decryptedText);
	}
	
	private String breakPlayfair(char[][] keyMatrix, String encryptedText, int index, StringBuilder decryptedText) {
		// Actual breaking of the playfair cipher
		
		if(index <  encryptedText.length() / 2) {
			char first = encryptedText.charAt(2 * index); // First char in digraph
			char second = encryptedText.charAt(2 * index + 1);  // Second char in digraph
			// First char in digraph
			int r1 = (int) MatrixLookup.getPosition(first, keyMatrix).getRowNum(); // Will return int of row number
			int c1 = (int) MatrixLookup.getPosition(first, keyMatrix).getColNum(); // Return int of col number
			// Second char in digraph
			int r2 = (int) MatrixLookup.getPosition(second, keyMatrix).getRowNum();
			int c2 = (int) MatrixLookup.getPosition(second, keyMatrix).getColNum();

			// Have logic for all this in github history...
			if (r1 == r2) {
				c1 = (c1 + 4) % 5; 
				c2 = (c2 + 4) % 5;
			} else if (c1 == c2) {
				r1 = (r1 + 4) % 5;
				r2 = (r2 + 4) % 5;
			} else {
		        int temp = c1;
		        c1 = c2;
		        c2 = temp;
		    }
			
			decryptedText.append(keyMatrix[r1][c1] +""+ keyMatrix[r2][c2]);
			return breakPlayfair(keyMatrix, encryptedText, 1 + index, decryptedText);
		} else {
			return decryptedText.toString();
		}
	}
	
	
}
