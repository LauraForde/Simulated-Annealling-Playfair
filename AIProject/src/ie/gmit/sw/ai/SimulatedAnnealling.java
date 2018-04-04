package ie.gmit.sw.ai;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class SimulatedAnnealling {
	
	PlayfairImpl pf = new PlayfairImpl();
	FileHandler fh = new FileHandler();
	nGramHandler ngh = new nGramHandler();
	Key k = new Key();
	
	private int temperature;
	private int transitions = 50000;
//	private String key;
//	private double fitness;
//	private String decrypted;

	Map<String, Double> quadgramMap;

	public String decrypt(String encryptedText) throws Exception {	
		//temperature = (int)((10 + 0.087 * (encryptedText.length() - 84)));
		temperature = 15;
		//temperature = 10;
		setQuadgramMap(ngh.getQuad());
		String key = k.shuffle(); // Generate random 25 letter key called parent
		String decrypted =  pf.decryptPF(key, encryptedText); // Decrypt with that key
		double keyFitness = scoreFitness(decrypted); // Score the fitness of the key
		String tmpDcrypt;
		System.out.println("Init key: " + key + ", score " + keyFitness + ", decrypt: " + decrypted + "\n");
		Random r = new Random();
		String bestKey = key;
		double bestFitness = keyFitness;
		
		for(int i = temperature; i > 0; i--) {
			long start = System.currentTimeMillis();
			for (int j = transitions; j > 0; j--) {
				String childKey = k.modifyKey(key); // Make small change
				tmpDcrypt = pf.decryptPF(childKey, encryptedText);
				double childFitness = scoreFitness(tmpDcrypt); // Score the fitness of the new key
				
				//System.out.println("Testing " + childKey + " with score " + childFitness + ". Decrypted: " + tmpDcrypt);
				
				double delta = childFitness - keyFitness;	
				if(delta > 0) {									// if the delta is over 0 this key is better
					key = childKey;
					keyFitness = childFitness;

				} else  {
					if(Math.exp(-delta/i) > 0.5) { // prevent getting stuck
						key = childKey;
						keyFitness = childFitness;
					}
				}
			
				if(keyFitness > bestFitness) {
					bestFitness = keyFitness;
					bestKey = key;
					
				}
			} // End transitions
			//Thread.sleep(10); // Sleeping because it runs too fast to output the score properly... just for display, sleep fixes issue
			//System.out.println("Temp " + i +  ". Current key: " + bestKey + " with score " + bestFitness + ". Decrypted: " + bestDec.substring(0, 20) + "..." + ". Time: " + ((System.currentTimeMillis() - start) / 1000.0) + "s");	
			System.out.println("Temp " + i +  ". Current key: " + key + " with score " + keyFitness + ". Decrypted: " + pf.decryptPF(bestKey, encryptedText).substring(0, 20) + "..." + ". Time: " + ((System.currentTimeMillis() - start) / 1000.0) + "s \n");	
		} // End temp
		//Thread.sleep(5); // Same here
		//System.out.println("Completed at temp " + temperature + " with " + transitions + " transitions each. Total tests: " + (temperature*transitions) + "\nBest Key: " + bestKey + ", with score: " + bestFitness + ". Decrypted: " + bestDec.substring(0, 100) + "...");
		System.out.println("Completed at temp " + temperature + " with " + transitions + " transitions each. Total tests: " + (temperature*transitions) + "\nBest Key: " + key + ", with score: " + keyFitness + ". Decrypted: " + decrypted.substring(0, 100) + "...\n");
		return decrypted;
	}
	
	// Score fitness
		public double scoreFitness(String decrypted) throws IOException {
			double score = 0;
			long total = 4224127912L;
			
			int limit = decrypted.length();
			if(limit > 43) // Not going to test entire text file, will work on 10 quadgrams
				limit = 43; // 13 = index of last letter in 10th quadgram (10-13)
			
			for (int index = 0; index <= limit - 4; index ++) {
				Double occurences = (Double) quadgramMap.get(decrypted.substring(index, index + 4));
				//System.out.print(decrypted.substring(index, index + 4) + "\t\t" + quadgramMap.get(decrypted.substring(index, index + 4)) + "\t\t" + occurences);
				if (occurences != null) {
					score += Math.log10(occurences/total);
				}
			}
			return score;
		}
		
		public Map<String, Double> getQuadgramMap() {
			return quadgramMap;
		}

		public void setQuadgramMap(Map<String, Double> quadgramMap) {
			this.quadgramMap = quadgramMap;
		}
		

}