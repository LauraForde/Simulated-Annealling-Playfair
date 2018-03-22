package ie.gmit.sw.ai;

import java.io.IOException;
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
	
	public String doLooping(double score, String parent) {
		/* 	4. For temp = 10 to 0, step -1
	 * 	5.   For transitions = 50,000 to 0, step -1
	 * 	6.		Set child = shuffleKey(parent) // Make small change to the key
	 * 	7.		Score the fitness of the key as logProbability(child)
	 * 	8.		Set delta = logProbability(child) - logProbability(parent)
	 * 	9.		If delta > 0 // i.e. new key better
	 * 10.		  Set parent = child
	 * 11.		Else if delta < 0
	 * 12.		  Set parent = [ child with prob e^(-delta / temp) ]
	 * 		 // end for transitions
	 * 	   // end for temp */

		
		
		return null;

		
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
