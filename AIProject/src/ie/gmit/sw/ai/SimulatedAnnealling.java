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

	// Initialise map of quadgrams <gram, count of occurrences>. Should be Int but gave a lot more trouble than simply using Double, haven't come across any implications.
	Map<String, Double> quadgramMap;
	
	/* Temp / Transitions Explanation
	 * According to Cowan in "Breaking Short Playfair Ciphers with the Simulated Annealing Algorithm", best temp to use is normally (10 + 0.087 * (encryptedText.length() - 84))
	 * Tried above equation with variety of transitions (5k, 10k, 20k, 25k) and none decrypted the text.
	 * Tried suggested temp 10 with 50k.
	 * Tried temp 7 with 40k, 50k.
	 * Tried temp 15 with 25k, 30k.
	 * Tried temp 20, 50k, steps -2.
	 * Temp 20, 50k transitions only one tested so far that seems to break it. Takes longer than it should though, finds key after
	 * 2-3 steps down in temp. Need to implement a break case? break-after-x-non-changes? 
	 */
	
	private int temperature = 20;
	private int transitions = 50000;
	
	// Also tried to speed up process by only using first 400 chars. Doesn't decrypt with any variations from the list above?

	public String decrypt(String encryptedText) throws Exception {	
		//temperature = (int)((10 + 0.087 * (encryptedText.length() - 84)));
		Random r = new SecureRandom(); // For e^(delta/time) stuff - was using plain Random, SecureRandom actually "more" random - https://stackoverflow.com/a/11052736/7232648
		
		setQuadgramMap(ngh.getQuad()); // Get the map from nGramHandler - Set this once here, when decrypt method called from CipherBreak. Could set it in scoreFitness but would be getting the map every single time it scores a new key - not exactly efficient.
		
		// Parent stuff
		String parentKey = k.shuffle(); // Generate random 25 letter key called parent
		String decrypted =  pf.decryptPF(parentKey, encryptedText); // Decrypt with that key
		double parentFitness = scoreFitness(decrypted); // Score the fitness of the key
		
		// Child key stuff
		String childKey;
		String tmpDcrypt;
		double childFitness;
		
		// For breaking out of the loop
		String lastKey = parentKey;
		int count = 1; // Not going to count first time key is output
		
		System.out.println(" key: " + parentKey + ", score " + parentFitness + ", decrypt: " + decrypted.substring(0, 20) + "...\n");
		
		for(int i = temperature; i > 0; i--) { // For each temp down to 0, steps of -1
			//System.out.print((100 - (i * 5))+ "%... \r"); // \r (carriage return) DOES NOT WORK IN ECLIPSE. Should work in command line. Need to test.
			for (int j = transitions; j > 0; j--) {
				childKey = k.modifyKey(parentKey); // Make small change to parent
				tmpDcrypt = pf.decryptPF(childKey, encryptedText); // Decrypt with the new key
				childFitness = scoreFitness(tmpDcrypt); // Score the fitness of the new key based on what it returns as decrypted
				
				double delta = childFitness - parentFitness;	
				if(delta > 0) { // Child / new key better
					parentKey = childKey; // Set parent = child
					parentFitness = childFitness; // Set parent fitness = child fitness

				} else  {
					// Equation will return double between 0 and 1 - closer to 1 = better, delta/i means will be less likely to take worse key at lower temp(i)
					if(Math.exp(delta/i) > r.nextDouble()) { // Using 
						parentKey = childKey; // Set parent = child
						parentFitness = childFitness; // Set parent fitness = child fitness
					}
				}
			
			} // End transitions
			System.out.println("LK: " + lastKey + ", count: " + count + "\nTemp " + i + ": " + parentKey + ", " + parentFitness + ": " + pf.decryptPF(parentKey, encryptedText).substring(0, 20) + "...");
			if (lastKey.equals(parentKey)) {
				lastKey = parentKey;
				count++;
			} else {
				lastKey = parentKey;
			}
		} // End temp
		Thread.sleep(5); // Was having minor issues with console not being able to keep up with variables, sleeping for 5 ms before outputting final just in case.
		System.out.println("Completed at temp " + temperature + " with " + transitions + " transitions each. Total tests: " + (temperature*transitions) + "\nBest Key: " + parentKey + ", with score: " + parentFitness + ". Decrypted: " + pf.decryptPF(parentKey, encryptedText).substring(0, 20) + "...");
		decrypted = pf.decryptPF(parentKey, encryptedText); //
		return decrypted;
	}
	
	// Score fitness
	public double scoreFitness(String decrypted) throws IOException {
		double score = 0;
		long total = 4224127912L; // Total number of possible quadgrams = 4,224,127,912.
		
		for(int i = 0; i < decrypted.length() - 4; i++) { // For each char in the decrypted text
			Double occurrences = quadgramMap.get(decrypted.substring(i, i+4)); // Get the number of occurrences of the current gram (chars i to i+4) of decrypted text
			if(occurrences == null) { // If the gram has no occurrences in 4grams.txt
				occurrences = 1.0; // Set it to one occurrence (for the sake of occurrences / total -> can't divide by 0)
			}
			score += Math.log10((double) occurrences / total); // Add the probability (occurrences / total)
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