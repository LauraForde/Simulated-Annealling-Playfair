package ie.gmit.sw.ai;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class SimulatedAnnealling {
	
	PlayfairImpl pf = new PlayfairImpl();
	FilePreparer fp = new FilePreparer();
	Key k = new Key();
	public int temp = 20;
	
	public String decrypt(String[] digraphs, int timeLimit, boolean outputSA) throws IOException {	
		// SA not working very well so setting a time limit on the program running - don't want user to be waiting 10 minutes.
		long startTime = System.currentTimeMillis() + (timeLimit * 1000); // 120000ms = 2 mins
		long upperLimit = System.currentTimeMillis() + 600000;
				
		String parentKey = k.shuffle();
		String childKey;
		double keyFitness;
		String decrypted = pf.pfDecrypt(parentKey, digraphs);
		keyFitness = scoreFitness(decrypted);
		
		for (int i = temp; i > 0; i--) {
			System.out.println("temp " + i);
			for (int j = 10000; j > 0; j--) {
				if (timeLimit > 0 && System.currentTimeMillis() > startTime) {
					System.out.println("Time limit reached, terminating program.");
					System.out.println("Best key found within limit: " + parentKey + "\nResult using key: " + decrypted);
					i = 0;
					j = 0;
				} else if (timeLimit == 0 && System.currentTimeMillis() > upperLimit){
					System.out.println("Upper 10 minute time limit reached, terminating program.");
					System.out.println("Best key found within limit: " + parentKey + "\nResult using key: " + decrypted);
					i = 0;
					j = 0;
				} else {
					childKey = k.modifyKey(parentKey);
					double childFitness = 0;
					double delta;
					String tmpDcrypt = pf.pfDecrypt(childKey, digraphs);
					childFitness = scoreFitness(tmpDcrypt);
					
					delta = childFitness - keyFitness;
					if(delta > 0) {
						parentKey = childKey;
						keyFitness = childFitness;
						decrypted = tmpDcrypt;
						if(outputSA == true)
							System.out.println("New better key: " + parentKey + " with score " + keyFitness + ". Decrypted: " + tmpDcrypt);
					}else if(delta < 0){
						if(Math.pow(Math.E, (delta/temp)) > 0.5) {
							parentKey = childKey;
							keyFitness = childFitness;
							decrypted = tmpDcrypt;
							if(outputSA == true)
								System.out.println("New key: " + parentKey + " with score " + keyFitness+ ". Decrypted: " + tmpDcrypt);
						}
						System.out.println(childKey + " with score " + childFitness + "not chosen");
					} 
				}
			}
		}
		return decrypted;
	}
	
	// Score fitness
	public double scoreFitness(String decrypted) throws IOException {
		Map<String, Double> quadgrams = FilePreparer.getQuad();
		double score = 0;
		double total = 0;
		
		// Calculate total of all probabilities in 4grams.txt
		for (Entry<String, Double> gram : quadgrams.entrySet()) {
			if (quadgrams.keySet().contains(gram.getKey())) {
				double prob = Math.log10(gram.getValue());
				total = total + prob;
			}			
		}		
		
		int limit = decrypted.length();
		if(limit > 45) // Not going to test entire text file, will work on 400 quadgrams
			limit = 45; // 403 = index of last letter in 400th quadgram (399-403)

		for (int index = 0; index <= decrypted.length() - 4; index++) {
			Double occurences = (Double) quadgrams.get(decrypted.substring(index, index + 4));
			if (occurences != null) {
				score += Math.log10(occurences / total);
				//System.out.println(decrypted.substring(index, index + 4) + ": " + Math.log10(occurences / 389373) + ", total: " + score);
			}
		}
		return score;
	}
	
}