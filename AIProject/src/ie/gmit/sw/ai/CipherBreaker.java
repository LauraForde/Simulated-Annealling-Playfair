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
		//String nKey = flipRows(key);
		System.out.println("New Key:");
		//PlayfairImpl.printMatrix(nKey);
		
	}
	
	private static String flipRows(String key) {
		String[] newRow = new String[5]; // String array for any new row, length 5
		StringBuilder newKey = new StringBuilder(); // Using string builder to make it easy to add each new row to new key string
		
		for(int i = 0; i < 5; i++) { // For each row in the key, i.e. 5 rows
			String row = key.substring(i*5, i*5 + 5); // Get the row substring of the key based on for loop index - 2*5, 2*5 +5 = 10 to 15 
			String revRow = new StringBuffer(row).reverse().toString(); // Reverse the stringified row, need StringBuffer for reversing
			newKey.append(revRow); // Append the reversed row to the new key
		}
		return newKey.toString(); // Return stringified new key
	}
	
}
