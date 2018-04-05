// AI Project 2018 - Break a Playfair Cipher using Simulated Annealing
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class CipherBreaker {
	static PlayfairImpl pf = new PlayfairImpl();
	static FileHandler fp = new FileHandler();
	static SimulatedAnnealing sa = new SimulatedAnnealing();
	static nGramHandler ngh = new nGramHandler();
	
	static Scanner input = new Scanner(System.in);
	static String filename;

	public static void main(String[] args) throws Exception {
		System.out.println("Enter Option: \n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
		menu(); // Run the menu method
	}

	// Handles getting choice from the user
	public static void menu() throws Exception {
		int choice = input.nextInt();
		String primedText;
		switch (choice) {
	        case 1:
	        	primedText = askFile();
	        	if(primedText.equals("exit")) {
	        		System.out.println("Enter Option: \n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
	        		menu();
	        		break;
	        	} else {
	        		knownKey(primedText); // User wants to decrypt file, execute runProg() method
	        		break;
	        	}
	        case 2:
	        	primedText = askFile();
	        	if(primedText.equals("exit")) {
	        		System.out.println("\nEnter Option: \n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
	        		menu();
	        		break;
	        	} else {
	        		runSimulatedAnnealling(primedText); // User wants to decrypt file, execute runProg() method
	        		break;
	        	}
	        case 3:
	            System.out.println("- Terminated Program -"); // User wants to quit
	            break;
	        default:
        		System.out.println("Invalid option! Please choose an option from the menu below.\n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
	            menu(); // Handling possibility of user entering invalid option, just call menu() until choice is valid
	    }
	}
	
	public static void runSimulatedAnnealling(String primedText) throws Exception {
		String decrypted;
		long start = System.currentTimeMillis(); // Start a timer
		
		try {
			System.out.println("Decrypting... ");
			decrypted = sa.decrypt(primedText); // Decrypt the text
			System.out.println("Decrypted in " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds."); // Output length of time taken to decrypt
			fp.writeFile(filename, decrypted); // Write decrypted text to file
			
		} catch (IOException e) {
			System.out.println("Unable to decrypt. Please choose an option from the menu.");
		} catch (NullPointerException e) {
			System.out.println("Oops! The file you selected is too large. Please enter a smaller file, ideally around 750 bytes in size.");
		}
		
		System.out.println("\nEnter Option: \n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
		menu();
	}
	
	public static void knownKey(String primedText) throws Exception {
		String decrypted;
		System.out.print("Enter 'M' to return to the menu.\nEnter a 25-letter key to decrypt with: ");
		String key = input.next(); 
		if(key.equals("m") || key.equals("M")) { // If the user enters M or m, return to menu
    		System.out.println("\nEnter Option: \n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
			menu();
		} else {
			key = key.toUpperCase().replaceAll("[^A-Za-z0-9 ]", ""); // Replace all non letter chars
			if(key.length() != 25) {
				System.out.print("Key must be 25 characters in length and contain only letters! ");
				knownKey(primedText); // Ask the user to enter key until valid
			}
			System.out.println("Decrypting... ");
			long start = System.currentTimeMillis(); // Start a timer
			decrypted = pf.decryptPF(key, primedText); // Decrypt the text
			fp.writeFile(filename, decrypted); // Write the text to a file
			
			System.out.println("\nDecrypted in " + ((System.currentTimeMillis() - start)) + " milliseconds. \n\nEnter Option: \n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
			menu(); // Return to the menu when done
		}
	}
	
	public static String askFile() {
		System.out.print("\nPlease enter name of .txt file to decrypt. Make sure the file is in the same directory as this project, e.g. Documents.\nEnter 'M' to return to the menu.\nFile name:");
		filename = input.next();
		
		if(filename.equals("m") || filename.equals("M")) {
			return "exit";
		} else {
			if(!filename.contains(".txt")) { // If the user didn't enter an extension add it on
				filename = filename + ".txt"; // ..\ Move 2 dirs back to same folder project is in

			}
			
			Path path = FileSystems.getDefault().getPath(filename);
			//System.out.println("Path: " + path);
			String contents = pf.primePlainTxt(fp.readFile(path));
			//System.out.println(contents);
			
			return contents;
		}
		
	}
	
}
