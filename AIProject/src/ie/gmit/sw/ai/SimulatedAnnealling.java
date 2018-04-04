package ie.gmit.sw.ai;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

public class SimulatedAnnealling {
	
	PlayfairImpl pf = new PlayfairImpl();
	FileHandler fh = new FileHandler();
	nGramHandler ngh = new nGramHandler();
	Key k = new Key();
	
	private int temperature;
	private int transitions = 50000;

	Map<String, Double> quadgramMap;

	public String decrypt(String encryptedText) throws Exception {	
		//temperature = (int)((10 + 0.087 * (encryptedText.length() - 84)));
		temperature = 20;
		//temperature = 10;
		setQuadgramMap(ngh.getQuad());
		String parentKey = k.shuffle(); // Generate random 25 letter key called parent
		String decrypted =  pf.decryptPF(parentKey, encryptedText); // Decrypt with that key
		double parentFitness = scoreFitness(decrypted); // Score the fitness of the key
		String tmpDcrypt;
		System.out.println("Init key: " + parentKey + ", score " + parentFitness + ", decrypt: " + decrypted + "\n");
		Random r = new SecureRandom();
		
		for(int i = temperature; i > 0; i--) {
			long start = System.currentTimeMillis();
			for (int j = transitions; j > 0; j--) {
				String childKey = k.modifyKey(parentKey); // Make small change
				tmpDcrypt = pf.decryptPF(childKey, encryptedText);
				double childFitness = scoreFitness(tmpDcrypt); // Score the fitness of the new key
				
				double delta = childFitness - parentFitness;	
				if(delta > 0) { // Child / new key better
					parentKey = childKey; // Set parent = child
					parentFitness = childFitness; // Set parent fitness = child fitness

				} else  {
					// Equation will return double between 0 and 1 - closer to 1 = better, delta/i means will be less likely to take worse key at lower temp(i)
					if(Math.exp(delta/i) > r.nextDouble()) {
						parentKey = childKey; // Set parent = child
						parentFitness = childFitness; // Set parent fitness = child fitness
					}
				}
			
			} // End transitions
			//Thread.sleep(10); // Sleeping because it runs too fast to output the score properly... just for display, sleep fixes issue
			//System.out.println("Temp " + i +  ". Current key: " + bestKey + " with score " + bestFitness + ". Decrypted: " + bestDec.substring(0, 20) + "..." + ". Time: " + ((System.currentTimeMillis() - start) / 1000.0) + "s");	
			System.out.println("Temp " + i +  ". Current key: " + parentKey + " with score " + parentFitness + ". Decrypted: " + pf.decryptPF(parentKey, encryptedText).substring(0, 20) + "..." + ". Time: " + ((System.currentTimeMillis() - start) / 1000.0) + "s \n");	
		} // End temp
		//Thread.sleep(5); // Same here
		System.out.println("Completed at temp " + temperature + " with " + transitions + " transitions each. Total tests: " + (temperature*transitions) + "\nBest Key: " + parentKey + ", with score: " + parentFitness + ". Decrypted: " + pf.decryptPF(parentKey, encryptedText).substring(0, 20) + "...");
		return decrypted;
	}
	
	// Score fitness
	public double scoreFitness(String decrypted) throws IOException {
		double score = 0;
		long total = 4224127912L; // Total number of possible quadgrams = 4,224,127,912.
		
		for(int i = 0; i < decrypted.length() - 4; i++) { // For each char in the decrypted text
			Double occurences = quadgramMap.get(decrypted.substring(i, i+4)); // Get the number of occurences of the current gram (chars i to i+4) of decrypted text
			if(occurences == null) { // If the gram has no occurences in 4grams.txt
				occurences = 1.0; // Set it to one occurance (for the sake of occurences / total -> can't divide by 0)
			}
			score += Math.log10((double) occurences / total); // Add the probability (occurences / total)
		}
		return score;
	}
	
	
	// Gets & Sets for the map of quadgrams
	public Map<String, Double> getQuadgramMap() {
		return quadgramMap;
	}

	public void setQuadgramMap(Map<String, Double> quadgramMap) {
		this.quadgramMap = quadgramMap;
	}
		
}