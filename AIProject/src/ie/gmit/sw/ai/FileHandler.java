package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class FileHandler {
		
	// Read the file - https://stackoverflow.com/a/9524761/7232648
	public String readFile(Path file) throws FileNotFoundException, UnsupportedEncodingException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
		          new FileInputStream(file.toString()), "US-ASCII"));
		  try {
			StringBuilder fileString = new StringBuilder();
		    int count = 0;
		    int intch;
		    while (((intch = br.read()) != -1) && count < 742) {
		    	fileString.append((char) intch);
		      count++;
		    }
		    br.close();
		    return fileString.toString();
		  	} catch (IOException x) {
				System.out.println("Oops! File not found.");
			    CipherBreaker.askFile();
		  }
		  return "Error with file";
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
			System.out.println(decrypted);
			System.out.println(outfilename + " saved.");

		} catch (FileNotFoundException e) {
			System.out.println("Oops! Error writing to file.");
		}
	}

}
