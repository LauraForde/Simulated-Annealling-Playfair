package ie.gmit.sw.ai;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class PlayfairImpl {
	
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
	
	public static StringBuilder decrypt(char[][] matrix, String[] digraphs) {
		StringBuilder plainText = new StringBuilder(); // Decrypted text
		
		// Decrypt using rules above with given matrix
		// Matrix will differ each time when Simulated Annealling implemented, working with basic quick brown fox matrix for now
		
		for (String digraph : digraphs) { // For each digraph in the digraph array
            char[] pair = digraph.toCharArray();
            
            // using hashmap to store key value pair - https://beginnersbook.com/2013/12/hashmap-in-java-with-example/
            HashMap<Character, Integer[]> map = new HashMap<Character, Integer[]>();
            
            for (char p : pair) {
            	// i j starting at 1, matrix normally read as 1, 5 etc not 0, 4?
            	for (int i = 0; i < 5; i++) {
	                for (int j = 0; j < 5; j++) {
	                    if (matrix[i][j] == p) {
	                    	Integer[] location = {i + 1, j + 1};
	                    	//System.out.print(p + ": " +  Arrays.toString(location) + "\t ");
	                    	map.put(p, location); // Add the char and its location in the matrix to the map
	                    }
	                    	
	                }
	            }
			}
            
            // Adapted from https://stackoverflow.com/a/3605320, just to make sure map is right
            for (Entry<Character, Integer[]> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + Arrays.toString(entry.getValue()));
            }
            
            
            //if(pair[0]) {

            // } else if in the same column {

            // } else different rows {

            //}
            // plainText.append(new pair);
        }
		
		return plainText; 
		
	}
	
}
