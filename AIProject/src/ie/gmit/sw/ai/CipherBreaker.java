// AI Project 2018 - Break a Playfair Cipher using Simulated Annealling
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

// Runner class - doesn't actually do anything, just calls other classes/methods
public class CipherBreaker {
	
	SimulatedAnnealling sa = new SimulatedAnnealling();
	static PlayfairImpl pf = new PlayfairImpl();
	static FilePreparer fp = new FilePreparer();
	static Scanner input = new Scanner(System.in);


	public static void main(String[] args) throws IOException {
		
		
		System.out.println("Enter Option: \n1. Decrypt a file\n2. Exit program");
		menu();
		
		
	}

	public static void menu() {
		int choice = input.nextInt();
		switch (choice) {
	        case 1:
	            //System.out.println("Decrypt");
	        	runProg();
	            break;
	        case 2:
	            System.out.println("Quit");
	            break;
	        default:
	            System.out.println("Invalid option! Please choose an option from the menu below. \n1. Decrypt a file\n2. Exit program");
	            menu(); // Handling possibility of 
	    }
	
	}
	
	public static void runProg() {
		
		String primedText = askFile();
		String[] digraphs = pf.makeDigraphs(primedText);
		//System.out.println("\n1. Primed Text: " + primedText);
		//System.out.println("2. Digraphs: " + Arrays.toString(digraphs)); // Print array contents - https://stackoverflow.com/a/409795

		//pf.printMatrix(key); // Prints two matrices, first is th given key and second is just ints 0-24

		//sa.decrypt(digraphs);
		//sa.scoreFitness("ESSIOHFABNWCVZGOCOISKLYMTCESGDYAWEIRSIESFICIZQISDZDVIAISISCHEUYCALRYWSSIRSKNCHOUKSTCIFOXDTUKKNLNALOK");
		//String decrypted = pf.decrypt(key, digraphs);
		//System.out.println("\nDecrypted: \n" + decrypted);
	}
	
	public static String askFile() {
		System.out.println("Please enter name of .txt file to decrypt:");
		String filename = input.next();
		
		if(!filename.contains(".txt")) {
			filename += ".txt";
		}
		
		Path path = FileSystems.getDefault().getPath(filename);
		System.out.println("Path: " + path);
		String contents = pf.primePlainTxt(fp.readFile(path));
		System.out.println(contents);
		
		return contents;
	}
	
}
