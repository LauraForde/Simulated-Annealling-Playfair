// AI Project 2018 - Break a Playfair Cipher using Simulated Annealling
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.awt.BufferCapabilities.FlipContents;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

// Runner class - doesn't actually do anything, just calls other classes/methods
public class CipherBreaker {

	public static void main(String[] args) throws IOException {
		/*SimulatedAnnealling sa = new SimulatedAnnealling();
		PlayfairImpl pf = new PlayfairImpl();

		String filename = "EncryptedExamTips.txt";
		//System.out.println("Working Directory = " + System.getProperty("user.dir")); // Get current directory, adapted from https://stackoverflow.com/a/7603444
		Path path = FileSystems.getDefault().getPath(filename);
		//System.out.println("Path: " + path);
		
		String primedText = pf.primePlainTxt(FilePreparer.readFile(path));
		String[] digraphs = pf.makeDigraphs(primedText);
		//System.out.println("\n1. Primed Text: " + primedText);
		//System.out.println("2. Digraphs: " + Arrays.toString(digraphs)); // Print array contents - https://stackoverflow.com/a/409795

		//pf.printMatrix(key); // Prints two matrices, first is th given key and second is just ints 0-24

		String decrypted = sa.decrypt(digraphs);
		//String decrypted = pf.decrypt(key, digraphs);
		System.out.println("\nDecrypted: \n" + decrypted);*/
		
		String key = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
		PlayfairImpl.printMatrix(key);
		String nKey = swapLetters(key, (int)(Math.random() * (key.length()-1)), (int)(Math.random() * (key.length()-1)));
		//String nKey = swapLetters(key, 2, 2);
		System.out.println("New Key:");
		PlayfairImpl.printMatrix(nKey);
		
	}

	private static String swapLetters(String key, int l1, int l2) {
		char[] newKey = key.toCharArray(); // Make a char array with given key string
		
		if(l1 == l2) { // If given indices are the same
			l2 = (int)(Math.random() * (key.length()-1));
		}

		System.out.println("swap " + newKey[l1] + " & "+ newKey[l2]);
		char temp = newKey[l1];
		newKey[l1] = newKey[l2];
		newKey[l2] = temp;

		return new String(newKey);
	}

	
}
