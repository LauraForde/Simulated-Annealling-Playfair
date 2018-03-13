package ie.gmit.sw.ai;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public String decrypt(String[] digraphs) {
		String key = shuffle("ABCDEFGHIKLMNOPQRSTUVWXYZ");
		PlayfairImpl pf = new PlayfairImpl();
		String decrypted = pf.decrypt(key, digraphs);

		// Decrypt using key
		return decrypted;
	}
	
	// Score fitness
	public double scoreFitness(String decrypted) throws IOException {
		//System.out.print("4grams sample: ");
		Map quadgrams = FilePreparer.getQuad();
		
		int limit = decrypted.length();
		if(limit > 403) // Not going to test entire text file, will work on 400 quadgrams
			limit = 403; // 403 = index of last letter in 400th quadgram (399-403)

		for (int index = 0; index <= limit - 4; index++) {
			System.out.print((index + 1) + ". " + decrypted.substring(index, index + 4) + "[" + index + "-" + (index+4) + "]" + "\n");
		}
		
		double score = 0;
		return score;
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

}
