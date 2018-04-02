package ie.gmit.sw.ai;

import java.io.IOException;
import java.util.Map;

public class SimulatedAnnealling {
	
	PlayfairImpl pf = new PlayfairImpl();
	FilePreparer fp = new FilePreparer();
	Key k = new Key();
	
	private String parentKey = k.shuffle();
	private String childKey;
	private double keyFitness;
	
	public String decrypt(String[] digraphs) throws IOException {		
		String decrypted = pf.pfDecrypt(parentKey, digraphs);
		keyFitness = scoreFitness(decrypted, parentKey);
		
		for (int i = 6; i > 0; i--) {
			for (int j = 10000; j > 0; j--) {
				childKey = k.modifyKey(parentKey); // Make a small change to the parent key
				String tmpDcrypt = pf.pfDecrypt(childKey, digraphs);		
				double childFitness = scoreFitness(tmpDcrypt, childKey); // Score the fitness of the new key
				
				double delta = childFitness - keyFitness;
				if(delta > 0) {
					parentKey = childKey;
					keyFitness = childFitness;
					System.out.println("D: " + delta + ". Child better, new key: " + parentKey + ". Decrypted: " + tmpDcrypt);
				}else if(delta < 0){ 
					double p = Math.pow(Math.E,(delta/10));
					if (p > 0.5) {
						System.out.println("D: " + delta + ". Child not better, still using " + parentKey + " with score " + keyFitness+ ". Decrypted: " + tmpDcrypt);
						parentKey = childKey;
						keyFitness = childFitness;					
					}
					System.out.println("D: " + delta + ". " + childKey  + ", score " + childFitness + ", not accept.");
				}
			}
		}
		return decrypted;
	}
	
	// Score fitness
	public double scoreFitness(String decrypted, String key) throws IOException {
		Map<String, Double> quadgrams = FilePreparer.getQuad();
		double score = 0;
		
		int limit = decrypted.length();
		if(limit > 103) // Not going to test entire text file, will work on 400 quadgrams
			limit = 103; // 403 = index of last letter in 400th quadgram (399-403)

		for (int index = 0; index <= limit - 4; index++) {
			Double occurences = (Double) quadgrams.get(decrypted.substring(index, index + 4));
			if (occurences != null) {
				double temp = Math.log10(occurences/389373);
				score += temp;
				//score = score + Math.log10(occurences/389373);
			}
		}
		
		return score;

	}
	
}