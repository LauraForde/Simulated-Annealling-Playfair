package ie.gmit.sw.ai;

import java.io.IOException;
import java.util.Map;

public class SimulatedAnnealling {
	
	PlayfairImpl pf = new PlayfairImpl();
	FileHandler fh = new FileHandler();
	nGramHandler ngh = new nGramHandler();
	Key k = new Key();
	
	private int temperature;
	private int transitions = 15000;
//	private String key;
//	private double fitness;
//	private String decrypted;

	Map<String, Double> quadgramMap;

	public String decrypt(String encryptedText) throws Exception {	
		temperature = (int)((10 + 0.087 * (encryptedText.length() - 84)) / 2);
		//temperature = 10;
		setQuadgramMap(ngh.getQuad());
		String key = k.shuffle(); // Generate random 25 letter key called parent
		String decrypted =  pf.decryptPF(key, encryptedText); // Decrypt with that key
		double keyFitness = scoreFitness(decrypted); // Score the fitness of the key
		double bestFitness = keyFitness;	
		String bestKey = key;
		String bestDec = decrypted;
		String tmpDcrypt;
		System.out.println("Init key: " + key + ", score " + keyFitness + ", decrypt: " + decrypted + "\n");
		
		for(int i = temperature; i > 0; i--) {
			long start = System.currentTimeMillis();
			for (int j = transitions; j > 0; j--) {
				String childKey = k.modifyKey(key); // Make small change
				//Thread.sleep(5);
				tmpDcrypt = pf.decryptPF(childKey, encryptedText);
				//Thread.sleep(5);
				double childFitness = scoreFitness(tmpDcrypt); // Score the fitness of the new key
				//Thread.sleep(5);
				
				//System.out.println("Testing " + childKey + " with score " + childFitness + ". Decrypted: " + tmpDcrypt);
				
				double delta = childFitness - keyFitness;	
				if(delta > 0) { // If the new key is better
//					setKey(childKey);
//					setFitness(childFitness);
//					setDecrypted(decrypted);
					key = childKey; // Set the parent to the new key
					keyFitness = childFitness;
					decrypted = tmpDcrypt;
					//System.out.println("New better: " + key + " with score " + keyFitness + ". Decrypted: " + decrypted);
				} else {
					double ePow = Math.pow(Math.E, -(delta/i));
					//System.out.println("epow: " + ePow);
					if(ePow < 10) { 
//						setKey(childKey);
//						setFitness(childFitness);
//						setDecrypted(decrypted);
						key = childKey; // Set the parent to the new key
						keyFitness = childFitness;
						decrypted = tmpDcrypt;
						//if (j == 1 || j == 25000);
						//System.out.println(" - New: " + key + " with score " + keyFitness + ". Decrypted: " + decrypted);

					}
				}	
				
				if(keyFitness > bestFitness) {
					bestFitness = keyFitness;
					bestKey = key;
					bestDec = tmpDcrypt;
				}
			} // End transitions
			System.out.println("Temp " + i +  ". Current key: " + bestKey + " with score " + bestFitness + ".\nDecrypted: " + bestDec + "\nTime: " + ((System.currentTimeMillis() - start) / 1000.0) + "s \n");	
		} // End temp
		return decrypted;
	}
	
	// Score fitness
		public double scoreFitness(String decrypted) throws IOException {
			double score = 0;
			long total = 4224127912L;

			for (int index = 0; index <= decrypted.length() - 4; index += 4) {
				Double occurences = (Double) quadgramMap.get(decrypted.substring(index, index + 4));
				if (occurences != null) {
					// Probability of gram is count of gram divided by total number... this part is definitely right?
					//System.out.print(Math.log10(occurences / total) + " ");
					score += Math.log10(occurences / total);
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