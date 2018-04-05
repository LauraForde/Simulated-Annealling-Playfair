package ie.gmit.sw.ai;

public class PlayfairImpl{

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

	// Commenting out Step 2 because no need to actually save digraphs when the text is just being parsed in twos in the decryptPF method anyway.
	// Leaving it here to show work and thought process.
	
	// Step 2 - Break the plain text into digraphs
	// Again, working with strings etc slows process down... easier to just work with indexes in SA/decrypt methods. Leaving here just in case.
	// "ARTIFICIAL" becomes [AR, TI, FI, CI, AL]
	
/*	public String[] makeDigraphs(String string) {
    
		// Create a new string array of length [half of given string] - String of length 100 becomes array of 50 (2 chars per digraph)
        String digraphs[] = new String[string.length() / 2];
        int index = 0;

        for (int i = 0; i < string.length(); i = i + 2) { // Using String length and increments of 2 (Instead of digraphs[] length and increments of 1)
        	digraphs[index] = string.substring(i, i + 2); // Assign substring (current char -> +2) to digraph[] element at current index  
        	index++; // Move to next index
        }
        
        return digraphs;
	}*/

	// Gets key and encrypted text from SA, creates a matrix and loops through text 2 chars at a time to decrypt.
	public String decryptPF(String key, String encryptedTxt) throws Exception {
		// Make a matrix for the key. Was using strings initially but might be the reason for slowness?
		char[][] keyMatrix = new char[5][5];
		int index = 0;

		for (int i = 0; i < 5; i++) { // For each "row" in the string
			for (int j = 0; j < 5; j++) { // for each "column" in the string
				keyMatrix[i][j] = key.charAt(index); // Set the keyMatrix at the current pos equal to the current index.
				// e.g. - char at index 13 should go to 2, 4 
				index++; // Increment index to get next char in key
			}
		}
		StringBuilder humanReadable = new StringBuilder(); 
		for (index = 0; index < (encryptedTxt.length() / 2) -2; index++) { // Start at 0, loop (half the number of characters in the text) times.
			// Note: I had the key implemented as a String at first but using a char array of [5][5] not only makes the process a lot faster,
			// it makes decryption using keys a lot easier. See here - https://github.com/rebeccabernie/Simulated-Annealling-Playfair/blob/263c8b6fe62ecd091c6808bb4c5087a08561fbdc/AIProject/src/ie/gmit/sw/ai/PlayfairImpl.java
			// for a historic version of this file, complete with explanations of that version.
			
			// Index works as such: index 0 deals with chars 0 and 1, index 1 deals with chars 1 and 2, so on and so forth.
			char first = encryptedTxt.charAt(2 * index); // First char in digraph
			char second = encryptedTxt.charAt(2 * (index + 1));  // Second char in digraph
			
			// First char in digraph
			int row1 = (int) MatrixLookup.getPosition(first, keyMatrix).getRowNum(); // Returns row number from MatrixLookup
			int col1 = (int) MatrixLookup.getPosition(first, keyMatrix).getColNum(); // Return column number
			// Second char in digraph
			int row2 = (int) MatrixLookup.getPosition(second, keyMatrix).getRowNum();
			int col2 = (int) MatrixLookup.getPosition(second, keyMatrix).getColNum();

			// Have logic for all this in github history...
			if (row1 == row2) { // Letters in the same row - replace letter with the letter to immediate LEFT. 
				col1 = (col1 + 4) % 5; // Add 4, return remainder that divided by 5. eg. col2 should move to 1 - 2+4=6%5=1. 3+4=7%5=2.
				col2 = (col2 + 4) % 5; // Do the same for second char's column.
			} else if (col1 == col2) { // Letters in the same col - replace with letter ABOVE. Works basically the same as rows.
				row1 = (row1 + 4) % 5; // Add 4 to row num... row2 +4=6%5= now in row 1.
				row2 = (row2 + 4) % 5; // Same for the second char.
			} else { // Letters are in different rows/columns
				// Basically swap the column position of the first and second characters.
				// e.g. top left and bottom right becomes BOTTOM left and TOP right. Easier to explain with diagrams...
		        int temp = col1;
		        col1 = col2;
		        col2 = temp;
		    }
			humanReadable.append(keyMatrix[row1][col1] +""+ keyMatrix[row2][col2]); // Append the two chars to the humanReadable string builder
		}	
		return humanReadable.toString(); // Return the stringified String Builder
	}
	
	// Print the matrix, nothing important just prints a 25 character string nicely in 5 x 5 format.
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
	
}
