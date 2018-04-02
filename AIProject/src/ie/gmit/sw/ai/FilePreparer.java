package ie.gmit.sw.ai;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class FilePreparer {
	
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
	
	// Quadgram stuff - method generates a Map of String/Double pairs and returns completed map
	public static Map<String, Double> getQuad() throws IOException {
		// Read text file to map adapted from https://stackoverflow.com/a/8886723
		Map<String, Double> map = new HashMap<String, Double>(); // Should be <String, Integer> but will worry about that later
	    BufferedReader in = new BufferedReader(new FileReader("4grams.txt"));
	    String line = "";
	    while ((line = in.readLine()) != null) {
	        String[] parts = line.split(" ");
	        map.put(parts[0], Double.parseDouble(parts[1]));
	    }
	    in.close();
		return map;
	}

}
