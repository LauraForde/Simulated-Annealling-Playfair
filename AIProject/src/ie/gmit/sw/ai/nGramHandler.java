package ie.gmit.sw.ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

// Quadgram stuff - generates a Map of String/Double pairs, gets total of all probabilities in the map

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
	
	// Gets the total of all probabilities in the 4grams file. Don't think its needed but leaving here just in case.
	public double getTotal(Map<String, Double> qmap) {
		double total = 0;
		// Calculate total of all probabilities in 4grams.txt
		for (Entry<String, Double> gram : qmap.entrySet()) {
			if (qmap.keySet().contains(gram.getKey())) {
				double prob = Math.log10(gram.getValue());
				total = total + prob;
			}			
		}		
		return total;
	}

}
