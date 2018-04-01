package ie.gmit.sw.ai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimulatedAnnealling {
	
	/* Steps
	 * 	1. Generate a random 25 letter key called parent
	 * 	2. Decrypt the cipher text with the key
	 * 	3. Score the fitness of the key as logProbability(parent)
	 * 	4. For temp = 10 to 0, step -1
	 * 	5.   For transitions = 50,000 to 0, step -1
	 * 	6.		Set child = shuffleKey(parent) // Make small change to the key
	 * 	7.		Score the fitness of the key as logProbability(child)
	 * 	8.		Set delta = logProbability(child) - logProbability(parent)
	 * 	9.		If delta > 0 // i.e. new key better
	 * 10.		  Set parent = child
	 * 11.		Else if delta < 0
	 * 12.		  Set parent = [ child with prob e^(-delta / temp) ]
	 * 		 // end for transitions
	 * 	   // end for temp 
	 */
	
	// Decrypt using a given key
	public String decrypt(String[] digraphs) throws IOException {
		String key = shuffle("ABCDEFGHIKLMNOPQRSTUVWXYZ");
		PlayfairImpl pf = new PlayfairImpl();
		String decrypted = pf.decrypt(key, digraphs);
		
		double parentFitness = scoreFitness(decrypted, key);
		
		for (int temp = 10; temp >= 0; temp--) {
			for (int transitions = 50000; transitions >= 0; transitions--) {
				String child = shuffle(key);
				String[] childDigraphs = pf.makeDigraphs(decrypted);
				String decryptChild = pf.decrypt(key, childDigraphs);
				System.out.println(decryptChild);
				double childFitness = scoreFitness(decryptChild, key);
				double delta = childFitness - parentFitness;
				if (delta > 0) {
					key = child;
					System.out.println("Child better, new key: " + key);
				} else if (delta < 0){
					System.out.println("Child not better");
				}
				
			}
		}
		return decrypted;
	}
	
	// Score fitness
	public double scoreFitness(String decrypted, String key) throws IOException {
		//System.out.print("4grams sample: ");
		Map quadgrams = FilePreparer.getQuad();
		double score = 0;
		
		int limit = decrypted.length();
		if(limit > 23) // Not going to test entire text file, will work on 400 quadgrams
			limit = 23; // 403 = index of last letter in 400th quadgram (399-403)

		for (int index = 0; index <= limit - 4; index++) {
			//System.out.print((index + 1) + ". " + decrypted.substring(index, index + 4) + "[" + index + "-" + (index+4) + "]" + "\n");
			Double occurences = (Double) quadgrams.get(decrypted.substring(index, index + 4));
			if (occurences != null) {
				//System.out.println("Oc: " + occurences + "  / L: " + Math.log10(occurences));
				score = score + Math.log10(occurences/389373);
			}
		}
		System.out.println("Score: " + score);		
		
		return score;

	}
	
	public String modifyKey(String key){
//		The method shuffleKey() on line 6 should make the following changes to the key with the frequency given 
//		(you can approximate this using Math.random() * 100):
//		Swap single letters (90%)
//		Swap random rows (2%)
//		Swap columns (2%)
//		Flip all rows (2%)
//		Flip all columns (2%)
//		Reverse the whole key (2%) 
		
		//Random r = new Random(); // Using random to generate int
		//int x = r.nextInt(99); // with random, 2% of time result will be 1 or 2, 2% of time result will be 3 or 4, so on
		
		int x = (int)(Math.random() * 100);
		
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
			
		} else if ( x >= 8 && x < 10) {
			// 2%, swap random rows
			
		} else {
			// 90%, swap single letters
			
		} // end if else for % of time do x
		
	    return key;
	}

	// Shuffle function adapted from https://stackoverflow.com/a/3316696
	public String shuffle(String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        
        return output.toString();
    }

	// Functions for modifying key, separating into their own functions because tidied than lumping it all into the for loop
	
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
	
}
