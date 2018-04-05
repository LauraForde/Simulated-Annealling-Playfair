// AI Project 2018 - Break a Playfair Cipher using Simulated Annealing
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

// Handles all quadgram stuff - generates a Map of String/Double pairs, gets total of all probabilities in the map

public class nGramHandler {
	
	// Returns a hashmap of all quadgrams in the 4grams file, key value pairs.
	public Map<String, Double> getQuad() throws NumberFormatException, IOException{
		Map<String, Double> quadgramMap = new HashMap<String, Double>();
		// Read text file to map adapted from https://stackoverflow.com/a/8886723
	    BufferedReader in = new BufferedReader(new FileReader("4grams.txt"));

	    String line = "";
	    while ((line = in.readLine()) != null) {
	        String[] parts = line.split(" "); // Split the line at the space
	        quadgramMap.put(parts[0], Double.parseDouble(parts[1])); // first part is the gram, second is the count
	    }
	    in.close(); // Good practise to close any streams etc
		return quadgramMap;
	}
	
	// Gets the total of all probabilities in the 4grams file. Ended up not using but leaving here just in case.
	public double getTotal(Map<String, Double> qmap) {
		double total = 0;
		// Calculate total of all probabilities in 4grams.txt
		for (Entry<String, Double> gram : qmap.entrySet()) { // For each entry in the quadgram map
			if (qmap.keySet().contains(gram.getKey())) { // If the map contains the key
				double prob = Math.log10(gram.getValue()); // Add the gram's occurrence count to the total
				total = total + prob;
			}			
		}		
		return total;
	}

}
