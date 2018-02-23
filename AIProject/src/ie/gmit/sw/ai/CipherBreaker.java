// AI Project 2018 - Break a Playfair Cipher using Simulated Annealling
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

import java.nio.file.FileSystems;
import java.nio.file.Path;

// Runner class - doesn't actually do anything, just calls other classes/methods
public class CipherBreaker {

	public static void main(String[] args) {
		SimulatedAnnealling sa = new SimulatedAnnealling();
		/*char key[] = "THEQUICKBROWNFXMPDVLAZYGS".toCharArray(); // to char array - https://stackoverflow.com/a/11711248
		System.out.println("shuffling THEQUICKBROWNFXMPDVLAZYGS");
		System.out.print("new key: ");
		System.out.print(sa.shuffle(key));*/
		
		FilePreparer fp = new FilePreparer();
		String filename = "EncryptedExamTips.txt";
		System.out.println("Working Directory = " + System.getProperty("user.dir")); // Get current directory, adapted from https://stackoverflow.com/a/7603444
		Path path = FileSystems.getDefault().getPath(filename);
		System.out.println("Path: " + path);
		char file[] = fp.readFile(path).toCharArray();
		System.out.print("File: " + fp.readFile(path) + "\nShuffled: ");
		System.out.print(sa.shuffle(file));
		
		
		
	}
}
