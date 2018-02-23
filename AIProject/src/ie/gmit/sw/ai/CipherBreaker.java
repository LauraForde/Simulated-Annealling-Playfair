// AI Project 2018 - Break a Playfair Cipher using Simulated Annealling
// Rebecca Kane G00320698

package ie.gmit.sw.ai;

// Runner class - doesn't actually do anything, just calls other classes/methods
public class CipherBreaker {

	public static void main(String[] args) {
		SimulatedAnnealling sa = new SimulatedAnnealling();
		char key[] = "THEQUICKBROWNFXMPDVLAZYGS".toCharArray(); // to char array - https://stackoverflow.com/a/11711248
		System.out.println("shuffling THEQUICKBROWNFXMPDVLAZYGS");
		System.out.print("new key: ");
		System.out.print(sa.shuffle(key));
	}
}
