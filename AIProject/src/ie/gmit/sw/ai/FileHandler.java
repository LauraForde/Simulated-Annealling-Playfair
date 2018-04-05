package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandler {
		
	// Read the file - https://stackoverflow.com/a/9524761/7232648
	public String readFile(Path file) {
		String fileString = "";
		// Adapted from https://docs.oracle.com/javase/tutorial/essential/io/file.html#textfiles
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
		    String line = null;
		    if(file.toFile().length() > 1000) {
		    	System.out.println("Oops! File too large, please try a smaller file.");
			    CipherBreaker.askFile();
		    }else {
		    	while ((line = reader.readLine()) != null) {
			        fileString += line; // Add the current line to the file string
			    }
		    }
		    
		} catch (IOException e) {
			System.out.println("Oops! File not found.");
		    CipherBreaker.askFile();
		}
		return fileString;
	}
	
	// Write File
	public void writeFile(String infilename, String decrypted) {
		PrintWriter out;
		String outfilename = "Decrypted-" + infilename;

		// Don't need to worry about extension for in or outfilename, added to filename in CipherBreaker class
		System.out.println("Writing to " + outfilename);
		try {
			out = new PrintWriter(outfilename);
			out.println(decrypted);
			out.close();
			System.out.println(decrypted);
			System.out.println(outfilename + " saved.");

		} catch (FileNotFoundException e) {
			System.out.println("Oops! Error writing to file.");
		}
	}
}
