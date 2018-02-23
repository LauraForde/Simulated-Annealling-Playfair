package ie.gmit.sw.ai;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class FilePreparer {
	
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
		    System.err.format("IOException: %s%n", x);
		}

		return fileString;
		
	}

}
