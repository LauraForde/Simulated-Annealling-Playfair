package ie.gmit.sw.ai;

public class PlayfairImpl {
	
	// Step 1 - Prime the plain text 
	public String primePlainTxt(String string) {
		// Pass the given string (uppercase and with no spaces ) to a new stringbuilder var
		// Using StringBuilder because standard String does not allow appending / is immutable
        // Why use SB? - https://stackoverflow.com/q/5234147
        StringBuilder str = new StringBuilder(string.toUpperCase().replaceAll("[^A-Za-z0-9 ]", "")); // 

        // "Parse any double letters and replace the second occurrence with X" - e.g. LETTERKENNY becomes LETXERKENXY
        for (int i = 0; i < string.length() - 1; i += 2) {
            if (str.charAt(i) == str.charAt(i + 1))
            	str.insert(i + 1, "x");
        }
        
        // "If the plaintext has an odd number of characters, append the letter X to make it even"
        if (str.length() % 2 == 1) {
        	str.append("x");
        }
		return str.toString();
	}

	// Step 2 - Break the plain text into digraphs
	// "ARTIFICIAL" becomes [AR, TI, FI, CI, AL]
	public String[] makeDigraphs(String string) {
    
		// Create a new string array of length [half of given string] - String of length 100 becomes array of 50 (2 chars per digraph)
        String digraphs[] = new String[string.length() / 2];
        int index = 0;

        for (int i = 0; i < string.length(); i = i + 2) {
        // Using digraph length doesn't seem to work, will try string / 2
        //for (int i = 0; i < digraphs.length; i++) { 
        	//digraphs[index] = string.substring(i, i + 2); // Assign substring (current char -> +2) to digraph[] element at current index  
           // index = index + 2;
        	
        	digraphs[index] = string.substring(i, i + 2); // Assign substring (current char -> +2) to digraph[] element at current index  
        	index++;
        }
        
        return digraphs;
	}

}
