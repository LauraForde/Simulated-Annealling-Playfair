// AI Project 2018 - Break a Playfair Cipher using Simulated Annealling
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;


public class CipherBreaker {
	static PlayfairImpl pf = new PlayfairImpl();
	static FilePreparer fp = new FilePreparer();
	static Scanner input = new Scanner(System.in);
	static String filename;


	public static void main(String[] args) throws IOException {
		System.out.println("Enter Option: \n1. Decrypt File (known key)\n2. Decrypt File (unknown key)\n3. Exit program");
		menu(); // Run the menu method
		
	}

	// Handles getting choice from the user
	public static void menu() {
		int choice = input.nextInt();
		String primedText;
		switch (choice) {
	        case 1:
	        	primedText = askFile();
	        	knownKey(primedText);
	        	break;
	        case 2:
	        	runSimulatedAnnealling(); // User wants to decrypt file, execute runProg() method
	        	break;
	        case 3:
	            System.out.println("- Terminated Program -"); // User wants to quit
	            break;
	        default:
	            System.out.println("Invalid option! Please choose an option from the menu below. \n1. Decrypt File (known key)\n2. Decrypt File (unknown key)\n3. Exit program");
	            menu(); // Handling possibility of user entering invalid option, just call menu() until choice is valid
	    }
	
	}
	
	public static void runSimulatedAnnealling() {
		SimulatedAnnealling sa = new SimulatedAnnealling();
		String primedText = askFile();
		String[] digraphs = pf.makeDigraphs(primedText);
		String decrypted;

		System.out.print("Please note this program could take a long time to find the correct key.\nEnter a time limit in seconds (the program will terminate and output the best key found up to that point).\nEnter 0 if you are happy to let the program run as long as required, up to a 10 minute cut off.\nTime Limit: ");
		int timeLimit = input.nextInt();

		try {
			System.out.println("Decrypting... ");
			decrypted = sa.decrypt(digraphs, timeLimit);
			fp.writeFile(filename, decrypted);
			
		} catch (IOException e) {
			System.out.println("Unable to decrypt. Please choose an option from the menu.");
		}
		System.out.println("\nEnter Option: \n1. Decrypt File (known key)\n2. Decrypt File (unknown key)\n3. Exit program");
		menu();
	}
	
	public static void knownKey(String primedText) {
		long start = System.currentTimeMillis();
		String[] digraphs = pf.makeDigraphs(primedText);
		String decrypted;
		System.out.print("Enter key to decrypt with: ");
		String key = input.next(); 
		key = key.toUpperCase().replaceAll("[^A-Za-z0-9 ]", ""); // Replace all non letter chars
		if(key.length() != 25) {
			System.out.print("Key must be 25 characters in length and contain only letters! ");
			knownKey(primedText);
		}
		decrypted = pf.pfDecrypt(key, digraphs);
		fp.writeFile(filename, decrypted);
		
		System.out.println("\nDecrypted in " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds. \n\nEnter Option: \n1. Decrypt File (known key)\n2. Decrypt File (unknown key)\n3. Exit program");
		menu();
	
	}
	
	public static String askFile() {
		System.out.print("\nPlease enter name of .txt file to decrypt. Make sure the file is in the same directory as this project, e.g. Documents.\nFile name:");
		filename = input.next();
		
		if(!filename.contains(".txt")) { // If the user didn't enter an extension add it on
			filename ="..\\" + filename + ".txt"; // ..\\ Move 2 dirs back to same folder project is in
		}
		
		Path path = FileSystems.getDefault().getPath(filename);
		//System.out.println("Path: " + path);
		String contents = pf.primePlainTxt(fp.readFile(path));
		System.out.println(contents);
		
		return contents;
	}
	
}
