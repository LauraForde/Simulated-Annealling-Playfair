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
		        //System.out.println(line);
		        fileString += line; // Add the current line to the file string
		    }
		} catch (IOException x) {
			System.out.println("Oops! File not found.");
		    CipherBreaker.askFile();
		}
		return fileString;
	}
	
	// Write File
	public void writeFile(String infilename, String decrypted) {
		PrintWriter out;
		// Using ..\\ to go 2 dirs back (dir containing project, desktop if project is on desktop, etc), need to get rid of ..\\ from the passed in file name too
		//String outfilename = "..\\Decrypted-" + infilename.replace("..\\", ""); 
		String outfilename = "Decrypted-" + infilename;//.replace("..\\", ""); 

		// Don't need to worry about extension for in or outfilename, added to filename in CipherBreaker class
		//System.out.println("Writing to " + outfilename.replace("..\\", ""));
		System.out.println("Writing to " + outfilename);//.replace("..\\", ""));

		try {
			out = new PrintWriter(outfilename);
			out.println(decrypted);
			out.close();
			//System.out.println(outfilename.replace("..\\", "") + " saved.");
			System.out.println(outfilename + " saved.");

		} catch (FileNotFoundException e) {
			System.out.println("Oops! Error writing to file.");
		}
	}

}
