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
		String nKey = flipColumns(key);
		System.out.println("New Key:");
		PlayfairImpl.printMatrix(nKey);
		
	}
	
	private static String flipColumns(String key) {
		char[] newKey = key.toCharArray(); // Make a char array with given key string
		int length = key.length() - (key.length()/5);  // Length = length of original key minus (length / 5) -> 25 - (25/5 = 5) = 20. Solves out of bounds issue.
		
		for(int i = 0; i < key.length() / 5; i++) { // For each char in key
			for(int j = 0; j < key.length() / 5; j++) { // for each char in row (length/5(rows) = 5)
				// Multiplying by 5 because basically swapping chars with char in same col diff row.
				char temp = key.charAt(i*5 + j); // Save char at pos i*5 + j, ex: 3*5 + 4 = 19, into temp var
				newKey[(i*5) + j] =  key.charAt(length + j); // Set the char at that pos in the new array to be char in original key at length+j
				newKey[length + j] =  temp; // Set the char at length + j to the temp character.
				// Tested using PlayfairImpl.printMatrix(key), does flip all columns.
			}
			length = length - 5;
		}
		return new String(newKey);
		
	}
}
