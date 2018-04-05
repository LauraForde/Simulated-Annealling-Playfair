// AI Project 2018 - Break a Playfair Cipher using Simulated Annealing
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class FileHandler {
		
	// Read the file
	public String readFile(Path file) {
		String fileString = "";
		// Adapted from https://docs.oracle.com/javase/tutorial/essential/io/file.html#textfiles
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        fileString += line; // Add the current line to the file string
		    }
		} catch (IOException x) {
			System.out.println("Oops! File not found."); // Return error message and file name prompt
		    CipherBreaker.askFile();
		}
		return fileString;
	}
	
	// Write File
	public void writeFile(String infilename, String decrypted) throws Exception {
		PrintWriter out;
		String outfilename = "Decrypted-" + infilename;

		// Don't need to worry about extension for in or outfilename, added to filename in CipherBreaker class
		System.out.println("Writing to " + outfilename);

		try {
			out = new PrintWriter(outfilename);
			out.println(decrypted);
			out.close();
			System.out.println(outfilename + " saved.");

		} catch (FileNotFoundException e) {
			System.out.println("Oops! Error writing to file."); // Return error message and menu
			CipherBreaker.menu();
		}
	}

}
