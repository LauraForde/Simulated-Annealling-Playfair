package ie.gmit.sw.ai;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class FilePreparer {
	
	// Read the file
	public static String readFile(Path file) {
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
	
	// Quadgram stuff
	public static Map getQuad() throws IOException {
		// https://stackoverflow.com/a/8886723
		Map<String, String> map = new HashMap<String, String>(); // Should be <String, Integer> but will worry about that later
        BufferedReader in = new BufferedReader(new FileReader("4grams.txt"));
        String line = "";
        while ((line = in.readLine()) != null) {
            String[] parts = line.split(" ");
            map.put(parts[0], parts[1]);
        }
        in.close();

    	/*int count = 0;
        for (String gram: map.keySet()){
            if (count < 5) {
            	String key = gram.toString();
            	String value = map.get(gram).toString();  
            	System.out.print(key + " " + value + "\t");
            	count++;
            }
        } */
        
		return map;
	}

}
