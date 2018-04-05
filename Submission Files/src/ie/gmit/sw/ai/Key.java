// AI Project 2018 - Break a Playfair Cipher using Simulated Annealing
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// Moved all key-related operations into its own class

public class Key {
	private final String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
	
	// Modify the original random key using the given rules
	public String modifyKey(String key){
		
		int x = (int)(Math.random() * 100); // Generate random number 0-99 inc
		
		if(x >= 0 && x < 2) {
			// 2% of the time, reverse the whole key
			return new StringBuffer(key).reverse().toString(); // Simple built in reverse string stuff 
		} else if ( x >= 2 && x < 4) {
			// 2%, flip all cols
			return flipColumns(key);
		} else if ( x >= 4 && x < 6) {
			// 2%, flip all rows
			return flipRows(key);
		} else if ( x >= 6 && x < 8) {
			// 2%, swap cols
			return swapColumns(key, (int)(Math.random() * 4), (int)(Math.random() * 4));
		} else if ( x >= 8 && x < 10) {
			// 2%, swap random rows
			return swapRows(key, (int)(Math.random() * 4), (int)(Math.random() * 4));
		} else {
			// 90%, swap single letters
			return swapLetters(key, (int)(Math.random() * (key.length()-1)), (int)(Math.random() * (key.length()-1)));
		} // end if else for % of time do x
	} // end 

	// Fisher-Yates Shuffle, adapted from project spec
	public String shuffle(){
		int index;
		 Random random = ThreadLocalRandom.current();
		 char[] key = alphabet.toCharArray();
		 for (int i = key.length - 1; i > 0; i--) {
			 index = random.nextInt(i + 1);
			 if (index != i) {
				 key[index] ^= key[i];
				 key[i] ^= key[index];
				 key[index] ^= key[i];
			 }
		 }
		return new String(key);
    }

	// Functions for modifying key----------------------------------------------------------------------------------
	// Flip Columns
	private String flipColumns(String key) {
		char[] newKey = key.toCharArray(); // Make a char array with given key string
		int length = key.length() - (key.length()/5);  // Length = length of original key minus (length / 5) -> 25 - (25/5 = 5) = 20. Solves out of bounds issue.
		
		for(int i = 0; i < key.length() / 5; i++) { // For each char in key
			for(int j = 0; j < key.length() / 5; j++) { // for each char in row (length/5(rows) = 5)
				// Multiplying by 5 because basically swapping chars with char in same col diff row.
				char temp = key.charAt(i*5 + j); // Save char at pos i*5 + j, ex: 3*5 + 4 = 19, into temp var
				newKey[(i*5) + j] =  key.charAt(length + j); // Set the char at that pos in the new array to be char in original key at length+j
				newKey[length + j] =  temp; // Set the char at length + j to the temp character.
				// Tested using PlayfairImpl.printMatrix(key), does flip all columns.
			}
			length = length - 5;
		}
		return new String(newKey);
	}
	
	// Flip Rows
	private String flipRows(String key) {
		StringBuilder newKey = new StringBuilder(); // Using string builder to make it easy to add each new row to new key string
		
		for(int i = 0; i < 5; i++) { // For each row in the key, i.e. 5 rows
			String row = key.substring(i*5, i*5 + 5); // Get the row substring of the key based on for loop index - 2*5, 2*5 +5 = 10 to 15 
			String revRow = new StringBuffer(row).reverse().toString(); // Reverse the stringified row, need StringBuffer for reversing
			newKey.append(revRow); // Append the reversed row to the new key
		}
		return newKey.toString(); // Return stringified new key
	}

	// Swap Columns
	private String swapColumns(String key, int col1, int col2) {
		if (col1 == col2) { // Making sure same column can't be passed in to be swapped
			//System.out.println("Same");
			return swapColumns(key, (int)(Math.random() * 4), (int)(Math.random() * 4));
		} else {
			//System.out.println("Different");
			char[] newKey = key.toCharArray();
			for(int i = 0; i < key.length() / 5 ; i++) {
				int rowInd = i*5; // Row of index -> eg 2 = 10 (third row) - Accounting for each item in a column being 5 indices away from the items either side... if that makes sense...
				char temp =  newKey[(rowInd + col1)]; // Set temporary = newKey at row index + column number
				newKey[(rowInd + col1)] = newKey[(rowInd + col2)]; // Set char at that index to be the char on the same row but in the second column
				newKey[(rowInd + col2)] = temp; // Set that index to be the temp value
				// I.e. for each row, swap the chars in the given columns
			}
			return new String(newKey);
		}
	}

	// Swap Rows
	private String swapRows(String key, int row1, int row2) {
		// Works similar to swapping columns
		if (row1 == row2) { // Making sure same row can't be passed in to be swapped
			//System.out.println("Same");
			return swapRows(key, (int)(Math.random() * 4), (int)(Math.random() * 4));
		} else {
			//System.out.println("Different");
			row1 = row1 * 5; // Need to get index, not row num -> 4th row = row 3 = 3*5 = index 15.
			row2 = row2 * 5;
			char[] newKey = key.toCharArray();
			for(int i = 0; i < key.length() / 5 ; i++) {
				char temp =  newKey[(i + row1)]; // Set temporary = newKey at index i + row number
				newKey[(i + row1)] = newKey[(i + row2)]; // Set char at that index to be the char at that index + 2nd given row's number
				newKey[(i + row2)] = temp; // Set that index to be the temp value
				// I.e. for each row, swap the chars in the given columns
			}
			return new String(newKey);
		}
	}

	// Swap Random Letters
	private String swapLetters(String key, int l1, int l2) {
		char[] newKey = key.toCharArray(); // Make a char array with given key string
		
		if(l1 == l2) { // If given indices are the same
			l2 = (int)(Math.random() * (key.length()-1));
		}
		char temp = newKey[l1];
		newKey[l1] = newKey[l2];
		newKey[l2] = temp;

		return new String(newKey);
	}

}
