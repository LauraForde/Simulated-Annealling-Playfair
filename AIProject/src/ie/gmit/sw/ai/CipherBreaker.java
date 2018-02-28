// AI Project 2018 - Break a Playfair Cipher using Simulated Annealling
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

// Runner class - doesn't actually do anything, just calls other classes/methods
public class CipherBreaker {

	public static void main(String[] args) {
		SimulatedAnnealling sa = new SimulatedAnnealling();
		
		
		FilePreparer fp = new FilePreparer();
		String filename = "EncryptedExamTips.txt";
		//System.out.println("Working Directory = " + System.getProperty("user.dir")); // Get current directory, adapted from https://stackoverflow.com/a/7603444
		Path path = FileSystems.getDefault().getPath(filename);
		//System.out.println("Path: " + path);
		//char file[] = fp.readFile(path).toCharArray();
		//System.out.println("File: " + fp.readFile(path));
		//System.out.print(sa.shuffle(file));
		
		PlayfairImpl pf = new PlayfairImpl();
		String primedText = pf.primePlainTxt(fp.readFile(path));
		//System.out.println("\n1. Primed Text: " + primedText);
		String[] digraphs = pf.makeDigraphs(primedText);
		//System.out.println("2. Digraphs: " + Arrays.toString(digraphs)); // Print array contents - https://stackoverflow.com/a/409795
		
		
		String key = "THEQUICKBROWNFXMPDVLAZYGS";
		pf.printMatrix(key);
		String[] diTest = {"AR", "DN", "TI", "IT", "FI", "HE", "TH"};

		System.out.println(Arrays.toString(digraphs));
		//System.out.println(Arrays.deepToString(matrix)); // deepToString for outputting nested arrays adapted from https://stackoverflow.com/a/409795
		//String decrypted = pf.decrypt(key, digraphs);
		String decrypted = pf.decrypt(key, diTest);
		System.out.println("Decrypted: \n" + decrypted);
		//System.out.println(pf.decrypt(key, digraphs));
	
	}
}
