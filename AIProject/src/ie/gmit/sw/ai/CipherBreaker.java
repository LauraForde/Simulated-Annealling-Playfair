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
		String nKey = swapColumns(key, 2, 2); // Try swapping cols 2 and 4
		System.out.println("New Key:");
		PlayfairImpl.printMatrix(nKey);
		
	}
	
	private static String swapColumns(String key, int col1, int col2) {
		if (col1 == col2) { // Making sure same column can't be passed in to be swapped
			System.out.println("Same");
			col1 = 2;
			col2 = 4;
		}

		System.out.println("Different");
		char[] newKey = key.toCharArray();
		for(int i = 0; i < key.length() / 5 ; i++) {
			int rowInd = i*5; // Row of index -> eg 2 = 10 (third row) - Accounting for each item in a column being 5 indices away from the items either side... if that makes sense...
			char temp =  newKey[(rowInd + col1)]; // Set temporary = newKey at row index + column number
			newKey[(rowInd + col1)] = newKey[(rowInd + col2)]; // Set char at that index to be the char on the same row but in the second column
			newKey[(rowInd + col2)] = temp; // Set that index to be the temp value
			// I.e. for each row, swap the chars in the given columns
		}
		return new String(newKey);
	}
	
}
