// AI Project 2018 - Break a Playfair Cipher using Simulated Annealling
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

public class CipherBreaker {
	static PlayfairImpl pf = new PlayfairImpl();
	static FileHandler fp = new FileHandler();
	static SimulatedAnnealling sa = new SimulatedAnnealling();
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
		long start = System.currentTimeMillis();
		
		try {
			System.out.println("Decrypting... ");
			decrypted = sa.decrypt(primedText);
			System.out.println("Decrypted in " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds.");
			fp.writeFile(filename, decrypted);
			
		} catch (IOException e) {
			System.out.println("Unable to decrypt. Please choose an option from the menu.");
		}
		System.out.println("\nEnter Option: \n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
		menu();
	}
	
	public static void knownKey(String primedText) throws Exception {
		String decrypted;
		System.out.print("Enter 'M' to return to the menu.\nEnter a 25-letter key to decrypt with: ");
		String key = input.next(); 
		if(key.equals("m") || key.equals("M")) {
    		System.out.println("\nEnter Option: \n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
			menu();
		} else {
			key = key.toUpperCase().replaceAll("[^A-Za-z0-9 ]", ""); // Replace all non letter chars
			if(key.length() != 25) {
				System.out.print("Key must be 25 characters in length and contain only letters! ");
				knownKey(primedText);
			}
			long start = System.currentTimeMillis();
			decrypted = pf.decryptPF(key, primedText);
			System.out.println("Decrypted text: " + decrypted.substring(0, 100) + "...");
			fp.writeFile(filename, decrypted);
			
			System.out.println("\nDecrypted in " + ((System.currentTimeMillis() - start) / 1000.0) + " seconds. \n\nEnter Option: \n1. Decrypt File using Known Key\n2. Decrypt File using Simulated Annealing\n3. Exit Program");
			menu();
		}
	}
	
	public static String askFile() {
		System.out.print("\nPlease enter name of .txt file to decrypt. Make sure the file is in the same directory as this project, e.g. Documents.\nEnter 'M' to return to the menu.\nFile name:");
		filename = input.next();
		
		if(filename.equals("m") || filename.equals("M")) {
			return "exit";
		} else {
			if(!filename.contains(".txt")) { // If the user didn't enter an extension add it on
				//filename = "..\\" + filename + ".txt"; // ..\ Move 2 dirs back to same folder project is in
				filename = filename + ".txt"; // ..\ Move 2 dirs back to same folder project is in

			}
			
			Path path = FileSystems.getDefault().getPath(filename);
			//System.out.println("Path: " + path);
			String contents = pf.primePlainTxt(fp.readFile(path));
			System.out.println(contents);
			
			return contents;
		}
		
	}
	
}
