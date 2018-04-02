// AI Project 2018 - Break a Playfair Cipher using Simulated Annealling
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

// Runner class - doesn't actually do anything, just calls other classes/methods
public class CipherBreaker {
	static PlayfairImpl pf = new PlayfairImpl();
	static FilePreparer fp = new FilePreparer();
	static Scanner input = new Scanner(System.in);


	public static void main(String[] args) throws IOException {
		System.out.println("Enter Option: \n1. Decrypt a file\n2. Exit program");
		menu(); // Run the menu method
	}

	// Handles getting choice from the user
	public static void menu() {
		int choice = input.nextInt();
		switch (choice) {
	        case 1:
	        	runProg(); // User wants to decrypt file, execute runProg() method
	            break;
	        case 2:
	            System.out.println("- Terminated Program -"); // User wants to quit
	            break;
	        default:
	            System.out.println("Invalid option! Please choose an option from the menu below. \n1. Decrypt a file\n2. Exit program");
	            menu(); // Handling possibility of user entering invalid option, just call menu() until choice is valid
	    }
	
	}
	
	public static void runProg() {
		SimulatedAnnealling sa = new SimulatedAnnealling();
		String primedText = askFile();
		String[] digraphs = pf.makeDigraphs(primedText);
		
		System.out.print("Please note this program could take a long time to find the correct key.\nEnter a time limit in seconds (the program will terminate and output the best key found up to that point).\nEnter 0 if you are happy to let the program run as long as required, up to a 10 minute cut off.\nTime Limit: ");
		int timeLimit = input.nextInt();

		try {
			System.out.println("Decrypting... ");
			sa.decrypt(digraphs, timeLimit);
			
		} catch (IOException e) {
			System.out.println("Unable to decrypt. Please choose an option from the menu.");
			menu();
		}
	}
	
	public static String askFile() {
		System.out.println("Please enter name of .txt file to decrypt:");
		String filename = input.next();
		
		if(!filename.contains(".txt")) {
			filename += ".txt";
		}
		
		Path path = FileSystems.getDefault().getPath(filename);
		//System.out.println("Path: " + path);
		String contents = pf.primePlainTxt(fp.readFile(path));
		System.out.println(contents);
		
		return contents;
	}
	
}
