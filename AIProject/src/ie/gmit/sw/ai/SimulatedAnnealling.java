package ie.gmit.sw.ai;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
	
	// SimulatedAnnealling
	public void simulatedAnnealingBreak() {
		// Generate a random 25 letter key called parent
		char key[] = "THEQUICKBROWNFXMPDVLAZYGS".toCharArray(); // to char array - https://stackoverflow.com/a/11711248
		char parent[] = shuffle(key);
		
		// Decrypt using key
	}
	
	// Fisher-Yates Shuffle, O(n) time complexity
	public char[] shuffle(char[] key) {
		int index;
		Random random = ThreadLocalRandom.current();
		
		for (int i = key.length - 1; i > 0; i--) {
			// Get a random index of the array
			index = random.nextInt(i + 1);
			
			if(index != i) {
				// ^ is a bitwise operator, related to binary
				// good explanation - https://www.programiz.com/java-programming/bitwise-operators#xor
				key[index] ^= key[i];
				key[i] ^= key[index];
				key[index] ^= key[i];
			}
		}
		return key;
	}

}
