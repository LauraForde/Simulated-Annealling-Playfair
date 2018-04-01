package ie.gmit.sw.ai;

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
	
	public static void printMatrix(String keystr) {
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
	
	public String pfDecrypt(String matrix, String[] digraphs) {
		// Running into trouble... better to just work with string matrix???? Array too complex..?
		// Can surely do calculations on "THEQUICKBROWNFXMPDVLAZYGS" to get "location"... maybe. Will try that out

		StringBuilder plainText = new StringBuilder(); // Using Stringbuilder again to build the decrypted text
		//System.out.println("\nKey: " + matrix);
		
        for (String digraph : digraphs) {
        	 // indexOf returns the index of a given character, if null returns null
        	// Pass the first char to indexOf to find where it is within the matrix
        	// e.g. matrix.indexOf('f') will return 13
        	// Divide to get row, modulo to get column. Dividing gives how many times it goes in evenly (row), modulo gives the remainder (col)
        	// Take into account rows and columns start from 0, not 1
        	// F is at index 13, which is row 2, col 3 (or row 3 col 4) - 13 / 5 = 2 remainder 3
        
        	// First char in digraph
            int row0 = matrix.indexOf(digraph.charAt(0)) / 5;
            int col0 = matrix.indexOf(digraph.charAt(0)) % 5;
            
            // Second char in digraph
            int row1 = matrix.indexOf(digraph.charAt(1)) / 5;
            int col1 = matrix.indexOf(digraph.charAt(1)) % 5;
            
            // New Indexes
            int ind0;
            int ind1;
            
            // New chars
            char new0 = '-';
            char new1 = '-';

            if (row0 == row1) { // Letters in same row
            	// Replace letter with the letter to immediate left (index - 1?)
            	
            	// New Indexes (one to the left)
            	ind0 = matrix.indexOf(digraph.charAt(0)) - 1;
            	ind1 = matrix.indexOf(digraph.charAt(1)) - 1;

            	//System.out.println("inds:  " + ind0 + "  " + ind1);
            	
            	if (ind0 < 0) { // If the new index (moved left) of the char is less than 0 (i.e. out of bounds)
            		// Get the char at the index equal to the matrix size minus the index of the given digraph
            		// So T (index 0) => (24 - (0)) = char at 24, which is S, which is right
            		new0 = matrix.charAt(24 - (matrix.indexOf(digraph.charAt(0))));
            		// Other char works fine, can't have two of the same and first char in the matrix is the only problem
                	new1 = matrix.charAt(ind1);
            	} else if (ind1 < 0){
            		// Same as above except for the second char of the digraph
            		new0 = matrix.charAt(ind0);
            		new1 = matrix.charAt(24 - (matrix.indexOf(digraph.charAt(1))));
            	} else if (ind0 >= 0 && ind1 >= 0 ){
            		// Both fine
            		new0 = matrix.charAt(ind0);
                	new1 = matrix.charAt(ind1);
            	}
            	
            	//System.out.println(digraph + "  =>  " + new0 + "  " + new1);
            } // End same row rule
            
            else if (col0 == col1) { // Letters in the same column 
            	// Replace letter with the letter immediately above (index - 5?)
            	
            	// New Indexes (same column, row above)
            	ind0 = matrix.indexOf(digraph.charAt(0)) - 5;
            	ind1 = matrix.indexOf(digraph.charAt(1)) - 5;
            	
            	//System.out.println("\ninds:  " + ind0 + "  " + ind1);
            	
            	if (ind0 < 0) {
            		//System.out.println(matrix.indexOf(digraph.charAt(1)) + "  -> " + (matrix.indexOf(ind1)));
            		// For some reason 5 minus 5 is coming out as -1... just gonna keep +1 there until it causes a problem
            		new0 = matrix.charAt(25 + (ind0)); // E.g T => 25 + (- 5) = 20, which is right index
            		new1 = matrix.charAt(matrix.indexOf(ind1) + 1); // Why +1 needed????? ind1 shouldn't be -1??? 
            		
            	} else if (ind1 < 0){
            		// Same as above except other way around
            		new0 = matrix.charAt(matrix.indexOf(ind0) + 1);
            		new1 = matrix.charAt(25 + (ind1));
            		
            	} else if (ind0 >= 0 && ind1 >= 0){
            		// Both fine
            		new0 = matrix.charAt(ind0);
                	new1 = matrix.charAt(ind1);
            	}
          	
            	//System.out.println(digraph + "  =>  " + new0 + "  " + new1);           	
            } // End same column rule
            
            else { // Letters in different rows/columns
            	// Box/alternate corners...
            	
            	/*LOGIC
            	AR, FI, RD should return SI,OB,KL
            	Will have to use calculations here to get "corners"
            	AR - 20 (A) 9 (R) should become 	24 (S) 5 (I)
            	FI - 13 (F) 5 (I) 	  =>		10 (O) 8 (B)
            	RD - 9 (R) 17 (D)	  =>		19 (L) 7 (K)
            	            	
            	cipher(B, P) = { matrix[row(B)][col(P)] , matrix[row(P)][col(B)] }
            	Reversed =>		cipher 0,1	=> row1 col0, row0 col1 ? yes? 
                Rows got by dividing, columns by moduloing, need to reverse that..?
                A -> S (index 24), A is 20 (row * 5) r's column is 4, add that..?
                R -> I (5), R -> 1 * 5 = 5 + 0 = 5 (which is I)
                F -> O (10) -> row * 5 = 10 + I's column (0) = 10
                I -> B (8), I-> 1 * 5 = 5 + f's column (8) = 8
                That seems to work
                Algorithm = multiply char's row by 5 and add the other char's column*/
                
                new0 = matrix.charAt(row0 * 5 + col1);
                new1 = matrix.charAt(row1 * 5 + col0);
            	//System.out.println(digraph + "  =>  " + new0 + "  " + new1);           	
                
            }


            plainText.append(new0);
            plainText.append(new1);
        } // End for each digraph in array
        //System.out.println("PlainText: " + plainText);

        return plainText.toString();
	}
	
}
