package ie.gmit.sw.ai;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SimulatedAnnealling {
	
	PlayfairImpl pf = new PlayfairImpl();
	FilePreparer fp = new FilePreparer();
	Key k = new Key();
	
	private String parentKey;
	private String childKey;
	private double keyFitness;
	
	String filename = "EncryptedExamTips.txt";
	Path path = FileSystems.getDefault().getPath(filename);
	String primedText = pf.primePlainTxt(fp.readFile(path));
	String[] digraphs = pf.makeDigraphs(primedText);
	String initialKey = k.shuffle(); 
	
	public String decrypt(String primedText) throws IOException {		
		String decrypted = pf.pfDecrypt(initialKey, digraphs);
		keyFitness = scoreFitness(decrypted, initialKey);
		parentKey = initialKey;
		
		for (int i = 5; i > 0; i--) {
			for (int j = 50000; j > 0; j--) {
				childKey = k.modifyKey(parentKey);
				String[] tmpDigraphs = pf.makeDigraphs(primedText);
				String tmpDcrypt = pf.pfDecrypt(childKey, tmpDigraphs);		
				
				
				double childFitness = scoreFitness(tmpDcrypt, childKey);
				double delta = childFitness - keyFitness;
				if(delta > 0) {
					parentKey = childKey;
					keyFitness = childFitness;
					System.out.println("Child better, new key: " + parentKey);
				}else if(delta < 0){ 
					double p = Math.pow(Math.E,(delta/10));
					if (p > 0.5) {
						System.out.println("Child not better, still using " + parentKey + " with score " + keyFitness);
						parentKey = childKey;
						keyFitness = childFitness;					}
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
		if(limit > 403) // Not going to test entire text file, will work on 400 quadgrams
			limit = 403; // 403 = index of last letter in 400th quadgram (399-403)

		for (int index = 0; index <= limit - 4; index++) {
			//System.out.print((index + 1) + ". " + decrypted.substring(index, index + 4) + "[" + index + "-" + (index+4) + "]" + "\n");
			Double occurences = (Double) quadgrams.get(decrypted.substring(index, index + 4));
			if (occurences != null) {
				//System.out.println("Oc: " + occurences + "  / L: " + Math.log10(occurences));
				score = score + Math.log10(occurences/389373);
			}
		}
		//System.out.println("Score: " + score);		
		
		return score;

	}
	
	}